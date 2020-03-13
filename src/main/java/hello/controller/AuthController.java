package hello.controller;

import hello.entity.LoginResult;
import hello.entity.Result;
import hello.entity.User;
import hello.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

import static hello.entity.LoginResult.failure;


@Controller
public class AuthController {
    private AuthenticationManager authenticationmanager;
    private UserService userService;


    @Inject
    public AuthController(AuthenticationManager authenticationmanager, UserService userService) {
        this.authenticationmanager = authenticationmanager;
        this.userService = userService;
    }


    @GetMapping("/auth")
    @ResponseBody
    public Result auth() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = userService.getUserByUsername(authentication == null ? null : authentication.getName());

        if (loggedInUser == null) {
            return LoginResult.success("The user didn't login", false);
        } else {
            return LoginResult.success(null, true, loggedInUser);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPasswordJson) {
        String username = usernameAndPasswordJson.get("username");
        String password = usernameAndPasswordJson.get("password");

        if (username == null || password == null) {
//            return new Result("fail", "用户名或密码为空", false);
            return failure("用户名或密码为空");
        }
        if (username.length() < 1 || username.length() > 15) {
//            return new Result("fail", "用户名长度为1到15个字符", false);
            return failure("用户名长度为1到15个字符");
        }
        if (password.length() < 6 || password.length() > 16) {
//            return new Result("fail", "密码长度为6到16个任意字符", false);
            return failure("密码长度为6到16个任意字符");
        }

        User user = userService.getUserByUsername(username);

        if (user == null) {
            userService.save(username, password);
            return LoginResult.success( "success!", false, userService.getUserByUsername(username));
        } else {
//            return new Result("fail", "用户已存在", false);
            return failure("用户已存在");
        }
    }


    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> usernameAndPasswordJson) {
        String username = usernameAndPasswordJson.get("username");
        String password = usernameAndPasswordJson.get("password");
        UserDetails userdetails = null;
        try {
            userdetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
//            return new Result("fail", "The username doesn't exist.", false);
            return failure("The username doesn't exist");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userdetails, password, userdetails.getAuthorities());
        try {
            authenticationmanager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.success("success", true, userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return failure("密码不正确");
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUsername(userName);

        if (loggedInUser == null) {
//            return new Result("fail", "用户尚没有登陆", false);
            return failure("用户尚没有登陆");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.success("success", false);
        }

    }

}
