package co.id.mii.mcc63lmsserverside.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AppUser implements UserDetails {

  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
      role.getPrivileges().forEach(privilege -> {
        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
      });
    });

    return authorities;
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
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !user.getIsAccountLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.getIsEnabled();
  }

}
