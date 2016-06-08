package nl.tudelft.thefirstorder.service.util;

import com.itextpdf.text.*;
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
            //addTitlePage(document, project);
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
     * Add a front page to the pdf.
     * @param document the documents to which the data has to be added
     * @param project the project from which the data is exported
     * @throws DocumentException if something wrong is added to the document
     */
    private static void addTitlePage(Document document, Project project)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        Script script = project.getScript();

        try {
            Image image = Image.getInstance("src/main/java/nl/tudelft/thefirstorder/service/util/lightsaber.jpg");
            preface.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addEmptyLine(preface, 1);
        Paragraph par = new Paragraph(script.getName(), catFont);
        preface.add(par);
        par.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(preface, 1);
        addEmptyLine(preface, 3);
        preface.add(new Paragraph("",
                smallBold));
        addEmptyLine(preface, 8);
        preface.add(new Paragraph("",
                redFont));
        document.add(preface);
        document.newPage();
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
        table.getDefaultCell().setPaddingBottom(15);
        table.getDefaultCell().setPaddingTop(15);
        table.getDefaultCell().setBorder(0);
        PdfPTable cameratable = makeCameraTable();
        cameratable.getDefaultCell().setBorder(0);
        cameratable.getDefaultCell().setPaddingBottom(15);
        cameratable.getDefaultCell().setPaddingTop(15);

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
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(0);
        c1.setPaddingTop(15);
        c1.setPaddingBottom(15);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Camera"));
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(0);
        c1.setPaddingTop(15);
        c1.setPaddingBottom(15);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Player"));
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(0);
        c1.setPaddingTop(15);
        c1.setPaddingBottom(15);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Camera Action"));
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(0);
        c1.setPaddingTop(15);
        c1.setPaddingBottom(15);
        table.addCell(c1);

        return table;
    }

    private static PdfPTable makeCameraTable() {
        PdfPTable cameratable = new PdfPTable(3);

        PdfPCell c1 = new PdfPCell(new Phrase("Camera"));
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(0);
        c1.setPaddingTop(15);
        c1.setPaddingBottom(15);
        cameratable.addCell(c1);

        c1 = new PdfPCell(new Phrase("Camera Type"));
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(0);
        c1.setPaddingTop(15);
        c1.setPaddingBottom(15);
        cameratable.addCell(c1);

        c1 = new PdfPCell(new Phrase("Lens Type"));
        c1.setBackgroundColor(BaseColor.GRAY);
        c1.setBorder(0);
        c1.setPaddingTop(15);
        c1.setPaddingBottom(15);
        cameratable.addCell(c1);

        return cameratable;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
