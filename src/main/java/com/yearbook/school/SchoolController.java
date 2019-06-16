package com.yearbook.school;

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

import com.yearbook.student.Student;

@RestController
public class SchoolController {
	
	private final SchoolRepository schoolRepository;
	private final SchoolResourceAssembler schoolResourceAssembler;
	
	public SchoolController(SchoolRepository schoolRepository, SchoolResourceAssembler schoolResourceAssembler) {
		this.schoolRepository = schoolRepository;
		this.schoolResourceAssembler = schoolResourceAssembler;
	}
	
	@PostMapping("/schools")
	public ResponseEntity<?> create(@RequestBody School school) throws URISyntaxException {
		Resource<School> newResource = schoolResourceAssembler.toResource(schoolRepository.save(school));
		
		return ResponseEntity.created(new URI(newResource.getId().expand().getHref())).body(school);
	}
	
	@GetMapping("/schools")
	public Resources<Resource<School>> all() {
		List<Resource<School>> schools = schoolRepository.findAll()
				.stream().map(schoolResourceAssembler::toResource).collect(Collectors.toList());
		
		return new Resources<>(
				schools,
				linkTo(methodOn(SchoolController.class).all()).withSelfRel()
		);
	}
	
	@GetMapping("/schools/{id}")
	public Resource<School> one(@PathVariable Long id) {
		School school = schoolRepository.findById(id).orElseThrow(() -> new SchoolNotFoundException(id));
		
		return schoolResourceAssembler.toResource(school);
	}
	
	@DeleteMapping("/schools/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		schoolRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
