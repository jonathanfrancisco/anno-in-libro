package com.yearbook.student;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Service;

@Service
public class StudentResourceAssembler implements ResourceAssembler<Student, Resource<Student>> {

	@Override
	public Resource<Student> toResource(Student entity) {
		
		return new Resource<>(
				entity,
				linkTo(methodOn(StudentController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(StudentController.class).all()).withRel("students")
		);
	}
	
}
