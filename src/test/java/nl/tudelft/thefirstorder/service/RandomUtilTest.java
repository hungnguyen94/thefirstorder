package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.service.util.RandomUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Martin on 26-5-2016.
 */
public class RandomUtilTest {

    @Test
    public void generatePasswordTest() {
        assertNotEquals(RandomUtil.generatePassword(), RandomStringUtils.randomAlphanumeric(20));
    }

    @Test
    public void generateActivationKeyTest() {
        assertNotEquals(RandomUtil.generateActivationKey(), RandomStringUtils.randomAlphanumeric(20));
    }

    @Test
    public void generateResetKey() {
        assertNotEquals(RandomUtil.generateResetKey(), RandomStringUtils.randomAlphanumeric(20));
    }

}
