package nl.tudelft.thefirstorder.domain;

import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractAuditingTest {
    private AbstractAuditingEntity entity;

    private static final String DEFAULT_NAME = "foo";
    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.now();

    @Before
    public void setUp() throws Exception {
        entity = new AbstractAuditingEntity() {
            @Override
            public String getCreatedBy() {
                return super.getCreatedBy();
            }
        };
    }

    @Test
    public void createdByTest() throws Exception {
        entity.setCreatedBy(DEFAULT_NAME);
        assertThat(Objects.equals(entity.getCreatedBy(), DEFAULT_NAME));
    }

    @Test
    public void createdDateTest() throws Exception {
        entity.setCreatedDate(DEFAULT_DATE);
        assertThat(Objects.equals(entity.getCreatedDate(), DEFAULT_DATE));
    }

    @Test
    public void modifiedByTest() throws Exception {
        entity.setLastModifiedBy(DEFAULT_NAME);
        assertThat(Objects.equals(entity.getLastModifiedBy(), DEFAULT_NAME));
    }

    @Test
    public void lastModifiedDateTest() throws Exception {
        entity.setLastModifiedDate(DEFAULT_DATE);
        assertThat(Objects.equals(entity.getLastModifiedDate(), DEFAULT_DATE));
    }
}
