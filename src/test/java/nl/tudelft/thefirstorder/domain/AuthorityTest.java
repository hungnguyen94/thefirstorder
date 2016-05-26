package nl.tudelft.thefirstorder.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorityTest {

    private Authority entity;

    private static long DEFAULT_ID = 1L;
    private static String DEFAULT_NAME = "AAAAA";

    @Before
    public void setUp() throws Exception {
        entity = new Authority();
    }

    @Test
    public void getName() throws Exception {
        entity.setName(DEFAULT_NAME);
        assertThat(entity.getName()).isEqualTo(DEFAULT_NAME);
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
        entity.setName(DEFAULT_NAME);
        Authority entity2 = new Authority();
        entity2.setName(entity.getName());
        assertThat(entity.equals(entity2)).isTrue();
    }

    @Test
    public void equalsBranch5() throws Exception {
        entity.setName(DEFAULT_NAME);
        Authority entity2 = new Authority();
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void equalsBranch6() throws Exception {
        Authority entity2 = new Authority();
        entity2.setName(DEFAULT_NAME);
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void hashCodeTest() throws Exception {
        Authority entity2 = new Authority();
        entity.setName(DEFAULT_NAME);
        entity2.setName(DEFAULT_NAME);
        assertThat(entity.hashCode() == entity2.hashCode());
    }

    @Test
    public void toStringTest() throws Exception {
        Authority entity2 = new Authority();
        assertThat(Objects.equals(entity.toString(), entity2.toString()));
    }

}