package com.example.da1nhom9jav102.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // Không set cứng contentType là text/html ở đây để tránh làm lỗi MIME Type của các file tĩnh (.css, .js, hình ảnh...)
        chain.doFilter(request, response);
    }
}
