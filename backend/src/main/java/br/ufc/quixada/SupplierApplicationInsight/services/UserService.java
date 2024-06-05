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
        System.out.println(user);
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
        User user = userRepository.findById(id).orElse(null);
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
        if (userFields.containsKey("name") && !((String) userFields.get("name")).isEmpty()) {
            user.setName(userFields.get("name").toString());
        }
        if (userFields.containsKey("email") && !((String) userFields.get("email")).isEmpty()) {
            user.setEmail(userFields.get("email").toString());
        }
        if (userFields.containsKey("password") && !((String) userFields.get("password")).isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(userFields.get("password").toString()));
        }
        if (userFields.containsKey("role") && !((String) userFields.get("role")).isEmpty()) {
            user.setRole(UserRole.valueOf(userFields.get("role").toString()));
        }
        System.out.println(user);
        return userRepository.save(user);
    }
}
