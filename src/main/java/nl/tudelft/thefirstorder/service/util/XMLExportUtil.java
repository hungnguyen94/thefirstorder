package nl.tudelft.thefirstorder.service.util;

import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Project;
import nl.tudelft.thefirstorder.domain.Script;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;

/**
 * Export a project to a XML file.
 */
public class XMLExportUtil {

    /**
     * Constructor.
     */
    public XMLExportUtil() {
    }

    /**
     * Export the project.
     * @param project the project
     * @return A resource to be downloaded
     */
    public static Resource exportProjectToXML(Project project) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElement("Project");
            mainRootElement.setAttribute("name",project.getName());
            doc.appendChild(mainRootElement);

            Script script = project.getScript();
            Element scriptNode = doc.createElement("Script");
            Element scriptNameNode = doc.createElement("Name");
            scriptNameNode.appendChild(doc.createTextNode(script.getName()));
            scriptNode.appendChild(scriptNameNode);

            mainRootElement.appendChild(scriptNode);

            // append child elements to root element
            Iterator<Cue> iterator = script.getCues().iterator();
            while (iterator.hasNext()) {
                Element cueNode = doc.createElement("Cue");
                Cue cue = iterator.next();
                Element cueId = doc.createElement("Id");
                cueId.appendChild(doc.createTextNode(cue.getId() + ""));
                Element cameraNode = getCamera(doc, cue);
                Element cameraActionNode = getCameraAction(doc, cue);
                Element playerNode = getPlayer(doc, cue);
                Element timeNode = getTimePoint(doc, cue);
                cueNode.appendChild(cueId);
                cueNode.appendChild(cameraNode);
                cueNode.appendChild(cameraActionNode);
                cueNode.appendChild(playerNode);
                cueNode.appendChild(timeNode);
                scriptNode.appendChild(cueNode);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            // identity
            Transformer trans = tf.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.transform(new DOMSource(mainRootElement), new StreamResult(baos));

        } catch (Exception e) {
            e.printStackTrace();
        }
        Resource resource = new ByteArrayResource(baos.toByteArray());
        return resource;
    }

    /**
     * Get the information of a camera.
     * @param doc The document for which the data is extracted.
     * @param cue The camera from this cue
     * @return An element with the information
     */
    private static Element getCamera(Document doc, Cue cue) {
        Element cameraId = doc.createElement("Id");
        cameraId.appendChild(doc.createTextNode(cue.getCamera().getId() + ""));
        Element cameraName = doc.createElement("Name");
        cameraName.appendChild(doc.createTextNode(cue.getCamera().getName()));
        Element cameraX = doc.createElement("X-Position");
        cameraX.appendChild(doc.createTextNode(cue.getCamera().getX() + ""));
        Element cameraY = doc.createElement("Y-Position");
        cameraY.appendChild(doc.createTextNode(cue.getCamera().getY() + ""));
        Element cameraCameraType = doc.createElement("Camera-Type");
        cameraCameraType.appendChild(doc.createTextNode(cue.getCamera().getCameraType() + ""));
        Element cameraLensType = doc.createElement("Lens-Type");
        cameraLensType.appendChild(doc.createTextNode(cue.getCamera().getLensType() + ""));
        Element cameraNode = doc.createElement("Camera");
        cameraNode.appendChild(cameraId);
        cameraNode.appendChild(cameraName);
        cameraNode.appendChild(cameraX);
        cameraNode.appendChild(cameraY);
        cameraNode.appendChild(cameraCameraType);
        cameraNode.appendChild(cameraLensType);
        return cameraNode;
    }

    /**
     * Get the information of a camera action.
     * @param doc The document for which the data is extracted.
     * @param cue The camera action from this cue
     * @return An element with the information
     */
    private static Element getCameraAction(Document doc, Cue cue) {
        Element cameraName = doc.createElement("Name");
        cameraName.appendChild(doc.createTextNode(cue.getAction()));
        Element cameraNode = doc.createElement("Camera-Action");
        cameraNode.appendChild(cameraName);
        return cameraNode;
    }

    /**
     * Get the information of a time point.
     * @param doc The document for which the data is extracted.
     * @param cue The time point from this cue
     * @return An element with the information
     */
    private static Element getTimePoint(Document doc, Cue cue) {
        Element timeStart = doc.createElement("Bar");
        timeStart.appendChild(doc.createTextNode(cue.getBar() + ""));
        Element timeDuration = doc.createElement("Duration");
        timeDuration.appendChild(doc.createTextNode(cue.getDuration() + ""));
        Element timeNode = doc.createElement("Time-Point");
        timeNode.appendChild(timeStart);
        timeNode.appendChild(timeDuration);
        return timeNode;
    }

    /**
     * Get the information of a player.
     * @param doc The document for which the data is extracted.
     * @param cue The player from this cue
     * @return An element with the information
     */
    private static Element getPlayer(Document doc, Cue cue) {
        Element playerId = doc.createElement("Id");
        playerId.appendChild(doc.createTextNode(cue.getPlayer().getId() + ""));
        Element playerName = doc.createElement("Name");
        playerName.appendChild(doc.createTextNode(cue.getPlayer().getName()));
        Element playerX = doc.createElement("X-Position");
        playerX.appendChild(doc.createTextNode(cue.getPlayer().getX() + ""));
        Element playerY = doc.createElement("Y-Position");
        playerY.appendChild(doc.createTextNode(cue.getPlayer().getY() + ""));
        Element playerNode = doc.createElement("Player");
        playerNode.appendChild(playerId);
        playerNode.appendChild(playerName);
        playerNode.appendChild(playerX);
        playerNode.appendChild(playerY);
        return playerNode;
    }

}
