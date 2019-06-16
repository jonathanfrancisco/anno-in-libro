package com.yearbook.school;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Service;

@Service
public class SchoolResourceAssembler implements ResourceAssembler<School, Resource<School>> {

	@Override
	public Resource<School> toResource(School entity) {
		return new Resource<>(
				entity,
				linkTo(methodOn(SchoolController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(SchoolController.class).all()).withRel("schools")
		);
	}

	
}
