package br.com.alyson.apimanagertask.controller;

import br.com.alyson.apimanagertask.domain.model.User;
import br.com.alyson.apimanagertask.domain.service.AuthenticationService;
import br.com.alyson.apimanagertask.domain.service.JwtService;
import br.com.alyson.apimanagertask.dto.LoginUserDto;
import br.com.alyson.apimanagertask.dto.UserSaveDto;
import br.com.alyson.apimanagertask.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * @return ResponseEntity<User>
     */
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid UserSaveDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
