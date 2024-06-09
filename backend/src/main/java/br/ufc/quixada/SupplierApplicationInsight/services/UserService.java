package br.ufc.quixada.SupplierApplicationInsight.services;

import br.ufc.quixada.SupplierApplicationInsight.models.User;
import br.ufc.quixada.SupplierApplicationInsight.repositories.UserRepository;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.UserRole;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.AdminAlreadyExistsException;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserAlreadyExistsException;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findById(String id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        User userExists = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (userExists != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        if (user.getRole() == UserRole.ADMIN) {
            User adminExists = userRepository.findUserByRole(UserRole.ADMIN).orElse(null);
            if (adminExists != null)
                throw new AdminAlreadyExistsException("There is already an administrator user registered in the system");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.insert(user);
    }

    public User updateById(String id, Map<String, Object> userFields) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return updateUser(userFields, user);
    }

    public User updateMe(Map<String, Object> userFields) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null && user.getRole() == UserRole.USER) {
            userFields.remove("role");
        }

        return updateUser(userFields, user);
    }

    public String deleteById(String id) {
        userRepository.deleteById(id);
        return "User deleted";
    }

    public void deleteMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        userRepository.deleteById(user.getId());
    }

    private User updateUser(Map<String, Object> userFields, User user) {
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (userFields.isEmpty()) {
            throw new IllegalArgumentException("No fields to update");
        }
        userFields.forEach((key, value) -> {
            switch (key) {
                case "name":
                    user.setName((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "password":
                    user.setPassword(new BCryptPasswordEncoder().encode((String) value));
                    break;
                case "role":
                    user.setRole(UserRole.valueOf((String) value));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        return userRepository.save(user);
    }
}
