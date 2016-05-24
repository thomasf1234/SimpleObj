/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleobj;

import javafx.collections.ObservableFloatArray;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
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
    
    public SimpleObj toSimpleObj() {
        //SimpleObj mesh = new SimpleObj();
        TriangleMesh mesh = new TriangleMesh();
        ObservableFloatArray points = mesh.getPoints();

        for (Point3D vertex : this.vertices) {
           points.addAll((float) vertex.getX(), (float) vertex.getY(), (float) vertex.getZ());
        }
        
        ObservableFloatArray uvCoords = mesh.getTexCoords();
        ObservableFaceArray faces = mesh.getFaces();
        for(Polygon polygon : this.polygons) {          
            int[] orderedVertexIndicies = polygon.getOrderedVertexIndicies();
            Point2D[] orderedUV = polygon.getOrderedUV();
            
            for(int i=0; i < polygon.getLength(); i++) {         
              uvCoords.addAll((float) orderedUV[i].getX(), (float) orderedUV[i].getY());         
              faces.addAll(orderedVertexIndicies[i], i);
            }
        }
        
        SimpleObj simpleObj = new SimpleObj(mesh);
        simpleObj.setCullFace(CullFace.BACK);
        simpleObj.setDrawMode(DrawMode.FILL);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GRAY);
        material.setSpecularColor(Color.WHITE);
        material.setSpecularPower(10.0);
        simpleObj.setMaterial(material);
        
        return simpleObj;
    }

}
