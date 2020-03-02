package hello.service;

import javax.inject.Inject;

public class OrderService {
    private UserService userservice;

    @Inject
    public OrderService(UserService userservice) {
        this.userservice = userservice;
    }

    //It's a test for simulate Dependency Injection
    public void placeOrder(Integer userId, String good) {
        userservice.getUserById(userId);
    }
}
