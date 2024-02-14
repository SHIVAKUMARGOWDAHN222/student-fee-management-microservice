package com.org.studentmanagement.exception;

public class CourseAlreadyExistsException extends RuntimeException{

    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}
