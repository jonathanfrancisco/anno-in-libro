package com.yearbook.student;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.yearbook.course.Course;

import lombok.Data;
import school.School;

@Entity
@Data
public class Student {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	private String middleName;
	
	@NotNull
	private int yearGraduated;
	
	@ManyToOne
	@JoinColumn
	private School school;
	
	@ManyToOne
	@JoinColumn
	private Course course;

}
