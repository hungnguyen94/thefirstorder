package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Camera entity.
 */
public interface CameraRepository extends JpaRepository<Camera,Long> {

    List<Camera> findByProjectId(Long projectId);

}
