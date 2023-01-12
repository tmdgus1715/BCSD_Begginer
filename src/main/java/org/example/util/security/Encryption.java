package org.example.util.security;

public interface Encryption {
    public String encrypt(String password);

    public void checkPassword(String plainPassword, String hashedPassword);

}
