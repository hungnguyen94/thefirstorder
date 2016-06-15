package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.Multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the UploadResource REST controller.
 *
 * @see UploadResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class UploadResourceIntTest {

    @Inject
    private UploadResource uploadResource;

    @Test
    public void writeToFileTest() throws Exception {
        String root = uploadResource.getRoot();
        InputStream stream = new ByteArrayInputStream("foobar".getBytes(StandardCharsets.UTF_8));
        String location = "foo";

        uploadResource.writeToFile(location, stream);
        File file = new File(root + location);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void getRandomNameTest() {
        Assert.assertNotNull(uploadResource.getRandomName());
    }
}
