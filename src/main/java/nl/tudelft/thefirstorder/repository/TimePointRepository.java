package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.TimePoint;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimePoint entity.
 */
public interface TimePointRepository extends JpaRepository<TimePoint,Long> {

}
