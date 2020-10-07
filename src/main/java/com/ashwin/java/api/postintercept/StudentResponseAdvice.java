package com.ashwin.java.api.postintercept;

import com.ashwin.java.api.controller.StudentController;
import com.ashwin.java.domain.model.Student;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class StudentResponseAdvice implements ResponseBodyAdvice<Student> {
    private static final String TAG = StudentResponseAdvice.class.getName();

    @Override
    public boolean supports(MethodParameter returnType, Class converter) {
        System.out.println(TAG + " | supports | MethodParameter parameterName: " + returnType.getParameterName() + ", containingClass: " + returnType.getContainingClass() + ", parameterType: " + returnType.getParameterType() + " | class: " + converter.getName());
        if (returnType.getContainingClass() == StudentController.class && (returnType.getParameterType() == ResponseEntity.class || returnType.getParameterType() == Student.class)) {
            // Forward response to below methods
            return true;
        }
        return false;
    }

    @Override
    public Student beforeBodyWrite(Student student, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println(TAG + " | beforeBodyWrite | Student: " + student + " | methodParameter: " + methodParameter + " | mediaType: " + mediaType + " | class: " + aClass.getName() + " | request: " + serverHttpRequest + " | response: " + serverHttpResponse);
        student.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        return student;
    }
}
