package nl.tudelft.thefirstorder.service.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.core.io.Resource;

import nl.tudelft.thefirstorder.domain.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLExportUtil {

    public static Resource export(Project project) {
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElementNS("http://crunchify.com/CrunchifyCreateXMLDOM", "Companies");
            doc.appendChild(mainRootElement);

            // append child elements to root element
            mainRootElement.appendChild(getCamera(doc, "1", "Paypal", "Payment", "1000"));
            mainRootElement.appendChild(getCamera(doc, "2", "eBay", "Shopping", "2000"));
            mainRootElement.appendChild(getCamera(doc, "3", "Google", "Search", "3000"));

            // output DOM XML to console
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);

            System.out.println("\nXML DOM Created Successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Node getCamera(Document doc, String id, String name, String age, String role) {
        Element company = doc.createElement("Company");
        company.setAttribute("id", id);
        company.appendChild(getCompanyElements(doc, company, "Name", name));
        company.appendChild(getCompanyElements(doc, company, "Type", age));
        company.appendChild(getCompanyElements(doc, company, "Employees", role));
        return company;
    }

    // utility method to create text node
    private static Node getCompanyElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
