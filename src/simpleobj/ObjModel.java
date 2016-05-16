/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleobj;

import javafx.collections.ObservableFloatArray;
import javafx.geometry.Point3D;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.TriangleMesh;

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
    
//    public TriangleMesh toTriangleMesh() {
//        TriangleMesh mesh = new TriangleMesh();
//////        mesh.getTexCoords().addAll(0, 0);
//////        float h = 150;                    // Height
//////        float s = 300;      // Side
//        ObservableFloatArray points = mesh.getPoints();
//
//        for (Point3D vertex : this.vertices) {
//            points.addAll((float) vertex.getX(), (float) vertex.getY(), (float) vertex.getZ());
//        }
//        
//        ObservableFloatArray uvCoords = mesh.getTexCoords();
//        ObservableFaceArray faces = mesh.getFaces();
//        for(Polygon polygon : this.polygons) {
//            Point2D[] uv = polygon.getUV();
//            uvCoords.addAll(uv.getX(), uv.getY());
//            
//            Point3D 
//        }
//    }

}
