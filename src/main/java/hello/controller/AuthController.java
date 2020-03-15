package hello.controller;

import hello.entity.LoginResult;
import hello.entity.Result;
import hello.entity.User;
import hello.service.AuthService;
import hello.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final AuthService authService;

    @Inject
    public AuthController(AuthenticationManager authenticationmanager, UserService userService, AuthService authService) {
        this.authenticationmanager = authenticationmanager;
        this.userService = userService;
        this.authService = authService;
    }


    @GetMapping("/auth")
    @ResponseBody
    public LoginResult auth() {
        return authService.getCurrentUser()
                .map(LoginResult::success)
                .orElse(LoginResult.success("The user didn't login", false));
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
            return LoginResult.success("注册成功", userService.getUserByUsername(username));
        } else {
            return failure("用户已存在");
        }
    }


    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, Object> usernameAndPassword) {

        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();

        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure("用户不存在");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            authenticationmanager.authenticate(token);
            // 把用户信息保存在一个地方
            //   Cookie
            SecurityContextHolder.getContext().setAuthentication(token);

            return LoginResult.success("登录成功", userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return LoginResult.failure("密码不正确");
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUsername(userName);

        if (loggedInUser == null) {
            return failure("用户尚没有登陆");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.success("success", false);
        }

    }

}
