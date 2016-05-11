package nl.tudelft.thefirstorder.domain;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Persist AuditEvent managed by the Spring Boot actuator
 * @see org.springframework.boot.actuate.audit.AuditEvent
 */
@Entity
@Table(name = "jhi_persistent_audit_event")
public class PersistentAuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String principal;

    @Column(name = "event_date")
    private LocalDateTime auditEventDate;
    @Column(name = "event_type")
    private String auditEventType;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "jhi_persistent_audit_evt_data", joinColumns = @JoinColumn(name = "event_id"))
    private Map<String, String> data = new HashMap<>();

    /**
     * Get the id of the audit event.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the audit event.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the principal of the audit event.
     * @return the principal
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * Set the principal of the audit event.
     * @param principal the principal
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * Get the date of the audit event.
     * @return the date
     */
    public LocalDateTime getAuditEventDate() {
        return auditEventDate;
    }

    /**
     * Set the date of the audit event.
     * @param auditEventDate the date
     */
    public void setAuditEventDate(LocalDateTime auditEventDate) {
        this.auditEventDate = auditEventDate;
    }

    /**
     * Get the type of the audit event.
     * @return the type
     */
    public String getAuditEventType() {
        return auditEventType;
    }

    /**
     * Set the type of the audit event.
     * @param auditEventType the type
     */
    public void setAuditEventType(String auditEventType) {
        this.auditEventType = auditEventType;
    }

    /**
     * Get the data of the audit event.
     * @return the data
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * Set the data of the audit event.
     * @param data the data
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
