package nl.tudelft.thefirstorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TimePoint. Represents the starting time and duration of a bar in a Cue.
 */
@Entity
@Table(name = "time_point")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimePoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "duration")
    private Integer duration;

    @OneToOne(mappedBy = "timePoint", cascade = CascadeType.ALL)
    @JsonIgnore
    private Cue cue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for start time.
     * @return Integer representing the start time.
     */
    public Integer getStartTime() {
        return startTime;
    }

    /**
     * Setter for start time.
     * @param startTime Starting time in an integer.
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for duration.
     * @return Duration of this bar.
     */
    public Integer getDuration() {
        return duration;
    }


    /**
     * Setter for duration.
     * @param duration Duration as an integer.
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Getter for Cue.
     * @return Cue that owns this TimePoint.
     */
    public Cue getCue() {
        return cue;
    }

    /**
     * Setter for Cue.
     * @param cue Sets cue that owns this TimePoint.
     */
    public void setCue(Cue cue) {
        this.cue = cue;
    }

    /**
     * Equals method for timePoint.
     * @param o Object to compare.
     * @return True if object is equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimePoint timePoint = (TimePoint) o;
        if (timePoint.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, timePoint.id);
    }

    /**
     * Generate hashcode for this class.
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * ToString method.
     * @return String representation for this class.
     */
    @Override
    public String toString() {
        return "TimePoint{"
                + "id=" + id
                + ", startTime='" + startTime + "'"
                + ", duration='" + duration + "'"
                + '}';
    }
}
