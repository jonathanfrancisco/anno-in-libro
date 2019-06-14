package school;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.yearbook.student.Student;

import lombok.Data;

@Entity
@Data
public class School {
	
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;
	
	@OneToMany
	@JoinColumn
	private List<Student> students = new ArrayList<>();

}
