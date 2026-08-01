package com.example.test.security.principal;

import com.example.test.models.entities.User;
import com.example.test.models.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        CustomUserDetails customUserDetails =  CustomUserDetails.builder()
                .user(user)
                .authorities(user.getRoles().stream()
                        .flatMap(role -> {
                            Stream<GrantedAuthority> permissionAuthorities = role.getPermissions() != null
                                    ? role.getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.getName()))
                                    : Stream.empty();
                            return Stream.concat(
                                    Stream.of(new SimpleGrantedAuthority(role.getRoleName())),
                                    permissionAuthorities
                            );
                        })
                        .toList())
                .build();
        return customUserDetails;
    }
}
