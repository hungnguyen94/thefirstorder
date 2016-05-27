package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Map entity.
 */
public interface MapRepository extends JpaRepository<Map,Long> {

    List<Map> findByProjectIsNull();

}
