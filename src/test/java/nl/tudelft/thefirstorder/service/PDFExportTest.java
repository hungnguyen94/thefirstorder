package nl.tudelft.thefirstorder.service;

/**
 * Created by Martin on 18-5-2016.
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Project;
import nl.tudelft.thefirstorder.domain.Script;
import nl.tudelft.thefirstorder.service.util.PDFExportUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Class which tests the Player class.
 *
 * @author Martin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PDFExportTest {

    @Mock private HttpServletResponse response;
    @Mock private Project project;
    @Mock private Document document;
    @Mock private Script script;
    @Mock private Paragraph paragraph;

    @Test
    public void addMetaDataTest() {
        when(project.getScript()).thenReturn(script);
        when(script.getName()).thenReturn("Script");
        PDFExportUtil.addMetaData(document,project);
        verify(document).addTitle("Script");
        verify(document).addSubject("Script");
        verify(document).addKeywords("Script");
        verify(document).addAuthor("The First Order");
        verify(document).addCreator("The First Order");
    }

    @Test
    public void addTitlePageTest() throws DocumentException {
        when(project.getScript()).thenReturn(script);
        when(script.getName()).thenReturn("Script");
        PDFExportUtil.addTitlePage(document,project);
        verify(document).add(any(Paragraph.class));
        verify(document).newPage();
    }

    @Test
    public void addContentTest() throws DocumentException {
        when(project.getScript()).thenReturn(script);
        when(script.getCues()).thenReturn(new HashSet<Cue>());
        PDFExportUtil.addContent(document,project);
        verify(script).getCues();
        verify(document).add(any(Paragraph.class));
    }

    @Test
    public void addEmptyLine() {
        PDFExportUtil.addEmptyLine(paragraph,1);
        verify(paragraph).add(any(Paragraph.class));
        verifyZeroInteractions(paragraph);
    }

}
