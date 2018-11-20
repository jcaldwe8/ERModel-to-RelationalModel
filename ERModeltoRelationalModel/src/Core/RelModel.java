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
 */
public class RelModel {
    
    String name;
    ArrayList<Relation> relations;
    
    
    public RelModel(String name) {
        this.name = name;
        relations = new ArrayList<>();
    }//constructor
    
    public boolean containsRelation(String name) {
        for (Relation r : relations) {
            if (r.getName().equals(name))
                return true;
        }//for-each
        return false;
    }//containsRelation
    
    public Relation getRelation(String name) {
        for (Relation r : relations)
            if (r.getName().equals(name))
                return r;
        return new Relation("NULL");
    }//getRelation
    
    public void addRelation(String name) {
        if (!this.containsRelation(name)) {
            relations.add(new Relation(name));
        } else {
            System.err.println("Relation with name " + name + " already exists!!");
        }//if
    }//addRelation
      
    public void addAttr(String rel, String attr) {
        Relation r = getRelation(rel);
        if (r.getName().equals("NULL")) 
            return;
        r.addAttr(attr);
    }//addAttr
    
    public void addAttr(String rel, String attr, String fkRel, String fkAttr) {
        Relation r = getRelation(rel);
        if (r.getName().equals("NULL")) 
            return;
        r.addAttr(attr, fkRel, fkAttr);
    }//addAttr
    
    @Override
    public String toString() {
        String ret = name + ":\n= = = = = =\n";
        for (Relation r : relations) {
            ret += r.toString();
        }//for-each        
        return ret;
    }
    
    public void display() {
        System.err.println(this.toString());
    }//display
    
}
