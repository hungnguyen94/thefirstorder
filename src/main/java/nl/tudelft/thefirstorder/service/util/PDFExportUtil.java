package nl.tudelft.thefirstorder.service.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Project;
import nl.tudelft.thefirstorder.domain.Script;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

/**
 * Utility class to export a project to a PDF File.
 */
public class PDFExportUtil {

    private static Font catFont = new Font(Font.FontFamily.COURIER, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.COURIER, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font smallBold = new Font(Font.FontFamily.COURIER, 12,
            Font.BOLD);

    private static final int BORDER_CONSTANT = 0;
    private static final int PADDING_CONSTANT = 15;

    public PDFExportUtil() {
    }


    /**
     * Export a project to pdf.
     * @param project the project
     */
    public static Resource exportProjectToPDF(Project project) {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            addMetaData(document, project);
            addContent(document, project);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Resource resource = new ByteArrayResource(baos.toByteArray());

        return resource;
    }

    /**
     * Add the meta data to the pdf.
     * @param document the document to which the data has to be added
     */
    private static void addMetaData(Document document, Project project) {
        document.addTitle(project.getScript().getName());
        document.addSubject("Script");
        document.addKeywords("Script");
        document.addAuthor("The First Order");
        document.addCreator("The First Order");
    }

    /**
     * Add the content to the pdf (The cues, cameras and actions).
     * @param document the document to which the content has to be added
     * @param project the project from which the content is exported
     * @throws DocumentException if something wrong is added to the document
     */
    private static void addContent(Document document, Project project) throws DocumentException {
        Paragraph paragraph = new Paragraph(project.getScript().getName());
        Font font = paragraph.getFont();
        font.setStyle(Font.BOLD);
        paragraph.setFont(font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        PdfPTable table = makeCueTable();
        table.getDefaultCell().setPaddingBottom(PADDING_CONSTANT);
        table.getDefaultCell().setPaddingTop(PADDING_CONSTANT);
        table.getDefaultCell().setBorder(BORDER_CONSTANT);
        PdfPTable cameratable = makeCameraTable();
        cameratable.getDefaultCell().setBorder(BORDER_CONSTANT);
        cameratable.getDefaultCell().setPaddingBottom(PADDING_CONSTANT);
        cameratable.getDefaultCell().setPaddingTop(PADDING_CONSTANT);

        table.setHeaderRows(1);
        cameratable.setHeaderRows(1);
        Script script = project.getScript();
        Set<Cue> cues = script.getCues();
        int index = 1;
        Iterator<Cue> iterator = cues.iterator();
        while (iterator.hasNext()) {
            Cue cue = iterator.next();
            table.addCell(index + ".");
            table.addCell(cue.getCamera().getName());
            table.addCell(cue.getPlayer().getName());
            table.addCell(cue.getAction());
            Camera camera = cue.getCamera();
            cameratable.addCell(camera.getName());
            cameratable.addCell(camera.getCameraType());
            cameratable.addCell(camera.getLensType());
            index++;
        }
        Paragraph par = new Paragraph();
        par.add(new Paragraph("Cues"));
        addEmptyLine(par, 2);
        par.add(table);
        addEmptyLine(par, 3);
        par.add(new Paragraph("Cameras"));
        addEmptyLine(par, 2);
        par.add(cameratable);
        document.add(par);
    }

    private static PdfPTable makeCueTable() {
        PdfPTable table = new PdfPTable(4);

        PdfPCell c1 = new PdfPCell(new Phrase("Shot"));
        c1 = setSetting(c1);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Camera"));
        c1 = setSetting(c1);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Player"));
        c1 = setSetting(c1);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Camera Action"));
        c1 = setSetting(c1);
        table.addCell(c1);

        return table;
    }

    private static PdfPTable makeCameraTable() {
        PdfPTable cameratable = new PdfPTable(3);

        PdfPCell c1 = new PdfPCell(new Phrase("Camera"));
        c1 = setSetting(c1);
        cameratable.addCell(c1);

        c1 = new PdfPCell(new Phrase("Camera Type"));
        c1 = setSetting(c1);
        cameratable.addCell(c1);

        c1 = new PdfPCell(new Phrase("Lens Type"));
        c1 = setSetting(c1);
        cameratable.addCell(c1);

        return cameratable;
    }

    private static PdfPCell setSetting(PdfPCell c1) {
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(BORDER_CONSTANT);
        c1.setPaddingTop(PADDING_CONSTANT);
        c1.setPaddingBottom(PADDING_CONSTANT);
        return c1;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
