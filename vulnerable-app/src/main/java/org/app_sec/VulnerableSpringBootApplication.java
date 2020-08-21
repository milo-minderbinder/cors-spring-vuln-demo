package org.app_sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class VulnerableSpringBootApplication {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/cors-java-config")
                //        .allowedOrigins(
                //                "*"
                //        )
                        .allowCredentials(true);
            }
        };
    }

    @RestController
    public static class Controller {

        @CrossOrigin(
                //origins = {
                //        "*"
                //},
                allowCredentials = "true")
        @GetMapping("/cors-annotation-config")
        public Map<String, String> corsWithAnnotationConfig(HttpServletRequest request, HttpServletResponse response) {
            addCookies(request, response);
            return getRequestHeaders(request);
        }

        @GetMapping("/cors-java-config")
        public Map<String, String> corsWithJavaConfig(HttpServletRequest request, HttpServletResponse response) {
            addCookies(request, response);
            return getRequestHeaders(request);
        }

        private void addCookies(HttpServletRequest request, HttpServletResponse response) {
            String namePrefix = request.getServletPath()
                    .substring(1)
                    .replaceAll("[^A-Za-z0-9.-_]", "-");
            String origin = request.getHeader("Origin");

            response.addHeader("Set-Cookie", String.join("; ", List.of(
                    String.format("%s%s=%s", namePrefix, "", origin)
            )));
            response.addHeader("Set-Cookie", String.join("; ", List.of(
                    String.format("%s%s=%s", namePrefix, "-Secure", origin),
                    "Secure"
            )));
            response.addHeader("Set-Cookie", String.join("; ", List.of(
                    String.format("%s%s=%s", namePrefix, "-HttpOnly", origin),
                    "HttpOnly"
            )));
            response.addHeader("Set-Cookie", String.join("; ", List.of(
                    String.format("%s%s=%s", namePrefix, "-Secure-HttpOnly", origin),
                    "Secure",
                    "HttpOnly"
            )));
            response.addHeader("Set-Cookie", String.join("; ", List.of(
                    String.format("%s%s=%s", namePrefix, "-NoneSameSite-Secure", origin),
                    "SameSite=None",
                    "Secure"
            )));
            response.addHeader("Set-Cookie", String.join("; ", List.of(
                    String.format("%s%s=%s", namePrefix, "-LaxSameSite-Secure", origin),
                    "SameSite=Lax",
                    "Secure"
            )));
            response.addHeader("Set-Cookie", String.join("; ", List.of(
                    String.format("%s%s=%s", namePrefix, "-StrictSameSite-Secure", origin),
                    "SameSite=Strict",
                    "Secure"
            )));
        }

        private Map<String, String> getRequestHeaders(HttpServletRequest req) {
            Map<String, String> headers = new HashMap<>();
            for (Enumeration<String> e = req.getHeaderNames(); e.hasMoreElements(); ) {
                String name = e.nextElement();
                headers.put(name, req.getHeader(name));
            }
            return headers;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(VulnerableSpringBootApplication.class, args);
    }
}
