package hello.configuration;

import hello.mapper.UserMapper;
import hello.service.OrderService;
import hello.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class JavaConfiguration {

//    @Bean
//    public UserService userservice(UserMapper usermapper) {
//        return new UserService(usermapper);
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService(new BCryptPasswordEncoder());
    }

}