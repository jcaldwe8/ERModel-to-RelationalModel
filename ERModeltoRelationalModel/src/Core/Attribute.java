/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;

/**
 *
 * @author caldw
 * Attribute: an Attribute has a name and can be simple, composite, or multivalued
 */
public class Attribute {
    
    String name;
    AttrType type;
    ArrayList<Attribute> comp; //simple attribute components that make up a composite attribute
    
    public void Attribute(String name, AttrType type) {
        this.name = name; //name of the attribute
        this.type = type; //type of the attribute
    }//constructor
    
}
