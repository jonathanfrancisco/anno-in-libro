package com.yearbook.school;

public class SchoolNotFoundException extends RuntimeException {
	public SchoolNotFoundException(Long id) {
		super("School not found! ID: "+id);
	}
}
