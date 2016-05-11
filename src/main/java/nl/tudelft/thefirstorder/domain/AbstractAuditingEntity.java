package nl.tudelft.thefirstorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    /**
     * Get the creator of the entity.
     * @return the creator
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the creator of the entity.
     * @param createdBy the creator
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get the date of creation of the entity.
     * @return the date
     */
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the date of creation of the entity.
     * @param createdDate the date
     */
    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get the last modified user of the entity.
     * @return the user
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Set the last modified user of the entity.
     * @param lastModifiedBy the user
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Get the last modified date of the entity.
     * @return the date
     */
    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Set the last modified date of the entity.
     * @param lastModifiedDate the date
     */
    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
