package uz.pdp.app_codingbat.payload.auth.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ReqSignIn {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
