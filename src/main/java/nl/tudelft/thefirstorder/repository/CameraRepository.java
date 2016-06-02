package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Camera entity.
 */
public interface CameraRepository extends JpaRepository<Camera,Long> {

}
