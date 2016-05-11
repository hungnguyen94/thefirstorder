package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.config.audit.AuditEventConverter;
import nl.tudelft.thefirstorder.repository.PersistenceAuditEventRepository;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    private AuditEventConverter auditEventConverter;

    /**
     * Constructor for AuditEventService.
     * @param persistenceAuditEventRepository Repository
     * @param auditEventConverter Converter
     */
    @Inject
    public AuditEventService(
        PersistenceAuditEventRepository persistenceAuditEventRepository,
        AuditEventConverter auditEventConverter) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    /**
     * Find all the audit events on a page.
     * @param pageable the page
     * @return the events
     */
    public Page<AuditEvent> findAll(Pageable pageable) {
        return persistenceAuditEventRepository.findAll(pageable)
            .map(persistentAuditEvents -> auditEventConverter.convertToAuditEvent(persistentAuditEvents));
    }

    /**
     * Find audit events with a date specification
     * @param fromDate after this date
     * @param toDate before this date
     * @param pageable the page with events
     * @return the events which are between the two dates
     */
    public Page<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate, pageable)
            .map(persistentAuditEvents -> auditEventConverter.convertToAuditEvent(persistentAuditEvents));
    }

    /**
     * Find the audit event by id.
     * @param id the id
     * @return the audit event
     */
    public Optional<AuditEvent> find(Long id) {
        return Optional.ofNullable(persistenceAuditEventRepository.findOne(id)).map(
                auditEventConverter::convertToAuditEvent);
    }
}
