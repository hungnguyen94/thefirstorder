package nl.tudelft.thefirstorder.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.tudelft.thefirstorder.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Random;

/**
 * REST controller for managing uploads.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {
    private final Logger log = LoggerFactory.getLogger(UploadResource.class);
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_IMG = "image/jpeg";
    public static final String FILE_ROOT = "src/main/webapp/";

    /**
     * Uploads the given multipart file to the server.
     *
     * @param file should be a multipart file, containing an image
     * @return the location of the created file with the web application folder as root
     * @throws IOException when the file could not be created or the data could not be written to the file.
     */
    @RequestMapping(value = "/upload/image",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> uploadImage(@RequestBody(required = true) MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (contentType.equals(CONTENT_TYPE_IMG)) {
            String location = "content/upload/" + getRandomName() + ".jpg";
            writeToFile(location, file.getInputStream());

            return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("Uploaded", "image"))
                .body("{\"location\" : \"" + location + "\"}");
        }
        return ResponseEntity.badRequest().body("Not an image: " + contentType);
    }

    /**
     * Uploads the given multipart file to the server.
     *
     * @param file should be a multipart file, containing a pdf file
     * @return the location of the created file with the web application folder as root
     * @throws IOException when the file could not be created or the data could not be written to the file.
     */
    @RequestMapping(value = "/upload/pdf",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> uploadPDF(@RequestBody(required = true) MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (contentType.equals(CONTENT_TYPE_PDF)) {
            String location = "content/upload/" + getRandomName() + ".pdf";
            writeToFile(location, file.getInputStream());

            return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("Uploaded", "pdf"))
                .body("{\"location\" : \"" + location + "\"}");
        }
        return ResponseEntity.badRequest().body("Not a pdf file: " + contentType);
    }

    private static final int IMAGE_CHARS = 1024;

    /**
     * Writes the given input stream to a file.
     *
     * @param contents should be the input stream written to the file
     */
    public void writeToFile(String location, InputStream contents) {
        try {
            int read = 0;
            byte[] bytes = new byte[IMAGE_CHARS];

            OutputStream out = new FileOutputStream(new File(getRoot() + location));
            while ((read = contents.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the root of the web application, from the root of the project.
     *
     * @return a String representing the path to the web application
     */
    public String getRoot() {
        return FILE_ROOT;
    }

    private static final int NUM_BITS = 256;
    private static final int CHARS = 32;

    /**
     * Returns a random name to be assigned to the uploaded file.
     *
     * @return a random alphanumeric string
     */
    public String getRandomName() {
        Random random = new Random();
        return new BigInteger(NUM_BITS, random).toString(CHARS);
    }
}
