package com.fivetraining.app;

import com.fivetraining.app.models.User;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;

public class UserSession {
    private User currentUser;

    public UserSession() {
        currentUser = null;
    }

    public void signIn(User user) {
        currentUser = user;
    }

    public void signOut() {
        currentUser = null;
    }
    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public User getAuthenticatedUser() {
        return currentUser;
    }

    public void throwIfNotAuthenticated() throws ConsoleCommandExecutionException {
        if (!isAuthenticated()) {
            throw new ConsoleCommandExecutionException("Você precisa entrar primeiro para fazer isso");
        }
    }
}
