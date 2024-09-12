package com.example.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotFoundException extends RuntimeException{
    private String message;
    private HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        this.message = message;
    }
}