package com.yearbook.course;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

	private final CourseRepository courseRepository;
	private final CourseResourceAssembler courseResourceAssembler;
	
	public CourseController(CourseRepository courseRepository, CourseResourceAssembler courseResourceAssembler) {
		this.courseRepository = courseRepository;
		this.courseResourceAssembler = courseResourceAssembler;
	}
	
	@PostMapping("/courses")
	public ResponseEntity<?> create(@RequestBody Course course) throws URISyntaxException {
		Resource<Course> newResource = this.courseResourceAssembler
				.toResource(this.courseRepository.save(course));
		
		return ResponseEntity.created(new URI(newResource.getId().expand().getHref())).body(newResource);
	}
	
	@GetMapping("/courses")
	public Resources<Resource<Course>> all() {
		List<Resource<Course>> resources = this.courseRepository.findAll()
				.stream().map(this.courseResourceAssembler::toResource).collect(Collectors.toList());
		
		return new Resources<>(
				resources,
				linkTo(methodOn(CourseController.class).all()).withSelfRel()
		);
	}
	
	@GetMapping("/courses/{id}")
	public Resource<Course> one(@PathVariable Long id) {
		
		Course course = this.courseRepository.findById(id)
				.orElseThrow(() -> new CourseNotFoundException(id));
		
		return this.courseResourceAssembler.toResource(course);
	}
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		this.courseRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
