package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.entity.Role;
import uz.pdp.app_codingbat.entity.User;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.mapper.UserMapper;
import uz.pdp.app_codingbat.payload.user.req.RequestFilterByCreatedDate;
import uz.pdp.app_codingbat.payload.user.req.ReqUserUpdate;
import uz.pdp.app_codingbat.payload.user.res.ResUserSimple;
import uz.pdp.app_codingbat.repository.AttachmentRepository;
import uz.pdp.app_codingbat.repository.UserRepository;
import uz.pdp.app_codingbat.utils.CoreUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final PasswordEncoder passwordEncoder;

    public ResUserSimple getOneByEmail(String email) {
        User user = getUserByEmail(email);
        return UserMapper.fromEntityToDto(user);
    }

    public List<ResUserSimple> getAllUsers(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<User> users = userRepository.findAll(pageRequest);

       return UserMapper.getAllFromPages(users);
    }

    public void deleteUserById(UUID id) {
        User user = getUserById(id);

        user.setDeleted(Boolean.TRUE);
        userRepository.save(user);
    }

    public ResUserSimple updateUser(ReqUserUpdate req) {
        UUID id = req.getUserId();
        User user = getUserById(id);

        user.setEmail(CoreUtils.getIfExists(req.getEmail(), user.getEmail()));
        updateRole(user, req.getRole());
        updatePassword(user, req.getPassword());

        userRepository.save(user);
        return UserMapper.fromEntityToDto(user);
    }

    public Page<ResUserSimple> search(String keyword, Pageable pageable) {
        Page<User> users = userRepository.search(keyword, pageable);

        List<ResUserSimple> resUsers = users.getContent()
                .stream()
                .map(UserMapper::fromEntityToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(resUsers, pageable, users.getTotalElements());
    }

    private void updatePassword(User user, String newPassword) {
        String password = CoreUtils.getIfExists(newPassword, user.getPassword());
        user.setPassword(passwordEncoder.encode(password));
    }

    private void updateRole(User user, String newRole){
        String updatedRole = CoreUtils.getIfExists(newRole, user.getRole().getName());
        Role role = new Role(updatedRole);
        user.setRole(role);
    }

    private User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));
    }
    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));
    }

    public List<ResUserSimple> getFilteredUsers(RequestFilterByCreatedDate req) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getSize(), sort);

        Page<User> users = userRepository.findUsersByCreatedAtBetween(
                req.getStartDate(),
                req.getEndDate(),
                pageRequest
        );

        return UserMapper.getAllFromPages(users);
    }
}
