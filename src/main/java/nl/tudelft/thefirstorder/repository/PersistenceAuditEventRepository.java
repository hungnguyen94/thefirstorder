package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.PersistentAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

    /**
     * Find all audit events by a principal.
     * @param principal the principal
     * @return a list of audit events
     */
    List<PersistentAuditEvent> findByPrincipal(String principal);

    /**
     * Find all audit events by a principal which are dated after a certain date.
     * @param principal the principal
     * @param after the date
     * @return a list of audit events.
     */
    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

    /**
     * Find all audits between two dates.
     * @param fromDate the first date
     * @param toDate the last date
     * @param pageable a object for the pages
     * @return a page of audits
     */
    Page<PersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate,
                                                              LocalDateTime toDate,
                                                              Pageable pageable);
}
