package com.yearbook.course;

public class CourseNotFoundException extends RuntimeException {
	public CourseNotFoundException(Long id) {
		super("Course not found! ID: "+id);
	}
}
