package hello.entity;

import javax.inject.Inject;
import java.time.Instant;

public class User {

    int id;
    String username;
    String avatar;
    Instant createdAt;
    Instant updateAt;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
        this.avatar = "";
        this.createdAt = Instant.now();
        this.updateAt = Instant.now();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
