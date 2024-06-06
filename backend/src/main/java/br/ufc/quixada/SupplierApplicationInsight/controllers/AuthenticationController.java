package br.ufc.quixada.SupplierApplicationInsight.controllers;


import br.ufc.quixada.SupplierApplicationInsight.models.User;
import br.ufc.quixada.SupplierApplicationInsight.services.TokenService;
import br.ufc.quixada.SupplierApplicationInsight.services.UserService;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.request.AuthenticationRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.request.RegisterRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.response.AuthenticationResponseDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = this.tokenService.generateToken((User) auth.getPrincipal());
        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(new AuthenticationResponseDTO(token, user.toUserDTO()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody @Valid RegisterRequestDTO body) throws UserAlreadyExistsException {
        User newUserCreated = userService.createUser(new User(body.name(), body.email(), body.password(), body.role()));
        return this.login(new AuthenticationRequestDTO(newUserCreated.getEmail(), body.password()));
    }


}
