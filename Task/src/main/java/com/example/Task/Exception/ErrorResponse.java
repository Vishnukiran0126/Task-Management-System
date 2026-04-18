package com.example.Task.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private String timestamp;
    private String path;

    public ErrorResponse(String message, int status,String path){
        this.timestamp= LocalDateTime.now().toString();
        this.message=message;
        this.status=status;
        this.path=path;
    }
}
