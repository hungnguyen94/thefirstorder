package nl.tudelft.thefirstorder.models;

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

    /**
     * Returns the url of the image object.
     * @return String of the url.
     */
    public String getUrl() {
        return url;
    }
}
