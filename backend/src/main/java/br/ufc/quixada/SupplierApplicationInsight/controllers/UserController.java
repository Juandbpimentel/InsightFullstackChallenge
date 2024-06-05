package br.ufc.quixada.SupplierApplicationInsight.controllers;

import br.ufc.quixada.SupplierApplicationInsight.models.User;
import br.ufc.quixada.SupplierApplicationInsight.services.TokenService;
import br.ufc.quixada.SupplierApplicationInsight.services.UserService;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.user.request.UserUpdateRequestDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.user.response.UserMeGetResponseDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.user.response.UserMeUpdateResponseDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.user.response.UserUpdateResponseDTO;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserAlreadyExistsException;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(produces = "application/json", path = "/list")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }


    @GetMapping(produces = "application/json", path = "/me")
    public ResponseEntity<UserMeGetResponseDTO> getMe() {
        User user = userService.getMe();
        return ResponseEntity.ok(new UserMeGetResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole().name()));
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/me")
    public ResponseEntity<UserMeUpdateResponseDTO> updateMe(@Valid @RequestBody UserUpdateRequestDTO user) {
        User updatedUser = userService.updateMe(user.toMap());
        String token = tokenService.generateToken(updatedUser);
        return ResponseEntity.ok(new UserMeUpdateResponseDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getRole().name(), token));
    }

    @DeleteMapping(path = "/me")
    public ResponseEntity<String> deleteMe() {
        userService.deleteMe();
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) throws UserNotFoundException {
        User user = userService.findById(id);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "/{id}")
    public ResponseEntity<UserUpdateResponseDTO> updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequestDTO user) throws UserNotFoundException {
        User updatedUser = userService.updateById(id, user.toMap());
        updatedUser.setPassword(null);
        return ResponseEntity.ok(new UserUpdateResponseDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getRole().name()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json", path = "/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        User newUser = userService.createUser(user);
        newUser.setPassword(null);
        return ResponseEntity.ok(newUser);
    }

}
