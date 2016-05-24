package nl.tudelft.thefirstorder.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private User entity;

    private static long DEFAULT_ID = 1L;
    private static String DEFAULT_LOGIN = "AAAAA";
    private static String OTHER_LOGIN = "BBBBB";

    @Before
    public void setUp() throws Exception {
        entity = new User();
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
        entity.setLogin(DEFAULT_LOGIN);
        User entity2 = new User();
        entity2.setLogin(DEFAULT_LOGIN);
        assertThat(entity.equals(entity2)).isTrue();
    }

    @Test
    public void equalsBranch5() throws Exception {
        entity.setLogin(DEFAULT_LOGIN);
        User entity2 = new User();
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void equalsBranch6() throws Exception {
        entity.setLogin(OTHER_LOGIN);
        User entity2 = new User();
        entity2.setLogin(DEFAULT_LOGIN);
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void hashCodeTest() throws Exception {
        User entity2 = new User();
        entity.setLogin(DEFAULT_LOGIN);
        entity2.setLogin(DEFAULT_LOGIN);
        assertThat(entity.hashCode() == entity2.hashCode());
    }

    @Test
    public void toStringTest() throws Exception {
        User entity2 = new User();
        assertThat(Objects.equals(entity.toString(), entity2.toString()));
    }

}