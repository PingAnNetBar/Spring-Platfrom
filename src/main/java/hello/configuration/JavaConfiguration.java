package hello.configuration;

import hello.mapper.UserMapper;
import hello.service.OrderService;
import hello.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {

    @Bean
    public UserService userservice(UserMapper usermapper) {
        return new UserService(usermapper);
    }

    @Bean
    public OrderService orderservice(UserService userservice) {
        return new OrderService(userservice);
    }
}