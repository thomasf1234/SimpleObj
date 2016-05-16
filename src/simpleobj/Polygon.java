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
            vertices[i] = this.objModel.vertices[vertexIndicies[i]];
        }

        return vertices;
    }
    
    public Point3D[] getOrderedVertices() {
        Point3D[] vertices = new Point3D[getLength()];
        for (int i = 0; i < vertices.length; i++) {
            int[] orderedVertexIndicies = getOrderedVertexIndicies();
            vertices[i] = this.objModel.vertices[orderedVertexIndicies[i]];
        }

        return vertices;
    }
    
    public int[] getOrderedVertexIndicies() {
        int[] orderedVertexIndicies = new int[3];
     
        //start at heighest vertex
        orderedVertexIndicies[0] = getMaxYVertexIndex();

        int[] otherVertexIndicies = new int[2];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            if (this.vertexIndicies[i] != orderedVertexIndicies[0]) {
                otherVertexIndicies[index] = this.vertexIndicies[i];
                index++;
            }

        }
        
        Point3D currentVertex = this.objModel.vertices[orderedVertexIndicies[0]];
 
        Point3D vertex1 = this.objModel.vertices[otherVertexIndicies[0]];
        
        double x12, y12, theta1, theta2;
        
        x12 = vertex1.getX() - currentVertex.getX();
        y12 = vertex1.getY() - currentVertex.getY();
        if(x12 < 0)
            theta1 = Math.PI + Math.atan(Math.abs(x12) / Math.abs(y12));
        else
            theta1 = Math.PI - Math.atan(Math.abs(x12) / Math.abs(y12));
  
        
        Point3D vertex2 = this.objModel.vertices[otherVertexIndicies[1]];
        x12 = vertex2.getX() - currentVertex.getX();
        y12 = vertex2.getY() - currentVertex.getY();
        if(x12 < 0)
            theta2 = Math.PI + Math.atan(Math.abs(x12) / Math.abs(y12));
        else
            theta2 = Math.PI - Math.atan(Math.abs(x12) / Math.abs(y12));
        
        if(isClockwise()) {
            if(theta1 < theta2) {
              orderedVertexIndicies[1] = otherVertexIndicies[0];   
              orderedVertexIndicies[2] = otherVertexIndicies[1];   
            } else {
              orderedVertexIndicies[1] = otherVertexIndicies[1];
              orderedVertexIndicies[2] = otherVertexIndicies[0];                    
            }
        } else {
          if(theta1 > theta2) {
              orderedVertexIndicies[1] = otherVertexIndicies[0];   
              orderedVertexIndicies[2] = otherVertexIndicies[1];   
            } else {
              orderedVertexIndicies[1] = otherVertexIndicies[1];
              orderedVertexIndicies[2] = otherVertexIndicies[0];                    
            }    
        } 

        return orderedVertexIndicies;
    }
//    
//    public Point2D[] getUV() {
//        
//    }

    public boolean isTriangle() {
        return getLength() == 3;
    }
    
    public boolean isClockwise() {
        return this.normal.getZ() > 0;
    }
    
    private int getMaxYVertexIndex() {
        int maxYVertexIndex = 0;

        for (int i = 1; i < getLength(); i++) {
            if (this.objModel.vertices[i].getY() > this.objModel.vertices[maxYVertexIndex].getY()) {
                maxYVertexIndex = i;
            }
        }

        return maxYVertexIndex;
    }
}
