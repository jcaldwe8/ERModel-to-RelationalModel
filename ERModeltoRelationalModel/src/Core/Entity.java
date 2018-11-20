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
    private ArrayList<EAttribute> attr;
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
    
    //addAttr: add an attribute either to the Entity or to a Composite EAttribute
    // attach: what the new attribute will be `attached' to
    // aname: name of the added attribute
    // type: type of the added attribute
    public void addAttr(String attach, String aName, AttrType type) {
        if (attach.equals(name)) {
            addAttrToEnt(aName, type);
        } else {
            addAttrToComp(attach, aName, type);
        }//if-else
    }//addAttr
    
    //addAttrToEnt: add an attribute to this Entity
    private void addAttrToEnt(String name, AttrType type) {
        EAttribute a = new EAttribute(name, type, 0);
        attr.add(a);
    }//addAttrToEnt
    
    //addAttrToComp: add a sub-attribute to a composite attribute
    private void addAttrToComp(String parAttr, String aName, AttrType ctype) {
        for (EAttribute a : attr) {
            if (a.getName().equals(parAttr) && a.getType() == AttrType.COMPOSITE) {
                a.addCompAttr(aName, ctype);
                return;
            } else if (a.getType() == AttrType.COMPOSITE) {
                EAttribute b;
                b = a.getSubAttribute(parAttr);
                if (b.getName().equals(parAttr)) { 
                    b.addCompAttr(aName, ctype);
                    return;
                }
            }//if
        }//for
    }//addAttrToComp

    //setKeyAttr: set the key attributes of this entity type
    public void setKeyAttr(String... keys) {
        keyAttr.clear();
        boolean in;
        for (String a : keys) {
            in = false;
            for (EAttribute at : attr) {
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
    
    public ArrayList<EAttribute> getAttr() {
        return attr;
    }
    
    public EAttribute getAttr(String a) {
        try {
            for (EAttribute e : this.getAttr())
                if (e.getName().equals(a))
                    return e;
            System.err.println("Couldn't find Attribute with name " + a + "\nReturning NULL element");
            throw new ElementNotFound("The Attribute with name " + a + " was not found!!");
        } catch (ElementNotFound e) {
            e.printStackTrace();
        }
        return new EAttribute("NULL", AttrType.NULL, -1);
    }//getAttr
    
    public Set<String> getKey() {
        return keyAttr;
    }
    
    public String getIDRel() {
        return idRel;
    }//getIDRel
    
    @Override
    public String toString() {
        String ret;
        ret = name + "(" + type + ")";
        if (type == EntityType.WEAK) {
            ret = ret + "\nIdentifying Relationship: " + idRel;
        }//if
        ret += "\n- - - - - -";
        String key = "";
        for (EAttribute a : attr) {
            if (keyAttr.contains(a.getName())) { 
                key = "*"; 
            }//if
            ret += "\n" + key + a.toString();
            key = "";
        }//for
        if (attr.isEmpty())
            ret += "\n(no attributes)";
        ret += "\n";
        return ret;
    }//toString
    
    public void display() {
        System.err.println("\n" + this.toString());
    }
    
    public static void main(String args[]) {
        Entity car = new Entity("Car", EntityType.REG);
        car.addAttr("Car", "Licence Plate#", AttrType.SIMPLE);
        car.addAttr("Car", "Registration", AttrType.COMPOSITE);
        car.addAttr("Registration", "State", AttrType.SIMPLE);
        car.addAttr("Registration", "Number", AttrType.SIMPLE);
        car.setKeyAttr("Licence Plate#");
        car.display();
        
        Entity bankBranch = new Entity("Bank Branch", EntityType.WEAK, "Branch");
        bankBranch.addAttr("Bank Branch", "Branch No.", AttrType.SIMPLE);
        bankBranch.addAttr("Bank Branch", "Location", AttrType.SIMPLE);
        bankBranch.display();
        bankBranch.setKeyAttr("Branch No.", "Location");
        bankBranch.display();
    }
    
}
