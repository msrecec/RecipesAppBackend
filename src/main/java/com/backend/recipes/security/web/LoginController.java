package com.backend.recipes.security.web;

import com.backend.recipes.model.authority.Authority;
import com.backend.recipes.model.user.User;
import com.backend.recipes.repository.authority.AuthorityRepositoryJpa;
import com.backend.recipes.repository.user.UserRepositoryJpa;
import com.backend.recipes.security.jwt.JwtFilter;
import com.backend.recipes.security.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryJpa userRepositoryJpa;
    private final AuthorityRepositoryJpa authorityRepositoryJpa;

    public LoginController(TokenProvider tokenProvider,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           PasswordEncoder passwordEncoder,
                           UserRepositoryJpa userRepositoryJpa,
                           AuthorityRepositoryJpa authorityRepositoryJpa) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryJpa = userRepositoryJpa;
        this.authorityRepositoryJpa = authorityRepositoryJpa;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody LoginController.LoginDTO login) {

        Optional<User> user = userRepositoryJpa.findOneByUsername(login.getUsername());

        List<Authority> authorities = user.get().getAuthorities();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/user/register")
    public ResponseEntity<JWTToken> register(@Valid @RequestBody LoginController.RegisterDTO register) {


        Optional<User> user = userRepositoryJpa.findOneByUsername(register.getUsername());

        if(user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        String encodedPassword = passwordEncoder.encode(register.getPassword());

        Optional<Authority> authority = authorityRepositoryJpa.findById(2L);

        user = Optional.of(User.builder()
                .username(register.getUsername())
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .authorities(Arrays.asList(authority.get()))
                .password(encodedPassword)
                .build());

        user = Optional.of(userRepositoryJpa.save(user.get()));

        if(user.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();

    }


    /**
     * Return jwt token in body because Angular has problems with parsing plain string response entity
     */
    static class JWTToken {
        private String token;

        public JWTToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    static class LoginDTO {

        @NotNull
        private String username;

        @NotNull
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class RegisterDTO {
        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotNull
        private String firstName;
        @NotNull
        private String lastName;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}