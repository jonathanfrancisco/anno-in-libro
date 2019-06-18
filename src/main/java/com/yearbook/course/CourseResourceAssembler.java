package com.yearbook.course;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Service;

@Service
public class CourseResourceAssembler implements ResourceAssembler<Course, Resource<Course>> {

	@Override
	public Resource<Course> toResource(Course entity) {
		return new Resource<>(
				entity,
				linkTo(methodOn(CourseController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(CourseController.class).all()).withRel("courses")
		);
	}

}
