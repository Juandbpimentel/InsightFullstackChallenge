package br.ufc.quixada.SupplierApplicationInsight.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.ufc.quixada.SupplierApplicationInsight.models.User;
import br.ufc.quixada.SupplierApplicationInsight.services.TokenService;
import br.ufc.quixada.SupplierApplicationInsight.services.UserService;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.request.AuthenticationRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.request.RegisterRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.response.AuthenticationResponseDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserAlreadyExistsException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken( (User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponseDTO(token,body.email(),((User) auth.getPrincipal()).getRole().toString()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody @Valid RegisterRequestDTO body) throws UserAlreadyExistsException {
        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        User newUser = new User(body.name(), body.email(), encryptedPassword, body.role());
        User newUserCreated = userService.createUser(newUser);
        return this.login(new AuthenticationRequestDTO(newUserCreated.getEmail(), body.password()));
    }


}
