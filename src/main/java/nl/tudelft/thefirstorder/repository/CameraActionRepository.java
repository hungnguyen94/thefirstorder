package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.CameraAction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CameraAction entity.
 */
public interface CameraActionRepository extends JpaRepository<CameraAction,Long> {

}
