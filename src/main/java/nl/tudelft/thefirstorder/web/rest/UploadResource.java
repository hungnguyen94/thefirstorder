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

import java.io.*;
import java.util.Random;

/**
 * REST controller for managing uploads.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {
    private final Logger log = LoggerFactory.getLogger(UploadResource.class);

    @RequestMapping(value = "/upload",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> uploadFile(@RequestBody(required = true) MultipartFile file) throws IOException {
        String res = writeToFile(file.getInputStream());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("Uploaded", "image"))
            .body("{\"location\" : \"" + res + "\"}");
    }

    private static String writeToFile(InputStream contents) {
        Random random = new Random();
        String root = "src/main/webapp/";
        String location = "content/upload/" + random.nextInt(1000000000) + ".jpg";

        String path = root + location;

        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(new File(path));
            while ((read = contents.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }
}
