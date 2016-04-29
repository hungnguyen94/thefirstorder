package nl.tudelft.thefirstorder.spring.models;

import org.junit.Test;

/**
 * Created by rubenwiersma on 29/04/16.
 */
public class ImageTest {
    @Test
    public void ImageTest() {
        Image image = new Image("test");
        assert(image.getUrl().equals("test"));
    }
}
