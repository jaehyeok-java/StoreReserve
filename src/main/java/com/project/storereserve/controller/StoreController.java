package com.project.storereserve.controller;

import com.project.storereserve.domain.dto.StoreRequestDto;
import com.project.storereserve.domain.dto.StoreResponseDto;
import com.project.storereserve.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    // JWTFilter 를 거쳐서 토큰인증이 된경우에 가능하기에 테스트시 헤더에 Authorization 으로 Bearer 토큰 첨부할것.
    @PostMapping("/register")
    public ResponseEntity<StoreResponseDto> registerStore(@RequestBody StoreRequestDto requestDto) {
        StoreResponseDto store = storeService.registerStore(requestDto);
        return ResponseEntity.ok(store);
    }
}
