package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import hello.service.OrderService;

import javax.inject.Inject;

@RestController
public class HelloController {
    private OrderService orderservice;

    @Inject
    public HelloController(OrderService orderservice) {
        this.orderservice = orderservice;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}