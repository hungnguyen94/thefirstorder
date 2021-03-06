package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Project entity.
 */
public interface ProjectRepository extends JpaRepository<Project,Long> {

}
