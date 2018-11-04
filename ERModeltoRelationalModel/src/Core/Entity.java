/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author caldw
 * Entity: an Entity has a name and contains attributes
 *         it can be regular or weak (with an identifying relationship)
 */
public class Entity {
    
    private String name;
    private EntityType type;
    private String idRel; //name of identifying relationship
    private ArrayList<Attribute> attr;
    private Set<String> keyAttr;
    
    public Entity(String name, EntityType type) {
        this.name = name;
        this.type = type;
        attr = new ArrayList<>();
        keyAttr = new HashSet<>();
        idRel = "";
    }//constructor(for strong)
    
    public Entity(String name, EntityType type, String idRel) {
        this.name = name;
        this.type = type;
        attr = new ArrayList<>();
        keyAttr = new HashSet<>();
        if (type != EntityType.WEAK)
            return;
        this.idRel = idRel;
    }//constructor(for weak)
    
    //addAttr: add an attribute to this Entity
    public void addAttr(String name, AttrType type) {
        Attribute a = new Attribute(name, type, 0);
        attr.add(a);
    }//addAttr
    
    //addAttrToComp: add a sub-attribute to a composite attribute
    public void addAttrToComp(String parAttr, String cname, AttrType ctype) {
        for (Attribute a : attr) {
            if (a.getName().equals(parAttr) && a.getType() == AttrType.COMPOSITE) {
                a.addCompAttr(cname, ctype);
                return;
            } else if (a.getType() == AttrType.COMPOSITE) {
                Attribute b;
                b = a.getSubAttribute(parAttr);
                if (b.getName().equals(parAttr)) { 
                    b.addCompAttr(cname, ctype);
                    return;
                }
            }//if
        }//for
    }//addAttrToComp

    public void setKeyAttr(String... attrs) {
        keyAttr.clear();
        boolean in;
        for (String a : attrs) {
            in = false;
            for (Attribute at : attr) {
                if (at.getName().equals(a)) {
                    in = true;
                    break;
                }//if
            }
            if (in) {
                keyAttr.add(a);
            } else {
                System.err.println("Attribute " + a + " is not currently in the array of attributes!!!\nAdd it before declaring it part of the key for the entity!!!");
            }//if-else
        }//for-each
    }//setKeyAttr
    
    public String getName() {
        return name;
    }
    
    public EntityType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        String ret;
        ret = name + "(" + type + ")";
        if (type == EntityType.WEAK) {
            ret = ret + "\nIdentifying Relationship: " + idRel;
        }//if
        ret += "\n- - - - - -\n";
        String key = "";
        for (Attribute a : attr) {
            if (keyAttr.contains(a.getName())) { 
                key = "*"; 
            }//if
            ret += "\n" + key + a.toString();
            key = "";
        }//for
        if (attr.isEmpty())
            ret += "(no attributes)\n\n";
        return ret;
    }//toString
    
    public void display() {
        System.err.println("\n" + this.toString());
    }
    
    public static void main(String args[]) {
        Entity car = new Entity("Car", EntityType.REG);
        car.addAttr("Licence Plate#", AttrType.SIMPLE);
        car.addAttr("Registration", AttrType.COMPOSITE);
        car.addAttrToComp("Registration", "State", AttrType.SIMPLE);
        car.addAttrToComp("Registration", "Number", AttrType.SIMPLE);
        car.setKeyAttr("Licence Plate#");
        car.display();
        
        Entity bankBranch = new Entity("Bank Branch", EntityType.WEAK, "Branch");
        bankBranch.addAttr("Branch No.", AttrType.SIMPLE);
        bankBranch.addAttr("Location", AttrType.SIMPLE);
        bankBranch.display();
        bankBranch.setKeyAttr("Branch No.", "Location");
        bankBranch.display();
    }
    
}
