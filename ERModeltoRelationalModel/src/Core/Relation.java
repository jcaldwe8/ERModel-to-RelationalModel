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
 */
public class Relation {
    
    String name;
    ArrayList<RAttribute> attr;
    Set<String> key;
    ArrayList<FunctDep> F;
    
    public Relation(String name) {
        this.name = name;
        attr = new ArrayList<>();
        key = new HashSet<>();
        F = new ArrayList<>();
    }//Constructor
    
    public void addAttr(String a) {
        attr.add(new RAttribute(a));
    }//addAttr
    
    public void addAttr(String a, String relRef, String attrRef) {
        attr.add(new RAttribute(a, relRef, attrRef));
    }//addAttr
    
    //setKeyAttr: set the key attributes of this entity type
    public void setKeyAttr(ArrayList<String> keys) {
        //key.clear();
        boolean in;
        for (String a : keys) {
            in = false;
            for (RAttribute at : attr) {
                if (at.getName().equals(a)) {
                    in = true;
                    break;
                }//if
            }
            if (in) {
                key.add(a);
            } else {
                System.err.println("Attribute " + a + " is not currently in the array of attributes!!!\nAdd it before declaring it part of the key for the entity!!!");
            }//if-else
        }//for-each
    }//setKeyAttr

    public String getName() {
        return name;
    }

    public ArrayList<RAttribute> getAttr() {
        return attr;
    }

    public Set<String> getKey() {
        return key;
    }
    
    @Override
    public String toString() {
        String ret = name + "\n----------\n";
        ret += "Functional Dependencies:\n- - - - - -\n";
        for (FunctDep fd : F) {
            fd.toString();
        }//for-each
        ret += "Attributes:\n- - - - - -\n";
        for (RAttribute a : attr) {
            if (key.contains(a.getName()))
                ret += "(PK) ";
            ret += a.toString() + "\n";            
        }//for-each        
        return ret + "\n";
    }
    
    public void display() {
        System.err.println(this.toString());
    }//display
    
}
