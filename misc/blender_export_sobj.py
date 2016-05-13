import bpy
import xml.etree.cElementTree as ET
from xml.dom import minidom
    
def get_sobj_data(mesh, uv_layer):
    from collections import OrderedDict
    sobj_data = {'name': current_obj.name, 'vertex_count': len(mesh.vertices), 'polygon_count' : len(mesh.polygons), 'vertices' : [], 'polygons' : []}
    for vertex in mesh.vertices:
        sobj_data['vertices'].append([vertex.co.x, vertex.co.y, vertex.co.z])
    for polygon in mesh.polygons:
        if polygon.loop_total != 3: 
            raise Exception('Polygon index %d is not a triangle' % polygon.index)
        polygon_data = { "length" : polygon.loop_total, "normal" : [polygon.normal.x, polygon.normal.y, polygon.normal.z], "vertices" : [] }
        for loop_index in range(polygon.loop_start, polygon.loop_start + polygon.loop_total):
            vertex_index = mesh.loops[loop_index].vertex_index
            polygon_data["vertices"].append({"index" : vertex_index, "uv" : [uv_layer[loop_index].uv.x, uv_layer[loop_index].uv.y]})
        sobj_data['polygons'].append(polygon_data)
    return sobj_data

def export_xml(sobj, path):
    root = ET.Element("root")
    object_element = ET.SubElement(root, "object")
    
    object_element.set("name", sobj['name'])
    vertices_element = ET.SubElement(object_element, "vertices", count=str(sobj['vertex_count']))
    polygons_element = ET.SubElement(object_element, "polygons", count=str(sobj['polygon_count']))
    
    for index, vertex in enumerate(sobj['vertices']):
        vertex_element = ET.SubElement(vertices_element, "vertex")
        vertex_element.set("index", str(index))
        vertex_element.set("x", str(vertex[0]))
        vertex_element.set("y", str(vertex[1]))
        vertex_element.set("z", str(vertex[2]))
  
    for polygon in sobj['polygons']:
        polygon_element = ET.SubElement(polygons_element, "polygon")
        normal_element = ET.SubElement(polygon_element, "normal")
        normal_element.set("x", str(polygon['normal'][0]))
        normal_element.set("y", str(polygon['normal'][1]))
        normal_element.set("z", str(polygon['normal'][2]))
        vertices_element = ET.SubElement(polygon_element, "vertices", count=str(polygon['length']))
        
        for vertex in polygon['vertices']:
            vertex_element = ET.SubElement(vertices_element, "vertex", index=str(vertex['index']))
            uv_element = ET.SubElement(vertex_element, "uv")
            uv_element.set("x", str(vertex['uv'][0]))
            uv_element.set("y", str(vertex['uv'][1]))
            
    xmlstr = minidom.parseString(ET.tostring(root)).toprettyxml(indent="   ")
    with open(path + sobj["name"] + ".xml", "w") as f:
        f.write(xmlstr)


current_obj = bpy.context.active_object  
mesh = current_obj.data
uv_layer = mesh.uv_layers.active.data

sobj_data = get_sobj_data(mesh, uv_layer)
export_xml(sobj_data, "C:\\Users\\ad\\Documents\\NetBeansProjects\\CubeDemo\\CubeDemo\\src\\cubedemo\\") 
