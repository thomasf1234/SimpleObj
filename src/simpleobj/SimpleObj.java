/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleobj;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author ad
 */
public class SimpleObj extends MeshView {
    public SimpleObj(TriangleMesh mesh) {
        super(mesh);
    }
//    public static SimpleObj clone(SimpleObj simpleObj) {
//SimpleObj clone = new SimpleObj();
//clone.getPoints().addAll(simpleObj.getPoints());
//clone.getTexCoords().addAll(simpleObj.getTexCoords());
//clone.getFaces().addAll(simpleObj.getFaces());
//MeshView clone = new MeshView(mesh);
//        clone.setCullFace(CullFace.FRONT);
//        clone.setDrawMode(DrawMode.FILL);
//        PhongMaterial material = new PhongMaterial();
//        material.setDiffuseColor(Color.RED);
//        material.setSpecularColor(Color.DARKRED);
//        material.setSpecularPower(10.0);
//        clone.setMaterial(material);
//        clone.setTranslateX(selectedObject.getTranslateX());
//        clone.setTranslateY(selectedObject.getTranslateY());
//        clone.setTranslateZ(selectedObject.getTranslateZ());
//        clone.setScaleX(20);
//        clone.setScaleY(20);
//        clone.setScaleZ(20);
//        root.getChildren().add(clone);
//    }
    
}
