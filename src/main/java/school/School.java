package school;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class School {
	
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;

}
