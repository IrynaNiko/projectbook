package org.iryna.projectbook.service;

import org.iryna.projectbook.pojo.User;
import java.util.List;

public interface UserService {
    
    User        createUser(User user);

    User        checkUserByEmail(User user);

    User        getUserByEmail(String email);

    List<User>  readAllUsers();

    User        getUserById(int id);

    User        updateUser(User user);

    Integer     deleteUser(int id);
}
