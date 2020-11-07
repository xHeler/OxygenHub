package me.kwiatkowsky.OxygenHub.Service;

import me.kwiatkowsky.OxygenHub.Domain.User;
import me.kwiatkowsky.OxygenHub.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getList() {
        List<User> usersList = userRepository.findAll();
        return usersList;
    }

    public User create(User obj) {

        if (getList().stream()
                .anyMatch(user -> user.getUsername().equals(obj.getUsername()))) return null;

        return userRepository.save(obj);
    }

    public void delete(User obj) {
        userRepository.delete(obj);
    }

    public void deleteById(String id) {

        User userEntity = findById(id);
        if (!userEntity.getId().isEmpty()) userRepository.delete(userEntity);
    }

    public User findById(String id) {

        User userEntity = new User();
        getList().stream().filter( user -> user.getId().equals(id))
                .findAny().ifPresent( user -> {
            userEntity.setId(user.getId());
            userEntity.setUsername(user.getUsername());
            userEntity.setPassword(user.getPassword());
            userEntity.setUuid(user.getUuid());
            userEntity.setAccountNonExpired(user.isAccountNonExpired());
            userEntity.setAccountNonLocked(user.isAccountNonLocked());
            userEntity.setCredentialsNonExpired(user.isCredentialsNonExpired());
            userEntity.setEmail(user.getEmail());
            userEntity.setEnabled(user.isEnabled());
            userEntity.setRoles(user.getRoles());
        });

        return userEntity;
    }

    public User find(User user){

        User userEntity = findById(user.getId());

        return userEntity;
    }

    public User findByUsername(String username){
        User userEntity = new User();
        getList().stream().filter(userFinded ->
                userFinded.getUsername().equals(username))
                .findAny().ifPresent( userFinded -> {
            userEntity.setId(userFinded.getId());
            userEntity.setUsername(userFinded.getUsername());
            userEntity.setPassword(userFinded.getPassword());
            userEntity.setRoles(userFinded.getRoles());
            userEntity.setEnabled(userFinded.isEnabled());
            userEntity.setEmail(userFinded.getEmail());
            userEntity.setCredentialsNonExpired(userFinded.isCredentialsNonExpired());
            userEntity.setAccountNonLocked(userFinded.isAccountNonLocked());
            userEntity.setAccountNonExpired(userFinded.isAccountNonExpired());
            userEntity.setUuid(userFinded.getUuid());
        });

        return userEntity;
    }

    public boolean objectIsExist(User obj) {

        return getList().stream().anyMatch(user -> user.getId().equals(obj.getId()));
    }
}
