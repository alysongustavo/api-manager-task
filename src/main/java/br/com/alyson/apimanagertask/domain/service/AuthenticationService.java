package br.com.alyson.apimanagertask.domain.service;

import br.com.alyson.apimanagertask.domain.exception.DatabaseException;
import br.com.alyson.apimanagertask.domain.model.User;
import br.com.alyson.apimanagertask.domain.repository.UserRepository;
import br.com.alyson.apimanagertask.dto.LoginUserDto;
import br.com.alyson.apimanagertask.dto.UserSaveDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserSaveDto input) {
        User user = new User();

        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setStatus(input.getStatus());

        try{
            return userRepository.save(user);
        }catch (Exception e){
            throw new DatabaseException(String.format("Erro ao salvar usuário: %s", e.getMessage()));
        }


    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
