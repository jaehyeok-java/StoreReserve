package com.project.storereserve.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// url  =  http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String jwtScheme = "JWT";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtScheme);
        Components components = new Components().addSecuritySchemes(jwtScheme, new SecurityScheme()
                .name(jwtScheme)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"));

        return new OpenAPI()
                .info(apiInfo()) // API 기본 정보
                .addSecurityItem(securityRequirement) // JWT 인증 적용
                .components(components); // 보안 컴포넌트 추가
    }

    private Info apiInfo() {
        return new Info()
                .title("매장 예약 서비스 API")
                .description("매장 예약, 리뷰 작성 등의 기능을 제공하는 API 문서입니다.")
                .version("1.0.0");
    }
}
