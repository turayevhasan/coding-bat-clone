package uz.pdp.app_codingbat.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.app_codingbat.entity.User;
import uz.pdp.app_codingbat.payload.user.res.ResUserSimple;

import java.util.ArrayList;
import java.util.List;

public interface UserMapper {

    static ResUserSimple fromEntityToDto(User user) {
        return ResUserSimple.builder()
                .id(user.getId())
                .email(user.getEmail())
                .status(user.getStatus())
                .role(user.getRole().getName())
                .profileImagePath(user.getProfileImagePath())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deleted(user.isDeleted())
                .build();
    }
    static List<ResUserSimple> getAllFromPages(Page<User> all) {
        List<ResUserSimple> users = new ArrayList<>();

        for (User user : all) {
            users.add(fromEntityToDto(user));
        }
        return users;
    }
}
