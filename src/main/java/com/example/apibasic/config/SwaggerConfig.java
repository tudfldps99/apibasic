// 2023-01-16
package com.example.apibasic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.*;

// 설정파일 (maven 의 XML 파일 대체)
@Configuration      // 설정파일임을 알림
public class SwaggerConfig {

    // <bead id=groupedOpenApi class=org.springdoc.core.GroupedOpenApi/>        // XML 로 표현한다면 이렇게 표현
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        // https://springdoc.org/#demos 참고
        return GroupedOpenApi
                .builder()
                .group("test-project")
                .pathsToMatch("/posts/**", "/users/**")     // 문서화 시킬 API 의 url 패턴 작성. 여러개 작성 가능
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("my api")
                                .description("내 API 명세서입니다")
                                .version("v1.0.0")
                );
    }
}
