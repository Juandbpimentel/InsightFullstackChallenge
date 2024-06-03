package quixada.ufc.br.SupplierApplicationInsight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quixada.ufc.br.SupplierApplicationInsight.models.User;
import quixada.ufc.br.SupplierApplicationInsight.repositories.UserRepository;
import quixada.ufc.br.SupplierApplicationInsight.types.enums.UserRole;
import quixada.ufc.br.SupplierApplicationInsight.types.exceptions.AdminAlreadyExistsException;
import quixada.ufc.br.SupplierApplicationInsight.types.exceptions.UserAlreadyExistsException;
import quixada.ufc.br.SupplierApplicationInsight.types.exceptions.UserNotFoundException;

import java.util.List;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username).orElse(null);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        User userExists = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(userExists != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        if(user.getRole() == UserRole.ADMIN) {
            User adminExists = userRepository.findUserByRole(UserRole.ADMIN).orElse(null);
            if(adminExists != null)
                throw new AdminAlreadyExistsException("There is already an administrator user registered in the system");
        }
        return userRepository.insert(user);
    }


    public String deleteById(String id) {
        userRepository.deleteById(id);
        return "User deleted";
    }

    public User updateById(String id, User user) throws UserNotFoundException{
        User userExists = userRepository.findById(id).orElse(null);
        if(userExists == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public User getMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println(user);
        return user;
    }

    public User updateMe(User user) {
        User userExists = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userExists == null) {
            throw new UsernameNotFoundException("User not found");
        }
        user.setId(userExists.getId());
        System.out.println(userExists);
        System.out.println(user);
        return userRepository.save(user);
    }

    public void deleteMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        userRepository.deleteById(user.getId());
    }
}
