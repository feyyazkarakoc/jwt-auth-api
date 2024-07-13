package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.dto.RegisterRequest;
import com.tpe.exception.ConflictException;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new ConflictException("User is already registered.");
        }


        Role role = roleService.findByType(UserRole.ROLE_STUDENT);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUserName());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

    }
}
