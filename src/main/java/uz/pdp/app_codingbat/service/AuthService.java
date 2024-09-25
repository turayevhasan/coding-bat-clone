package uz.pdp.app_codingbat.service;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.config.UserPrincipal;
import uz.pdp.app_codingbat.config.jwt.JwtTokenProvider;
import uz.pdp.app_codingbat.config.prop.AppProp;
import uz.pdp.app_codingbat.entity.Attachment;
import uz.pdp.app_codingbat.entity.Role;
import uz.pdp.app_codingbat.entity.User;
import uz.pdp.app_codingbat.entity.enums.RoleEnum;
import uz.pdp.app_codingbat.entity.enums.UserStatus;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.payload.auth.req.ReqRefreshToken;
import uz.pdp.app_codingbat.payload.auth.req.ReqSignIn;
import uz.pdp.app_codingbat.payload.auth.req.ReqSignUp;
import uz.pdp.app_codingbat.payload.auth.res.ResSignIn;
import uz.pdp.app_codingbat.payload.auth.res.ResUserSimple;
import uz.pdp.app_codingbat.payload.auth.res.TokenDto;
import uz.pdp.app_codingbat.payload.base.ResBaseMsg;
import uz.pdp.app_codingbat.repository.AttachmentRepository;
import uz.pdp.app_codingbat.repository.RoleRepository;
import uz.pdp.app_codingbat.repository.UserRepository;
import uz.pdp.app_codingbat.utils.RestConstants;


import java.util.UUID;

import static uz.pdp.app_codingbat.enums.ErrorTypeEnum.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProp appProp;

    public ResBaseMsg signUp(ReqSignUp req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw RestException.restThrow(EMAIL_ALREADY_EXISTS);

        Role role = getRoleByName(RoleEnum.USER);

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        mailService.sendActivationEmail(user);

        return new ResBaseMsg("Success! Verification Sms sent your Email!");
    }

    public ResBaseMsg verifyEmail(String email) {
        User user = getUserByEmail(email);

        if (user.isActive())
            return new ResBaseMsg("User Already Verified!");

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        return new ResBaseMsg("User Successfully Verified!");
    }


    public ResSignIn signIn(ReqSignIn req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                ));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return generateSignInRes(userPrincipal.user());
    }

    public TokenDto refreshToken(ReqRefreshToken req) {

        String accessToken = req.getAccessToken().trim();
        accessToken = getTokenWithOutBearer(accessToken);

        try {
            jwtTokenProvider.extractUsername(accessToken);
        } catch (ExpiredJwtException ex) {
            try {
                String refreshToken = req.getRefreshToken();
                refreshToken = getTokenWithOutBearer(refreshToken);

                String email = jwtTokenProvider.extractUsername(refreshToken);
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> RestException.restThrow(TOKEN_NOT_VALID));

                if (!user.isActive())
                    throw RestException.restThrow(USER_PERMISSION_RESTRICTION);

                return generateTokens(user);

            } catch (Exception e) {
                throw RestException.restThrow(REFRESH_TOKEN_EXPIRED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow(WRONG_ACCESS_TOKEN);
        }
        throw RestException.restThrow(ACCESS_TOKEN_NOT_EXPIRED);

    }

    private static String getTokenWithOutBearer(String token) {
        return token.startsWith(RestConstants.BEARER_TOKEN) ?
                token.substring(token.indexOf(RestConstants.BEARER_TOKEN) + 6).trim() :
                token.trim();
    }

    private ResSignIn generateSignInRes(User user) {
        ResUserSimple resUserSimple = new ResUserSimple(user);
        return new ResSignIn(resUserSimple, generateTokens(user));
    }

    private TokenDto generateTokens(User user) {
        String accessToken = jwtTokenProvider.generateToken(user);
        Long accessTokenExp = appProp.getJwt().getAccessTokenExp();
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        Long refreshTokenExp = appProp.getJwt().getRefreshTokenExp();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpire(accessTokenExp)
                .refreshTokenExpire(refreshTokenExp)
                .build();
    }

    private @NonNull Role getRoleByName(@NonNull RoleEnum roleEnum) {
        return roleRepository.findByName(roleEnum.name())
                .orElseThrow(RestException.thew(ROLE_NOT_FOUND));
    }

    private @NonNull User getUserByEmail(@NonNull String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(RestException.thew(USER_NOT_FOUND));
    }
}
