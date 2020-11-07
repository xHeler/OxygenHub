package me.kwiatkowsky.OxygenHub.Controller;

import me.kwiatkowsky.OxygenHub.Domain.User;
import me.kwiatkowsky.OxygenHub.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Rest {

    private final UserService userService;
    private final Map<String, Object> response = new LinkedHashMap<>();

    @Autowired
    public Rest(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody Map<String, Object> createUser(@Valid @RequestBody User userEntity, BindingResult
            bindingResult){

        if (checkError(bindingResult)){
            if (userService.objectIsExist(userEntity)){
                response.put("message", "Taki użytkownik już istnieje");
                response.put("type", "used");
                return response;
            }
            userEntity.setCredentialsNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userEntity.setAccountNonExpired(true);
            userEntity.setEnabled(true);
            userEntity.setPassword("{noop}" + userEntity.getPassword());
            userEntity.addRole("ROLE_USER");
            userService.create(userEntity);
            response.put("message", "Poprawnie zarejestrowano!");
            response.put("type", "correct");
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public @ResponseBody List<User> findUsers(){
        return userService.getList();
    }

    public boolean checkError(BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            List<FieldError> errors= bindingResult.getFieldErrors();
            response.put("message", "Error");

            for(FieldError error : errors){
                response.put(error.getField(), error.getDefaultMessage());
            }
            return false;
        } else {
            return true;
        }
    }
}
