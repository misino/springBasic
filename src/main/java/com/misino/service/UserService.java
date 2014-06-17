package com.misino.service;

import com.misino.data.domain.User;
import com.misino.utils.exceptions.BadAttemptException;
import com.misino.utils.exceptions.UniqueException;

import java.util.List;

public interface UserService
{
    public User create(User user) throws UniqueException;
    public void delete(Long id);
    public User update(User user);
    public List<User> findAll();
    public User findById(Long id);
    public boolean userExists(String userName);
    public void activateUser(String email, String activationKey) throws BadAttemptException;
    public void sendForgottenPasswordMail(String email) throws BadAttemptException;
    void resetPassword(String email, String password, String key) throws BadAttemptException;
}
