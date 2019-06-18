package com.yearbook.student;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.yearbook.course.Course;
import com.yearbook.school.School;

import lombok.Data;

@Entity
@Data
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty
	private String firstName;
	
	@NotNull
	@NotEmpty
	private String lastName;
	
	private String middleName;
	
	private String quote;
	
	@NotNull
	private int yearGraduated;
	
	@ManyToOne
	@NotNull
	private School school;
	
	@ManyToOne
	@NotNull
	private Course course;
	
}
