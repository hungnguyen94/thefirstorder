package nl.tudelft.thefirstorder.model;

/**
 * Stores an image.
 */
public class Image {
    private String url;
    private int width;
    private int height;

    /**
     * Constructor for the image class.
     * @param url a String of the url of the image.
     */
    public Image(String url) {
        this.url = url;
    }
}
