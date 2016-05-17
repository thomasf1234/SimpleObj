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
        int[] orderedVertexIndicies = getOrderedVertexIndicies();
        
        for (int i = 0; i < vertices.length; i++) {          
            vertices[i] = this.objModel.vertices[orderedVertexIndicies[i]];
        }

        return vertices;
    }
    
    public int[] getOrderedVertexIndicies() {
      if(isClockwise()) {
          return getClockwiseVertexIndicies();
      } else {
          return getCounterClockwiseVertexIndicies();
      }
    }
    
    public int[] getCounterClockwiseVertexIndicies() {
      int[] counterClockwiseVertexIndicies = new int[3];  
      int[] clockwiseVertexIndicies = getClockwiseVertexIndicies(); 
      
      counterClockwiseVertexIndicies[0] = clockwiseVertexIndicies[0];
      counterClockwiseVertexIndicies[1] = clockwiseVertexIndicies[2];
      counterClockwiseVertexIndicies[2] = clockwiseVertexIndicies[1];

      return counterClockwiseVertexIndicies;
    }
    
    
    public int[] getClockwiseVertexIndicies() {
        int[] clockwiseVertexIndicies = new int[3];
     
        //start at heighest vertex
        clockwiseVertexIndicies[0] = getMaxYVertexIndex();

        int[] otherVertexIndicies = new int[2];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            if (this.vertexIndicies[i] != clockwiseVertexIndicies[0]) {
                otherVertexIndicies[index] = this.vertexIndicies[i];
                index++;
            }

        }
        
        if(this.objModel.vertices[otherVertexIndicies[0]].getX() > this.objModel.vertices[otherVertexIndicies[1]].getX()) {
          clockwiseVertexIndicies[1] = otherVertexIndicies[0];    
          clockwiseVertexIndicies[2] = otherVertexIndicies[1];    
        } else if (this.objModel.vertices[otherVertexIndicies[0]].getX() == this.objModel.vertices[otherVertexIndicies[1]].getX()) {
          if(this.objModel.vertices[otherVertexIndicies[0]].getY() > this.objModel.vertices[otherVertexIndicies[1]].getY()) { 
            clockwiseVertexIndicies[1] = otherVertexIndicies[0];    
            clockwiseVertexIndicies[2] = otherVertexIndicies[1];      
          } else {
            clockwiseVertexIndicies[1] = otherVertexIndicies[1];    
            clockwiseVertexIndicies[2] = otherVertexIndicies[0];     
          }     
        } else {
            clockwiseVertexIndicies[1] = otherVertexIndicies[1];    
            clockwiseVertexIndicies[2] = otherVertexIndicies[0]; 
        }

        return clockwiseVertexIndicies;
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
