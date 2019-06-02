package ru.protopopova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.protopopova.model.User;
import ru.protopopova.repository.CrudUserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationManager {

    @Autowired
    CrudUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        System.out.println("email!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        String password = authentication.getCredentials().toString();
        User user = userRepository.getByEmail(email);
        if (user != null) {
            password = StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password);
            if (user.getPassword().equals(password)) {
                new AuthorizedUser(user);
            }

        }
        return null;
    }


}

