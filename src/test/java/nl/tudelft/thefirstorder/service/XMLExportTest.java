package nl.tudelft.thefirstorder.service;
import nl.tudelft.thefirstorder.domain.*;
import nl.tudelft.thefirstorder.service.util.XMLExportUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Class which tests the XML Export.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class XMLExportTest {

    @Mock private Project project;
    @Mock private Cue cue;
    @Mock private Document document;
    @Mock private Element element1;
    @Mock private Element element2;
    @Mock private Element element3;
    @Mock private Element element4;
    @Mock private Element special;
    @Mock private Text text;
    @Mock private Camera camera;
    @Mock private CameraAction action;
    @Mock private TimePoint time;
    @Mock private Player player;
    @Mock private Script script;

    @Test
    public void exportProjectToXMLTest(){
        XMLExportUtil util = new XMLExportUtil();
        when(cue.getCamera()).thenReturn(camera);
        when(camera.getId()).thenReturn(new Long(1));
        when(camera.getName()).thenReturn("Camera");
        when(camera.getX()).thenReturn(1);
        when(camera.getY()).thenReturn(1);
        when(document.createElement(any(String.class))).thenReturn(special);
        when(document.createTextNode(any(String.class))).thenReturn(text);
        when(cue.getCameraAction()).thenReturn(action);
        when(action.getId()).thenReturn(new Long(1));
        when(action.getName()).thenReturn("Camera");
        when(cue.getTimePoint()).thenReturn(time);
        when(time.getId()).thenReturn(new Long(1));
        when(time.getStartTime()).thenReturn(1);
        when(time.getDuration()).thenReturn(1);
        when(cue.getPlayer()).thenReturn(player);
        when(player.getId()).thenReturn(new Long(1));
        when(player.getName()).thenReturn("Player");
        when(player.getX()).thenReturn(1);
        when(player.getY()).thenReturn(1);
        when(project.getName()).thenReturn("Project");
        when(project.getScript()).thenReturn(script);
        when(script.getName()).thenReturn("Script");
        Set<Cue> cues = new HashSet<Cue>();
        cues.add(cue);
        when(script.getCues()).thenReturn(cues);
        XMLExportUtil.exportProjectToXML(project);
    }

}
