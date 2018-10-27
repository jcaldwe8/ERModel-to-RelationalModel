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
    
    public void addRelationship(String name, Entity LE, Entity RE, Participation LP, Participation RP, Cardinality LC, Cardinality RC) {
        Relationship r = new Relationship(name, LE, RE, LP, RP, LC, RC);
        relationships.add(r);
    }//addRelationship
    
    //addAttrToRelationship: add an attribute to a relationship
    public void addAttrToRelationship(Attribute a, String relName) {
        Relationship r = getRelationship(relName);
        if (r != null) {
            r.addAttribute(a);
        } else {
            System.out.println("Couldn't find Relationship - Attribute not added");
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
    
}
