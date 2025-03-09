package com.project.storereserve.service;

import com.project.storereserve.domain.dto.StoreRequestDto;
import com.project.storereserve.domain.dto.StoreResponseDto;
import com.project.storereserve.domain.entity.Store;
import com.project.storereserve.domain.entity.User;
import com.project.storereserve.domain.reposiotry.StoreRepository;
import com.project.storereserve.domain.reposiotry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public StoreResponseDto registerStore(StoreRequestDto requestDto) {
        // 현재 로그인한 사용자 정보 가져오기
        // Spring Security 에서 제공하는 SecurityContextHolder 를 사용하여 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // JWT 에서 추출된 username(email)
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("권한 정보가 없습니다."));

        // 역할이 OWNER 인지 확인
        if (!role.equals("OWNER")) {
            throw new AccessDeniedException("매장 등록 권한이 없습니다.");
        }

        // 사용자 조회
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 매장 등록
        Store store = new Store();
        store.setName(requestDto.getName());
        store.setLocation(requestDto.getLocation());
        store.setDescription(requestDto.getDescription());
        store.setOwner(owner);

        return StoreResponseDto.from(storeRepository.save(store));
    }

}
