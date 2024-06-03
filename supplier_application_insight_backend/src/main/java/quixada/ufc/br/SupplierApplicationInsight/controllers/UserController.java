package quixada.ufc.br.SupplierApplicationInsight.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quixada.ufc.br.SupplierApplicationInsight.models.User;
import quixada.ufc.br.SupplierApplicationInsight.services.TokenService;
import quixada.ufc.br.SupplierApplicationInsight.services.UserService;
import quixada.ufc.br.SupplierApplicationInsight.types.dto.user.request.UserMeUpdateRequestDTO;
import quixada.ufc.br.SupplierApplicationInsight.types.dto.user.response.UserMeGetResponseDTO;
import quixada.ufc.br.SupplierApplicationInsight.types.dto.user.response.UserMeUpdateResponseDTO;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private TokenService tokenService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json", path="/list")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }



    @GetMapping(produces = "application/json", path="/me")
    public ResponseEntity<UserMeGetResponseDTO> getMe() {
        User user = userService.getMe();
        return ResponseEntity.ok(new UserMeGetResponseDTO(user.getName(), user.getEmail(), user.getRole().name()));
    }

    @PutMapping(consumes = "application/json",produces = "application/json", path="/me")
    public ResponseEntity<UserMeUpdateResponseDTO> updateMe(@Valid @RequestBody UserMeUpdateRequestDTO user) {
        User updatedUser = userService.updateMe(user.toUser());
        String token = tokenService.generateToken(updatedUser);
        return ResponseEntity.ok(new UserMeUpdateResponseDTO(updatedUser.getName(), updatedUser.getEmail(), updatedUser.getRole().name(),token));
    }

    @DeleteMapping(path="/me")
    public ResponseEntity<String> deleteMe() {
        userService.deleteMe();
        return ResponseEntity.ok("User deleted successfully");
    }




}