package com.project.storereserve.domain.dto;

import com.project.storereserve.domain.entity.RoleSelect;
import com.project.storereserve.domain.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreResponseDto {

    private Integer id;
    private String name;
    private String location;
    private String description;
    private OwnerDto owner;

    @Getter
    @AllArgsConstructor
    public static class OwnerDto {
        private Integer id;
        private String name;
        private String email;
        private RoleSelect role;
    }

    // 매장 등록 후 클라이언트에게 반환하는 json 데이터에서 비밀번호를 제거하기 위한 메서드
    public static StoreResponseDto from (Store store) {
        return new StoreResponseDto(
                store.getId(),
                store.getName(),
                store.getLocation(),
                store.getDescription(),
                new OwnerDto(
                        store.getOwner().getId(),
                        store.getOwner().getName(),
                        store.getOwner().getEmail(),
                        store.getOwner().getRole()
                )
        );
    }
}
