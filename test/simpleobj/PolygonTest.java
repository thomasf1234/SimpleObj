/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleobj;

import javafx.geometry.Point3D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ad
 */
public class PolygonTest {
    
    public PolygonTest() {
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
     * Test of getLength method, of class Polygon.
     */
    @Test
    public void testGetLength() {
        ObjModel objModel = new ObjModel("TestObject");
        objModel.vertices = new Point3D[]{new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1)};
        
        int[] vertexIndicies = new int[]{0, 1, 3};
        Point3D normal = new Point3D(0, 0, -1);
        Polygon polygon = new Polygon(objModel, vertexIndicies, normal, null);
        
        assertEquals(3, polygon.getLength());
    }

    /**
     * Test of getVertices method, of class Polygon.
     */
    @Test
    public void testGetVertices() {
        ObjModel objModel = new ObjModel("TestObject");
        objModel.vertices = new Point3D[]{new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1),};
       
        Point3D normal = new Point3D(0, 0, -1);
        Polygon triangle = new Polygon(objModel, new int[]{0, 1, 3}, normal, null);
        
        Point3D[] vertices = triangle.getVertices();
        
        assertEquals(3, vertices.length);
        assertEquals(new Point3D(0, 0, 0), vertices[0]);
        assertEquals(new Point3D(1, 0, 0), vertices[1]);
        assertEquals(new Point3D(0, 0, 1), vertices[2]);
    }

    @Test
    public void testGetOrderedVertices() {
        ObjModel objModel = new ObjModel("TestObject");
        objModel.vertices = new Point3D[]{new Point3D(0, 0, 0), new Point3D(1, 10, 0), new Point3D(0, 1, 0), new Point3D(1, 0, 1),};
       
        Point3D towardsNormal = new Point3D(0, 0, -1);
        Polygon triangle = new Polygon(objModel, new int[]{0, 1, 3}, towardsNormal, null);
        
        Point3D[] vertices = triangle.getOrderedVertices();
        
        assertEquals(3, vertices.length);
        assertEquals(new Point3D(1, 10, 0), vertices[0]);
        assertEquals(new Point3D(1, 0, 1), vertices[1]);
        assertEquals(new Point3D(0, 0, 0), vertices[2]);
    }

    
    /**
     * Test of isTriangle method, of class Polygon.
     */
    @Test
    public void testIsTriangle() {
        ObjModel objModel = new ObjModel("TestObject");
        objModel.vertices = new Point3D[]{new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1),};
       
        Point3D normal = new Point3D(0, 0, -1);
        Polygon triangle = new Polygon(objModel, new int[]{0, 1, 3}, normal, null);
        assertEquals(true, triangle.isTriangle());
        
        Polygon rectangle = new Polygon(objModel, new int[]{0, 1, 2, 3}, normal, null);
        assertEquals(false, rectangle.isTriangle());
    }
    
    @Test
    public void testIsClockwise() {
        ObjModel objModel = new ObjModel("TestObject");
        objModel.vertices = new Point3D[]{new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1),};
       
        Point3D awayNormal = new Point3D(2, 3, -1);
        Polygon triangle = new Polygon(objModel, new int[]{0, 1, 3}, awayNormal, null);
        assertEquals(false, triangle.isClockwise());
        
        Point3D towardsNormal = new Point3D(2, -3, 1);
        Polygon triangle2 = new Polygon(objModel, new int[]{1, 2, 3}, towardsNormal, null);
        assertEquals(true, triangle2.isClockwise());
    }
    
    @Test
    public void testGetOrderedVertexIndicies() {
        ObjModel objModel = new ObjModel("TestObject");
        objModel.vertices = new Point3D[]{new Point3D(-1, -1, 0), new Point3D(0, 1, 0), new Point3D(1, -1, 0)};
       
        //Clockwise
        Point3D awayNormal = new Point3D(0, 0, 1);
        Polygon triangle = new Polygon(objModel, new int[]{2, 1, 0}, awayNormal, null);
        Assert.assertArrayEquals(new int[]{1, 2, 0}, triangle.getOrderedVertexIndicies());
        
        triangle = new Polygon(objModel, new int[]{1, 0, 2}, awayNormal, null);
        Assert.assertArrayEquals(new int[]{1, 2, 0}, triangle.getOrderedVertexIndicies()); 
        
        triangle = new Polygon(objModel, new int[]{1, 0, 2}, awayNormal, null);
        Assert.assertArrayEquals(new int[]{1, 2, 0}, triangle.getOrderedVertexIndicies()); 
        
        triangle = new Polygon(objModel, new int[]{0, 2, 1}, awayNormal, null);
        Assert.assertArrayEquals(new int[]{1, 2, 0}, triangle.getOrderedVertexIndicies()); 
        
        triangle = new Polygon(objModel, new int[]{1, 2, 0}, awayNormal, null);
        Assert.assertArrayEquals(new int[]{1, 2, 0}, triangle.getOrderedVertexIndicies()); 
        
        //CounterClockwise
        Point3D towardsNormal = new Point3D(0, 0, -1);
        triangle = new Polygon(objModel, new int[]{2, 1, 0}, towardsNormal, null);
        Assert.assertArrayEquals(new int[]{1, 0, 2}, triangle.getOrderedVertexIndicies());
        
        triangle = new Polygon(objModel, new int[]{1, 0, 2}, towardsNormal, null);
        Assert.assertArrayEquals(new int[]{1, 0, 2}, triangle.getOrderedVertexIndicies()); 
        
        triangle = new Polygon(objModel, new int[]{1, 0, 2}, towardsNormal, null);
        Assert.assertArrayEquals(new int[]{1, 0, 2}, triangle.getOrderedVertexIndicies()); 
        
        triangle = new Polygon(objModel, new int[]{0, 2, 1}, towardsNormal, null);
        Assert.assertArrayEquals(new int[]{1, 0, 2}, triangle.getOrderedVertexIndicies()); 
        
        triangle = new Polygon(objModel, new int[]{1, 2, 0}, towardsNormal, null);
        Assert.assertArrayEquals(new int[]{1, 0, 2}, triangle.getOrderedVertexIndicies()); 
        
    }
    
}
