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

    public boolean isTriangle() {
        return getLength() == 3;
    }
}
