/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleobj;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

/**
 *
 * @author ad
 */

public class Polygon {

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
            vertices[i] = this.objModel.vertices[this.vertexIndicies[i]];
        }

        return vertices;
    }
    
    public Point3D[] getOrderedVertices() {
        Point3D[] vertices = new Point3D[getLength()];
        int[] orderedVertexIndicies = getOrderedVertexIndicies();
        
        for (int i = 0; i < vertices.length; i++) {          
            vertices[i] = this.objModel.vertices[orderedVertexIndicies[i]];
        }

        return vertices;
    }
    
    public int[] getOrderedVertexIndicies() {
        int[] orderedVertexIndicies = new int[3]; 
        
        Point3D v01 = this.objModel.vertices[this.vertexIndicies[1]].subtract(this.objModel.vertices[this.vertexIndicies[0]]);     
        Point3D v02 = this.objModel.vertices[this.vertexIndicies[2]].subtract(this.objModel.vertices[this.vertexIndicies[0]]);
        
        Point3D v01CrossV02 = v01.crossProduct(v02);

        if(v01CrossV02.dotProduct(this.normal) > 0) {
           orderedVertexIndicies[0] = this.vertexIndicies[0];   
           orderedVertexIndicies[1] = this.vertexIndicies[1];   
           orderedVertexIndicies[2] = this.vertexIndicies[2];   
        } else {
           orderedVertexIndicies[0] = this.vertexIndicies[0];   
           orderedVertexIndicies[1] = this.vertexIndicies[2];   
           orderedVertexIndicies[2] = this.vertexIndicies[1];   
        }
 
        return orderedVertexIndicies;
    }
    
    //need to test
    public Point2D[] getOrderedUV() {
      Point2D[] uv = new Point2D[getLength()];
      int[] orderedVertexIndicies = getOrderedVertexIndicies();
        for (int i = 0; i < orderedVertexIndicies.length; i++) {
            for (int j=0; j < this.vertexIndicies.length; j++) {
              if(orderedVertexIndicies[i] == this.vertexIndicies[j]) {
                uv[i] = this.uv[j];
                break;
              }               
            }
        }

        return uv;    
    }

    public boolean isTriangle() {
        return getLength() == 3;
    }
}
