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
public class InternalServerErrorException extends RuntimeException {
    private String message;
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public InternalServerErrorException(String message) {
        super(message);
        this.message = message;
    }

}