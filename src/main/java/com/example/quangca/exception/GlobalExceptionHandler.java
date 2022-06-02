package com.example.quangca.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AppException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleAppException(Exception e){
        LOGGER.error(e.toString());
        return ResponseEntity.status(404).body(e.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleException(Exception e){
//        LOGGER.error(e.toString());
//        return ResponseEntity.status(500).body("Unknow error!");
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleAccessDeniedException(Exception e){
//        LOGGER.error(e.toString());
//        Authentication auth
//                = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            LOGGER.warn("User: " + auth.getName()
//                    + " attempted to access the protected URL: ");
//        }
//
//        return ResponseEntity.status(500).body("Bạn không có quyền truy cập!");
//    }
}
