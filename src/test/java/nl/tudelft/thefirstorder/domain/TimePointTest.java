package nl.tudelft.thefirstorder.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TimePointTest {

    private TimePoint entity;

    private static long DEFAULT_ID = 1L;
    private static int DEFAULT_TIME = 11111;

    @Before
    public void setUp() throws Exception {
        entity = new TimePoint();
    }

    @Test
    public void durationTest() throws Exception {
        entity.setDuration(DEFAULT_TIME);
        assertThat(entity.getDuration()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    public void startTimeTest() throws Exception {
        entity.setStartTime(DEFAULT_TIME);
        assertThat(entity.getStartTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    public void getId() throws Exception {
        entity.setId(DEFAULT_ID);
        assertThat(entity.getId()).isEqualTo(DEFAULT_ID);
    }

    @Test
    public void equalsBranch1() throws Exception {
        assertThat(entity.equals(entity)).isTrue();
    }

    @Test
    public void equalsBranch2() throws Exception {
        assertThat(entity.equals(null)).isFalse();
    }

    @Test
    public void equalsBranch3() throws Exception {
        assertThat(entity.equals(new Object())).isFalse();
    }

    @Test
    public void equalsBranch4() throws Exception {
        entity.setId(DEFAULT_ID);
        TimePoint entity2 = new TimePoint();
        entity2.setId(entity.getId());
        assertThat(entity.equals(entity2)).isTrue();
    }

    @Test
    public void equalsBranch5() throws Exception {
        entity.setId(DEFAULT_ID);
        TimePoint entity2 = new TimePoint();
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void equalsBranch6() throws Exception {
        TimePoint entity2 = new TimePoint();
        entity2.setId(DEFAULT_ID);
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void hashCodeTest() throws Exception {
        TimePoint entity2 = new TimePoint();
        entity.setId(DEFAULT_ID);
        entity2.setId(DEFAULT_ID);
        assertThat(entity.hashCode() == entity2.hashCode());
    }

    @Test
    public void toStringTest() throws Exception {
        TimePoint entity2 = new TimePoint();
        assertThat(Objects.equals(entity.toString(), entity2.toString()));
    }

}