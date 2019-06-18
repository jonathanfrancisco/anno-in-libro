package com.yearbook.student;

public class StudentNotFoundException extends RuntimeException {
	public StudentNotFoundException(Long id) {
		super("Student not found! ID: "+id);
	}
}
