package controller;

import model.User;
import model.UserDAO;

public class LoginController {

    private final UserDAO dao;

    public LoginController() {
        this.dao = new UserDAO();
    }

    public User login(String username, String password) {
        return dao.findByCredentials(username, password);
    }

    public boolean authenticate(String username, String password) {
        return dao.findByCredentials(username, password) != null;
    }
}
