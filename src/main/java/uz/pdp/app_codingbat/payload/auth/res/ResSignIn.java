package uz.pdp.app_codingbat.payload.auth.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResSignIn {
    private ResUserSimple user;
    private TokenDto token;
}
