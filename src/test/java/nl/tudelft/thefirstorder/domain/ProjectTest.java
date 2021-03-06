package nl.tudelft.thefirstorder.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectTest {

    private Project entity;

    private static long DEFAULT_ID = 1L;
    private static String DEFAULT_NAME = "AAAAA";

    @Before
    public void setUp() throws Exception {
        entity = new Project();
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
        Project entity2 = new Project();
        entity2.setId(entity.getId());
        assertThat(entity.equals(entity2)).isTrue();
    }

    @Test
    public void equalsBranch5() throws Exception {
        entity.setId(DEFAULT_ID);
        Project entity2 = new Project();
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void equalsBranch6() throws Exception {
        Project entity2 = new Project();
        entity2.setId(DEFAULT_ID);
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    public void hashCodeTest() throws Exception {
        Project entity2 = new Project();
        entity.setId(DEFAULT_ID);
        entity2.setId(DEFAULT_ID);
        assertThat(entity.hashCode() == entity2.hashCode());
    }

    @Test
    public void toStringTest() throws Exception {
        Project entity2 = new Project();
        assertThat(Objects.equals(entity.toString(), entity2.toString()));
    }

    @Test
    public void cueTest() throws Exception {
        Set<Cue> cues = new HashSet<>();

        cues.add(new Cue());
        entity.setCues(cues);

        assertThat(Objects.equals(entity.getCues().size(), cues.size()));
    }
}
