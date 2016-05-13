/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleobj;

import javafx.geometry.Point3D;

/**
 *
 * @author ad
 */
public class ObjModel {

    public String name;
    public Point3D[] vertices;
    public Polygon[] polygons;

    public ObjModel(String name) {
        this.name = name;
    }

}
