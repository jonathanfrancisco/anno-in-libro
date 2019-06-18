package com.yearbook.student;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.asm.Advice.This;


@RestController
public class StudentController {
	
	private final StudentRepository studentRepository;
	private final StudentResourceAssembler studentResourceAssembler;
	
	public StudentController(StudentRepository studentRepository, StudentResourceAssembler studentResourceAssembler) {
		this.studentRepository = studentRepository;
		this.studentResourceAssembler = studentResourceAssembler;
	}
	
	@PostMapping("/students")
	public ResponseEntity<?> create(@Valid @RequestBody Student student) throws URISyntaxException {
		
		Resource<Student> newResource = this.studentResourceAssembler.toResource(
					this.studentRepository.save(student)
				);
		
		return ResponseEntity.created(new URI(newResource.getId().expand().getHref())).body(newResource);
	}
	
	@GetMapping("/students")
	public Resources<Resource<Student>> all() {
		List<Resource<Student>> resources = this.studentRepository.findAll()
				.stream().map(this.studentResourceAssembler::toResource).collect(Collectors.toList());
		
		return new Resources<>(
				resources,
				linkTo(methodOn(StudentController.class).all()).withSelfRel()
		);
	}
	
	@GetMapping("/students/{id}")
	public Resource<Student> one(@PathVariable Long id) {
		Student student = this.studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
		
		return this.studentResourceAssembler.toResource(student);
	}
	
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		this.studentRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Student student, @PathVariable Long id) throws URISyntaxException {
		
		Resource<Student> resource = this.studentRepository.findById(id).map((entity) -> {
			entity.setFirstName(student.getFirstName());
			entity.setLastName(student.getLastName());
			entity.setMiddleName(student.getMiddleName());
			entity.setQuote(student.getQuote());
			entity.setYearGraduated(student.getYearGraduated());
			entity.setSchool(student.getSchool());
			entity.setCourse(student.getCourse());
			return this.studentResourceAssembler.toResource(this.studentRepository.save(entity));
		}).orElseGet(() -> {
			student.setId(id);
			return this.studentResourceAssembler.toResource(this.studentRepository.save(student));
		});
		
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	
	
}
