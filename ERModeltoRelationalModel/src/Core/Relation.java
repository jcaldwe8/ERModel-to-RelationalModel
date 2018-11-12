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
    
    ArrayList<RAttribute> attr;
    ArrayList<FunctDep> F;
    Set<String> key;
    
    public Relation() {
        attr = new ArrayList<>();
        F = new ArrayList<>();
        key = new HashSet<>();
    }//Constructor
    
    public void addAttr(String a) {
        attr.add(new RAttribute(a));
    }//addAttr
    
    public void addAttr(String a, String relRef, String attrRef) {
        attr.add(new RAttribute(a, new ForeignKey(relRef, attrRef)));
    }//addAttr
    
    //setKeyAttr: set the key attributes of this entity type
    public void setKeyAttr(String... keys) {
        key.clear();
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
    
}
