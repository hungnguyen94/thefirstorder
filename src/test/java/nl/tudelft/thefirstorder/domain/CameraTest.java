package nl.tudelft.thefirstorder.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class CameraTest {

    private Camera entity;

    @Mock
    private Map map;

    private static long DEFAULT_ID = 1L;
    public static Double DEFAULT_VALUE = 1D;
    private static String DEFAULT_NAME = "AAAAA";

    @Before
    public void setUp() throws Exception {
        entity = new Camera();
    }

    @Test
    public void getId() throws Exception {
        entity.setId(DEFAULT_ID);
        assertThat(entity.getId()).isEqualTo(DEFAULT_ID);
    }

    @Test
    public void getName() throws Exception {
        entity.setName(DEFAULT_NAME);
        assertThat(entity.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void getX() throws Exception {
        entity.setX(DEFAULT_VALUE);
        assertThat(entity.getX()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void getY() throws Exception {
        entity.setY(DEFAULT_VALUE);
        assertThat(entity.getY()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void getMap() throws Exception {
        entity.setMap(map);
        assertThat(entity.getMap()).isEqualTo(map);
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
        Camera entity2 = new Camera();
        entity2.setId(entity.getId());
        assertThat(entity.equals(entity2)).isTrue();
    }

    @Test
    public void equalsBranch5() throws Exception {
        entity.setId(DEFAULT_ID);
        Camera entity2 = new Camera();
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void equalsBranch6() throws Exception {
        Camera entity2 = new Camera();
        entity2.setId(DEFAULT_ID);
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void hashCodeTest() throws Exception {
        Camera entity2 = new Camera();
        entity.setId(DEFAULT_ID);
        entity2.setId(DEFAULT_ID);
        assertThat(entity.hashCode() == entity2.hashCode());
    }

    @Test
    public void toStringTest() throws Exception {
        Camera entity2 = new Camera();
        assertThat(Objects.equals(entity.toString(), entity2.toString()));
    }

}