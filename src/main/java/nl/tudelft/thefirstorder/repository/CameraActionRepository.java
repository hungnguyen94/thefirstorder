package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.CameraAction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CameraAction entity.
 */
public interface CameraActionRepository extends JpaRepository<CameraAction,Long> {

}
