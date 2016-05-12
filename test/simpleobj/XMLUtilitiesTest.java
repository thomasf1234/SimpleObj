package simpleobj;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 *
 * @author ad
 */
public class XMLUtilitiesTest {
    
    public XMLUtilitiesTest() {
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

    @Test
    public void testGetRoot() throws ParserConfigurationException, SAXException, IOException {
        String testXmlPath = "test\\samples\\test.xml";
        org.w3c.dom.Element rootElement = XMLUtilities.getRoot(testXmlPath);
        assertEquals("root", rootElement.getNodeName());
    }
    
    @Test
    public void testGetFirstChild() throws ParserConfigurationException, SAXException, IOException {
        String testXmlPath = "test\\samples\\test.xml";
        org.w3c.dom.Element rootElement = XMLUtilities.getRoot(testXmlPath);
        org.w3c.dom.Element childElement = XMLUtilities.getFirstChild(rootElement, "vertex");
        assertEquals("vertex", childElement.getNodeName());
        assertEquals("1", childElement.getAttribute("index"));
        
        //when the child is not found return null
        assertEquals(null, XMLUtilities.getFirstChild(rootElement, "nonexistent"));
    }
    
    @Test
    public void testGetChildren() throws ParserConfigurationException, SAXException, IOException {
        String testXmlPath = "test\\samples\\test.xml";
        org.w3c.dom.Element rootElement = XMLUtilities.getRoot(testXmlPath);
        org.w3c.dom.Element[] childElements = XMLUtilities.getChildren(rootElement, "vertex");
        assertEquals(2, childElements.length);
        assertEquals("vertex", childElements[0].getNodeName());
        assertEquals("1", childElements[0].getAttribute("index"));
        assertEquals("vertex", childElements[1].getNodeName());
        assertEquals("2", childElements[1].getAttribute("index"));
        
        //when children are not found, return empty Element array
        org.w3c.dom.Element[] noChildElements = XMLUtilities.getChildren(rootElement, "nonexistent");
        assertEquals(0, noChildElements.length); 
    }
}
