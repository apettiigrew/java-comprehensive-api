package andrew.pettigrew.comprehensive_api.security;

import andrew.pettigrew.comprehensive_api.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        andrew.pettigrew.comprehensive_api.entities.User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
