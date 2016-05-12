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
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ad
 */
public class ObjModel {

    public String name;
    public Point3D[] vertices;

    public static ObjModel read(String xmlPath) throws ParserConfigurationException, SAXException, IOException, InvalidObjectXMLException {
        Element rootElement = XMLUtilities.getRoot(xmlPath);
        Element objectElement = XMLUtilities.getFirstChild(rootElement, "object");

        String name = objectElement.getAttribute("name");

        Element verticesElement = XMLUtilities.getFirstChild(objectElement, "vertices");
        Element[] vertexElementsOrdered = Order(XMLUtilities.getChildren(verticesElement, "vertex"));
        Point3D[] vertices = deriveVertices(vertexElementsOrdered);

        Element polygonsElement = XMLUtilities.getFirstChild(objectElement, "polygons");
        // Polygon[] faces = deriveFaces(XMLUtilities.getChildren(polygonsElement, "polygon"));

        return new ObjModel();
    }

    public static Element[] Order(Element[] vertexElementsUnordered) throws InvalidObjectXMLException {
        Element[] vertexElementsOrdered = new Element[vertexElementsUnordered.length];

        for (int i = 0; i < vertexElementsUnordered.length; i++) {
            boolean indexFound = false;

            for (Element vertexElement : vertexElementsUnordered) {
                if (vertexElement.hasAttribute("index") && Integer.parseInt(vertexElement.getAttribute("index")) == i) {
                    vertexElementsOrdered[i] = vertexElement;
                    indexFound = true;
                    break;
                }
            }

            if (indexFound == false) {
                throw new InvalidObjectXMLException("missing vertex index");
            }
        }

        return vertexElementsOrdered;
    }

    public static Point3D[] deriveVertices(Element[] vertexElementsOrdered) {
        Point3D[] vertices = new Point3D[vertexElementsOrdered.length];

        for (int i = 0; i < vertices.length; i++) {
            double x = Double.parseDouble(vertexElementsOrdered[i].getAttribute("x"));
            double y = Double.parseDouble(vertexElementsOrdered[i].getAttribute("y"));
            double z = Double.parseDouble(vertexElementsOrdered[i].getAttribute("z"));

            vertices[i] = new Point3D(x, y, z);
        }

        return vertices;
    }

//    public static Polygon[] deriveFaces(Element[] polygonElements) {
//        
//        return new Polygon(normal, vertices, uv);
//    }
}

class Polygon {

    public Point3D normal;
    private int[] vertexIndicies;
    public Point2D[] uv;
    private final ObjModel objModel;

    public Polygon(ObjModel objModel, int[] vertexIndicies, Point3D normal, Point2D[] uv) {
        this.objModel = objModel;
        this.vertexIndicies = vertexIndicies;
        this.uv = uv;
        this.normal = normal;
    }

    public int getLength() {
        return this.vertexIndicies.length;
    }

    public Point3D[] getVertices() {
        Point3D[] vertices = new Point3D[getLength()];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = this.objModel.vertices[vertexIndicies[i]];
        }

        return vertices;
    }

    public boolean isTriangle() {
        return getLength() == 3;
    }
}

class InvalidObjectXMLException extends Exception {
    //Parameterless Constructor

    public InvalidObjectXMLException() {
    }

    //Constructor that accepts a message
    public InvalidObjectXMLException(String message) {
        super(message);
    }
}
