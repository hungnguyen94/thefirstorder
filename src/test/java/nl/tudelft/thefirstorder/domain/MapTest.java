package nl.tudelft.thefirstorder.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MapTest {

    private Map entity;

    @Mock
    private Camera camera;

    @Mock
    private Player player;

    private static long DEFAULT_ID = 1L;
    private static String DEFAULT_NAME = "AAAAA";

    @Before
    public void setUp() throws Exception {
        entity = new Map();
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
    public void getPlayer() throws Exception {
        Set<Player> players = new HashSet<>();
        players.add(player);
        entity.setPlayers(players);
        assertThat(entity.getPlayers()).isEqualTo(players);
    }

    @Test
    public void getCameras() throws Exception {
        Set<Camera> cameras = new HashSet<>();
        cameras.add(camera);
        entity.setCameras(cameras);
        assertThat(entity.getCameras()).isEqualTo(cameras);
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
        Map entity2 = new Map();
        entity2.setId(entity.getId());
        assertThat(entity.equals(entity2)).isTrue();
    }

    @Test
    public void equalsBranch5() throws Exception {
        entity.setId(DEFAULT_ID);
        Map entity2 = new Map();
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void equalsBranch6() throws Exception {
        Map entity2 = new Map();
        entity2.setId(DEFAULT_ID);
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void hashCodeTest() throws Exception {
        Map entity2 = new Map();
        entity.setId(DEFAULT_ID);
        entity2.setId(DEFAULT_ID);
        assertThat(entity.hashCode() == entity2.hashCode());
    }

    @Test
    public void toStringTest() throws Exception {
        Map entity2 = new Map();
        assertThat(Objects.equals(entity.toString(), entity2.toString()));
    }

    @Test
    public void projectTest() throws Exception {
        Project project = new Project();
        project.setId(DEFAULT_ID);

        entity.setProject(project);
        assertThat(Objects.equals(entity.getProject().getId(), DEFAULT_ID));
    }

}
