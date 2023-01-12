package org.example.util.security;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class Bcrypt implements Encryption {

    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void checkPassword(String plainPassword, String hashedPassword) {
        if (!BCrypt.checkpw(plainPassword, hashedPassword)) {
            throw new RuntimeException("password is wrong");
        }

    }
}
