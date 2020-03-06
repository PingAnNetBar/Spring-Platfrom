package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //private UserMapper usermapper;
    private Map<String, String> userPassowrds = new ConcurrentHashMap<>();

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        save("John", "123456");
    }

//    public UserService() {
//            userPassowrds.put("John","123456");
//    }

    public void save(String username, String password) {
        userPassowrds.put(username, bCryptPasswordEncoder.encode(password));
    }

    public String getPassword(String username) {
        return userPassowrds.get(username);
    }

//    @Inject
//    public UserService(UserMapper usermapper) {
//        this.usermapper = usermapper;
//    }

//    public User getUserById(Integer id) {
//        return usermapper.finduserById(id);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!userPassowrds.containsKey(username)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        String encodePassword = userPassowrds.get(username);
        return new org.springframework.security.core.userdetails.User(username, encodePassword, Collections.EMPTY_LIST);
    }
}
