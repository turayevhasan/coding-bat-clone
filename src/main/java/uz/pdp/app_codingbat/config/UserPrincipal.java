package uz.pdp.app_codingbat.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.app_codingbat.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public record UserPrincipal(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        grantedAuthorities.addAll(user.getRole().getPermissions());
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isEnabled() {return user.isActive();}

    public boolean allOk() {
        return isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired() && isEnabled();
    }

    public UUID getId() {
        return user.getId();
    }

}
