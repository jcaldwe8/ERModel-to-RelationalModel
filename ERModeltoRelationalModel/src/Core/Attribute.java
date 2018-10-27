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
    
    private String name;
    private AttrType type;
    private int level;
    private String indent;
    private ArrayList<Attribute> comp; //simple attribute components that make up a composite attribute
    
    public Attribute(String name, AttrType type, int level) {
        this.name = name; //name of the attribute
        this.type = type; //type of the attribute
        comp = new ArrayList<>(); //initialize a composite's sub-attributes if needed
        this.level = level;
        for (int i = 0; i < level+1; i++)
            indent += " ";
    }//constructor
    
    //alterType: change the type of the attribute if needed
    public void alterType(AttrType newType) { type = newType; }
    
    //alterName: change the name if desired
    public void alterName(String newName) { name = newName; }
    
    //addCompAttr: add an attribute to a composite's parts
    public void addCompAttr(String cname, AttrType ctype) {
        if (type != AttrType.COMPOSITE) {
            System.err.println("Cannot add branching attributes - Not a COMPOSITE attribute");
            return;
        }
        Attribute a = new Attribute(cname, ctype, level + 1);
        comp.add(a);
    }//addCompAttr
    
    //print information to a string
    @Override
    public String toString() {
        String ret = name + " (" + type.toString() + ")";
        for (Attribute a : comp) {
            ret = ret + "\n" + indent + " ->" + a.toString();
        }
        return ret;
    }//toString

    public String getName() {
        return name;
    }

    public AttrType getType() {
        return type;
    }

    public ArrayList<Attribute> getComp() {
        return comp;
    }
    
}
