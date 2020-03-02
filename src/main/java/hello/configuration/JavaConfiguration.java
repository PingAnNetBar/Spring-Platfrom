package hello.configuration;

import hello.service.OrderService;
import hello.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {

    @Bean
    public UserService userservice() {
        return new UserService();
    }

    @Bean
    public OrderService orderservice() {
        return new OrderService(new UserService());
    }
}