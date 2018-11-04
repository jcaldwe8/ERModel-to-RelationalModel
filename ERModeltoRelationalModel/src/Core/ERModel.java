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
    
    String name;
    ArrayList<Entity> regEntities;
    ArrayList<Entity> weakEntities;
    ArrayList<Relationship> relationships;
    
    public ERModel(String name) {
        this.name = name;
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
        if (checkEnt(LE) || checkEnt(RE))
            return;
        Relationship r = new Relationship(name, LE, RE, LP, RP, LC, RC);
        relationships.add(r);
    }//addRelationship
    
    public boolean checkEnt(Entity eName) {
        for (Entity e : regEntities)
            if (e.getName().equals(eName))
                return true;
        for (Entity e : weakEntities)
            if (e.getName().equals(eName))
                return true;
        System.err.println("No Entity with name " + eName + " has been added yet!!\nAdd entity before including it in a relationship!!");
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
        try {
            for (Relationship r : relationships)
                if (r.getName().equals(name))
                    return r;
            System.err.println("Couldn't find Relationship with name " + name + "\nReturning NULL");
            throw new ElementNotFound("The Relationship with name " + name + " was not found!!");
        } catch (ElementNotFound e) {
            e.printStackTrace();
        }
        return null;
    }

    public Entity getEntity(String name) {
        try {
            for (Entity e : regEntities)
                if (e.getName().equals(name))
                    return e;
           for (Entity e : weakEntities)
                if (e.getName().equals(name))
                    return e;
            System.err.println("Couldn't find Entity with name " + name);
            throw new ElementNotFound("The Entity with name " + name + " was not found!!");
        } catch (ElementNotFound e) {
            e.printStackTrace();
        }
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
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        String ret = this.getName() + ":\n";
        if (!regEntities.isEmpty()) {
            ret += "Regular Entities:\n* * * * * * * *\n";
            for (Entity e : regEntities)
                ret += e.toString();
        }//if
        if (!weakEntities.isEmpty()) {
            ret += "Weak Entities:\n* * * * * * * *\n";
            for (Entity e : weakEntities)
                ret += e.toString();
        }//if
        if (!relationships.isEmpty()) {
            ret += "Relationships:\n* * * * * * * *\n";
            for (Relationship r : relationships)
                ret += r.toString();
        }//if
        return ret;
    }
    
    public void display() {
        System.err.println("\n" + this.toString());
    }
    
    public static void main(String args[]) {
        ERModel company = new ERModel("Company");
        Participation pWorksForEmp = Participation.FULL;
        Participation pWorksForDep = Participation.PARTIAL;
        Cardinality cWorksForEmp = new Cardinality(1,3);
        Cardinality cWorksForDep = new Cardinality(Cardinality.CardVal.N);
        company.addRegEntity("Employee");
        company.addRegEntity("Department");
        company.addRelationship("Works For", company.getEntity("Employee"), company.getEntity("Department"), pWorksForEmp, pWorksForDep, cWorksForEmp, cWorksForDep);
        company.display();
    }
}
