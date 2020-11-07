package me.kwiatkowsky.OxygenHub.Controller;

import me.kwiatkowsky.OxygenHub.Domain.User;
import me.kwiatkowsky.OxygenHub.Service.CustomUserDetailsService;
import me.kwiatkowsky.OxygenHub.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Issue {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @RequestMapping({"/", "/home", "/index", "/index.html"})
    public String home(Model model, HttpServletRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = request.getRemoteUser();

        if (username == null) return "index";

        User user = userDetailsService.findByUsername(username);


        model.addAttribute("username", user.getUsername());
        model.addAttribute("head", "https://crafatar.com/avatars/"+ user.getUuid() +"?size=24&default=MHF_Steve&overlay}");

        return "index";
    }

    @RequestMapping("/app/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/app/logging")
    public String logging(Model model){
        return "redirect:/";
    }

}
