package br.com.gathering.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebConfig {

    @Value("${ENV_GATHERING_ALLOWED_ORIGINS : *}")
    private String allowOrigins;

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                log.info("Registrando cors mapping para os seguintes domínios: " + allowOrigins);

                registry.addMapping("/**")
                        .allowedOrigins(getAllowedOrigins())
                        .allowedMethods("GET", "POST", "DELETE", "PUT")
                        .allowedHeaders("Authorization", "Content-Type", "Content-Disposition", "g-recaptcha-response")
                        .exposedHeaders("Authorization", "Content-Type", "Content-Disposition", "g-recaptcha-response");
            }

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.stream()
                        .filter(MappingJackson2HttpMessageConverter.class::isInstance)
                        .findFirst()
                        .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8));
            }

        };
    }

    private String[] getAllowedOrigins() {
        if(allowOrigins != null && allowOrigins.trim().length() > 0) {
            String[] split = allowOrigins.split(",");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }
            return split;
        }
        return new String[]{};
    }
}
