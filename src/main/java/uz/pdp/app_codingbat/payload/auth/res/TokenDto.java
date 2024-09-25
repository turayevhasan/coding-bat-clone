package uz.pdp.app_codingbat.payload.auth.res;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String accessToken;
    private Long accessTokenExpire;
    private String refreshToken;
    private Long refreshTokenExpire;
}
