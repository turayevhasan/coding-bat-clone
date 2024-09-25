package uz.pdp.app_codingbat.payload.user.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReqUserUpdate {
    @NotNull
    private UUID userId;

    private String email;
    private String password;
    private String role;
}
