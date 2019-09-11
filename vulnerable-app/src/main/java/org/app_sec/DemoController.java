package org.app_sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    private HttpServletRequest request;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/cors-annotation-config")
    public Map<String, String> corsWithAnnotationConfig(HttpServletResponse response) {
        response.addCookie(new Cookie("cors-spring-vuln-demo", "someval"));
        return getRequestHeaders(request);
    }

    @GetMapping("/cors-java-config")
    public Map<String, String> corsWithJavaConfig(HttpServletResponse response) {
        response.addCookie(new Cookie("cors-spring-vuln-demo", "someval"));
        return getRequestHeaders(request);
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest req) {
        Map<String, String> headers = new HashMap<>();
        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String name = e.nextElement();
            headers.put(name, request.getHeader(name));
        }
        return headers;
    }
}
