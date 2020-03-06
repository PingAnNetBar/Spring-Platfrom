package hello.controller;

import hello.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private UserDetailsService userdetailsservice;
    private AuthenticationManager authenticationmanager;

    @Inject
    public AuthController(UserDetailsService userdetailsservice, AuthenticationManager authenticationmanager) {
        this.userdetailsservice = userdetailsservice;
        this.authenticationmanager = authenticationmanager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        return new Result("OK", "用户尚没有登陆", false);
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> usernameAndPasswordJson) {
        String username = usernameAndPasswordJson.get("username");
        String password = usernameAndPasswordJson.get("password");
        UserDetails userdetails = null;
        try {
            userdetails = userdetailsservice.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result("fail", "用户不存在", false);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userdetails, password, userdetails.getAuthorities());
        try {
            authenticationmanager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            User loggedInUser = new User(1, "hunger");
            return new Result("OK", "登陆成功", true, loggedInUser);
        } catch (BadCredentialsException e) {
            return new Result("fail", "密码不正确", false);
        }

    }


    private static class Result {
        private String status;
        private String msg;
        private boolean isLogin;
        private Object data;

        public Result(String status, String msg, boolean isLogin) {
            this(status, msg, isLogin, null);
        }

        public Result(String status, String msg, boolean isLogin, Object data) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public Object getData() {
            return data;
        }
    }
}
