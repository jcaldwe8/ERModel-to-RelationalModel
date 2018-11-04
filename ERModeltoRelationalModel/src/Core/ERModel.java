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
 * ERModel: an ERModel contains Entities with attributes and Relationships that relate those entities
 */
public class ERModel {
    
    ArrayList<Entity> regEntities;
    ArrayList<Entity> weakEntities;
    ArrayList<Relationship> relationships;
    
    public ERModel() {
        regEntities = new ArrayList<>();
        weakEntities = new ArrayList<>();
        relationships = new ArrayList<>();
    }//constructor
    
    public void addRegEntity(String name) {
        Entity e = new Entity(name, EntityType.REG);
        regEntities.add(e);
    }//addRegEntity
    
    public void addWeakEntity(String name, String idRel) {
        Entity e = new Entity(name, EntityType.WEAK, idRel);
        weakEntities.add(e);
    }//addWeakEntity
    
    public void addRelationship(String name, String LE, String RE, Participation LP, Participation RP, Cardinality LC, Cardinality RC) {
        if (checkEnt(LE) || checkEnt(RE))
            return;
        Relationship r = new Relationship(name, LE, RE, LP, RP, LC, RC);
        relationships.add(r);
    }//addRelationship
    
    public boolean checkEnt(String eName) {
        for (Entity e : regEntities)
            if (e.getName().equals(eName))
                return true;
        for (Entity e : weakEntities)
            if (e.getName().equals(eName))
                return true;
        System.err.println("No Entity with name " + eName + " has been added yet!!\nAdd entity before including in a relationship!!");
        return false;
    }//checkEnt
    
    //addAttrToRelationship: add an attribute to a relationship
    public void addAttrToRelationship(Attribute a, String relName) {
        Relationship r = getRelationship(relName);
        if (r != null) {
            r.addAttribute(a);
        } else {
            System.out.println("Couldn't find Relationship with name " + relName + "\nAttribute not added");
        }
    }//addAttrToRelationship
    
    public Relationship getRelationship(String name) {
        for (Relationship r : relationships)
            if (r.getName().equals(name))
                return r;
        System.out.println("Couldn't find Relationship with name " + name);
        return null;
    }

    public ArrayList<Entity> getRegEntities() {
        return regEntities;
    }

    public ArrayList<Entity> getWeakEntities() {
        return weakEntities;
    }

    public ArrayList<Relationship> getRelationships() {
        return relationships;
    }
    
    public static void main(String args[]) {
        Relationship worksFor = new Relationship("Works_For");
        Participation pWorksForEmp = Participation.FULL;
        Participation pWorksForDep = Participation.PARTIAL;
        Cardinality cWorksForEmp = new Cardinality(1,3);
        Cardinality cWorksForDep = new Cardinality(Cardinality.CardVal.N);
        worksFor.setLeftEnt("Employee");
        worksFor.setRightEnt("Department");
        worksFor.setLeftPar(pWorksForEmp);
        worksFor.setRightPar(pWorksForDep);
        worksFor.setLeftCar(cWorksForEmp);
        worksFor.setRightCar(cWorksForDep);
    }
    
}
