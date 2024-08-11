package com.example.user.Services.UserService;

import com.example.user.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<Users> getAllEmployees();

    Users getEmployeeById(Long id);

    void createEmployee(Users employee);

    boolean deleteEmployee(Long id);

    Boolean updateUser(Long id,Users users);

    String getMethodTesting();

//    void createEmployee(Users user);
}
