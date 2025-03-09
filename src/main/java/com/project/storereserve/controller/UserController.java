package com.project.storereserve.controller;

import com.project.storereserve.domain.entity.User;
import com.project.storereserve.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    // 매장 등록을 위한 파트너 회원가입은 일반회원가입에서 role 이 OWNER 인 것만 매장등록이 가능하도록 함.
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userServiceImpl.registerUser(user));
    }


}
