package uz.pdp.app_codingbat.payload.auth.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReqSignUp {

    @NotBlank
    @Pattern(regexp = "^\\w*?[a-zA-Z]\\w+@[a-z\\d\\-]+(\\.[a-z\\d\\-]+)*\\.[a-z]+\\z")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

}
