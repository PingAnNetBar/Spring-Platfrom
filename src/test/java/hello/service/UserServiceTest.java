package hello.service;

import hello.entity.User;
import hello.dao.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockMapper;
    @InjectMocks
    UserService userService = new UserService(mockEncoder, mockMapper);

    @Test
    public void testSave() {
        when(mockEncoder.encode("myPassword")).thenReturn("myEncodedPassword");

        userService.save("myUser", "myPassword");

        verify(mockMapper).save("myUser", "myEncodedPassword");
    }

    @Test
    public void testGetUserByUsername() {

        userService.getUserByUsername("myUser");
        verify(mockMapper).findUserByUsername("myUser");
    }

    @Test
    public void throwExceptionWhenUserNotFound() {

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("myUser"));
    }

    @Test
    public void returnUserDetailsWhenUserFound() {

        when(mockMapper.findUserByUsername("myUser"))
                .thenReturn(new User(123, "myUser","myEncodedPassword"));

        UserDetails userDetails = userService.loadUserByUsername("myUser");

        Assertions.assertEquals("myUser",userDetails.getUsername());
        Assertions.assertEquals("myEncodedPassword",userDetails.getPassword());

    }
}