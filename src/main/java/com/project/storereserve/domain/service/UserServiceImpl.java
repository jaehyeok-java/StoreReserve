package com.project.storereserve.domain.service;

import com.project.storereserve.domain.entity.User;
import com.project.storereserve.domain.reposiotry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user){
        return userRepository.save(user);
    }
}
