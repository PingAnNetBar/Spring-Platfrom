package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        return new Result();
    }

    @PostMapping("/auth/login")
    public void login(@RequestBody Map<String, Object> usernameAndPasswordJson) {
        Map map = new HashMap();

    }

    private static class Result {

        public String getStatus() {
            return "OK";
        }

        public boolean getIsLogin() {
            return false;
        }
    }
}
