package hello;

import hello.service.User;
import hello.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import hello.service.OrderService;

import javax.inject.Inject;

@RestController
public class HelloController {

    private UserService userservice;

    @Inject
    public HelloController(UserService userservice) {
        this.userservice = userservice;
    }

    @RequestMapping("/")
    public User index() {
        return this.userservice.getUserById(1);
        //return "Greetings from Spring Boot!";
    }

}