package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.*;
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

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUploadMockMvc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        uploadResource = new UploadResource();
        this.restUploadMockMvc = MockMvcBuilders.standaloneSetup(uploadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

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
    public void writeToFileFailTest() {
        String root = uploadResource.getRoot();
        InputStream stream = new ByteArrayInputStream("foobar".getBytes(StandardCharsets.UTF_8));
        String location = "****";

        uploadResource.writeToFile(location, stream);
        File file = new File(root + location);
        Assert.assertFalse(file.exists());
    }

    @Test
    public void uploadImageTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", UploadResource.CONTENT_TYPE_IMG, "foobar".getBytes());

        restUploadMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/upload/image")
            .file(file))
            .andExpect(status().isOk());
    }

    @Test
    public void uploadPDFToImageTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", UploadResource.CONTENT_TYPE_PDF, "foobar".getBytes());

        restUploadMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/upload/image")
            .file(file))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void uploadPDFTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", UploadResource.CONTENT_TYPE_PDF, "foobar".getBytes());

        restUploadMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/upload/pdf")
            .file(file))
            .andExpect(status().isOk());
    }

    @Test
    public void uploadImageTOPDFTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", UploadResource.CONTENT_TYPE_IMG, "foobar".getBytes());

        restUploadMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/upload/pdf")
            .file(file))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getRandomNameTest() {
        Assert.assertNotNull(uploadResource.getRandomName());
    }
}
