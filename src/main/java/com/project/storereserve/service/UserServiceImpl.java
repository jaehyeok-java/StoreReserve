package com.project.storereserve.service;

import com.project.storereserve.domain.entity.User;
import com.project.storereserve.domain.reposiotry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email address already in use");
        }

        // 클라이언트에서 전달받은 role을 그대로 User 객체에 설정
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role must be provided");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


}
