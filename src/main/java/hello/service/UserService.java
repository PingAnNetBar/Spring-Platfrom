package hello.service;

import hello.mapper.UserMapper;

import javax.inject.Inject;

public class UserService {

    private UserMapper usermapper;

    @Inject
    public UserService(UserMapper usermapper) {
        this.usermapper = usermapper;
    }

    public User getUserById(Integer id) {
        return usermapper.finduserById(id);
    }
}
