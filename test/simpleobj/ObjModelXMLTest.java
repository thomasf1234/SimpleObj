/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleobj;

import java.io.IOException;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ad
 */
public class ObjModelXMLTest {
    
    public ObjModelXMLTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of read method, of class ObjModel.
     */
    @Test
    public void testOrder() throws ParserConfigurationException, SAXException, IOException, InvalidObjectXMLException {
        
        String[] validFilePaths = new String[]{"ordered.xml", "unordered.xml"};
        
        for(String validFilePath : validFilePaths) {
            String testXmlPathValid = "test\\samples\\validateOrder\\" + validFilePath;
            org.w3c.dom.Element rootElementValid = XMLUtilities.getRoot(testXmlPathValid);
            org.w3c.dom.Element verticesElementValid = XMLUtilities.getFirstChild(rootElementValid, "vertices");
            org.w3c.dom.Element[] vertexElementsValid = XMLUtilities.getChildren(verticesElementValid, "vertex");

            Element[] vertexElementsOrdered = ObjModelXML.Order(vertexElementsValid);
            assertEquals(3, vertexElementsOrdered.length);   
            assertEquals("0", vertexElementsOrdered[0].getAttribute("index"));  
            assertEquals("1", vertexElementsOrdered[1].getAttribute("index"));  
            assertEquals("2", vertexElementsOrdered[2].getAttribute("index"));  
        }
        
        
        String[] invalidFilePaths = new String[]{"lack_index.xml", "ordered_starts_at_1.xml"};
        
        for(String invalidFilePath : invalidFilePaths) {
            String testXmlPathInvalid = "test\\samples\\validateOrder\\" + invalidFilePath;
            org.w3c.dom.Element rootElementInvalid = XMLUtilities.getRoot(testXmlPathInvalid);
            org.w3c.dom.Element verticesElementInvalid = XMLUtilities.getFirstChild(rootElementInvalid, "vertices");
            org.w3c.dom.Element[] vertexElementsInvalid = XMLUtilities.getChildren(verticesElementInvalid, "vertex");

            try {
              org.w3c.dom.Element[] vertexElementsOrdered = ObjModelXML.Order(vertexElementsInvalid);
              fail("Should have raised InvalidObjectXMLException");
            } catch(InvalidObjectXMLException e) {
                
            }
        }
    }

    @Test
    public void testDeriveVertices() throws Exception {
        String testXmlPath = "test\\samples\\validateOrder\\ordered.xml";
        org.w3c.dom.Element rootElement = XMLUtilities.getRoot(testXmlPath);
        org.w3c.dom.Element verticesElement = XMLUtilities.getFirstChild(rootElement, "vertices");
        org.w3c.dom.Element[] vertexElements = XMLUtilities.getChildren(verticesElement, "vertex");
        org.w3c.dom.Element[] vertexElementsOrdered = ObjModelXML.Order(vertexElements);
        Point3D[] vertices = ObjModelXML.deriveVertices(vertexElementsOrdered);
        
        assertEquals(3, vertices.length);
        double delta = 0.0000000000000001;
        assertEquals(1, vertices[0].getX(), delta);
        assertEquals(0.9999999403953552, vertices[0].getY(), delta);
        assertEquals(-1, vertices[0].getZ(), delta);
        
        assertEquals(1, vertices[1].getX(), delta);
        assertEquals(-1, vertices[1].getY(), delta);
        assertEquals(-1, vertices[1].getZ(), delta);
        
        assertEquals(-1.0000001192092896, vertices[2].getX(), delta);
        assertEquals(-0.9999998211860657, vertices[2].getY(), delta);
        assertEquals(-1, vertices[2].getZ(), delta);
    }

    /**
     * Test of read method, of class ObjModel.
     */
    @Test
    public void testRead() throws Exception {
        String xmlPath = "test\\samples\\Cube.xml";
        ObjModel cube = ObjModelXML.read(xmlPath);
        
        assertEquals("Cube", cube.name);
        assertEquals(8, cube.vertices.length);
        assertEquals(new Point3D(1.0, 0.9999999403953552, -1.0), cube.vertices[0]);
        assertEquals(new Point3D(1.0000004768371582, 0.999999463558197, 1.0), cube.vertices[4]);
        assertEquals(new Point3D(-0.9999999403953552, 1.0, 1.0), cube.vertices[7]);

        assertEquals(12, cube.polygons.length);
        
        //testing first polygon
        assertEquals(true, cube.polygons[0].isTriangle());
        assertEquals(new Point3D(0, 0, -1), cube.polygons[0].normal);
        assertEquals(new Point3D(1, -1, -1), cube.polygons[0].getVertices()[0]);
        assertEquals(new Point2D(1, 0), cube.polygons[0].uv[0]);
        assertEquals(new Point3D(-1.0000001192092896, -0.9999998211860657, -1), cube.polygons[0].getVertices()[1]);
        assertEquals(new Point2D(1, 1), cube.polygons[0].uv[1]);
        assertEquals(new Point3D(-0.9999996423721313, 1.0000003576278687, -1), cube.polygons[0].getVertices()[2]);      
        assertEquals(new Point2D(0, 1), cube.polygons[0].uv[2]);
        
        //testing last polygon
        assertEquals(true, cube.polygons[11].isTriangle());
        assertEquals(new Point3D(2.6822084464583895 * Math.pow(10, -7), 1, 2.3841852225814364 * Math.pow(10, -7)), cube.polygons[11].normal);
        assertEquals(new Point3D(1.0000004768371582, 0.999999463558197, 1), cube.polygons[11].getVertices()[0]);
        assertEquals(new Point2D(0, 0), cube.polygons[11].uv[0]);
        assertEquals(new Point3D(1, 0.9999999403953552, -1), cube.polygons[11].getVertices()[1]);
        assertEquals(new Point2D(1, 0), cube.polygons[11].uv[1]);
        assertEquals(new Point3D(-0.9999999403953552, 1, 1), cube.polygons[11].getVertices()[2]);      
        assertEquals(new Point2D(0, 1), cube.polygons[11].uv[2]);
    }
}
