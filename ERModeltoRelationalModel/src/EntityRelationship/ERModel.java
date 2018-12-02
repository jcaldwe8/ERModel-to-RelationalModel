/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityRelationship;

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
    ArrayList<Relationship> relationships, bin1to1, bin1toN, binMtoN, binRange;
    
    public ERModel(String name) {
        this.name = name;
        regEntities = new ArrayList<>();
        weakEntities = new ArrayList<>();
        relationships = new ArrayList<>();
        bin1to1 = new ArrayList<>();
        bin1toN = new ArrayList<>();
        binMtoN = new ArrayList<>();
        binRange = new ArrayList<>();
    }//constructor
    
    public void addRegEntity(String name) {
        Entity e = new Entity(name, EntityType.REG);
        regEntities.add(e);
    }//addRegEntity
    
    public void addWeakEntity(String name, String idRel) {
        Entity e = new Entity(name, EntityType.WEAK, idRel);
        weakEntities.add(e);
    }//addWeakEntity
    
    public void addRelationship(String name, Entity LE, Entity RE, String LP, String RP, String LC, String RC) {
        if (!checkEnt(LE) || !checkEnt(RE))
            return;
        Relationship r = new Relationship(name, LE, RE, LP, RP, LC, RC);
        relationships.add(r);
    }//addRelationship
    
    public boolean checkEnt(Entity eName) {
        for (Entity e : regEntities)
            if (e.equals(eName))
                return true;
        for (Entity e : weakEntities)
            if (e.equals(eName))
                return true;
        System.err.println("No Entity with name " + eName + " has been added yet!!\nAdd entity before including it in a relationship!!");
        return false;
    }//checkEnt
    
    //TODO: improve to allow composite functionality
    //addAttrToRelationship: add an attribute to a relationship
    public void addAttrToRelationship(String attr, String type, String relName) {
        Relationship r = getRelationship(relName);
        if (!r.getName().equals("NULL")) {
            r.addAttribute(attr, type);
        } else {
            System.out.println("Couldn't find Relationship with name " + relName + "\nAttribute not added");
        }
    }//addAttrToRelationship
    
    //addAttrToEntity: add an attribute to the Entity called eName
    // eName: name of entity we add the attribute to
    // attach: where the attribute is attached (the name of the entity or a composite attribute)
    // aName: name of added attribute
    // type: type of added attribute
    public void addAttrToEntity(String eName, String attach, String aName, String type) {
        if (type.equalsIgnoreCase("COMPOSITE") || type.equalsIgnoreCase("C")) {
            getEntity(eName).addAttr(attach, aName, AttrType.COMPOSITE);
        } else if (type.equalsIgnoreCase("SIMPLE") || type.equalsIgnoreCase("S")) {
            getEntity(eName).addAttr(attach, aName, AttrType.SIMPLE);
        } else if (type.equalsIgnoreCase("MULTIVALUED") || type.equalsIgnoreCase("M")) {
            getEntity(eName).addAttr(attach, aName, AttrType.MULTIVALUED);
        } else if (type.equalsIgnoreCase("DERIVED") || type.equalsIgnoreCase("D")) {
            getEntity(eName).addAttr(attach, aName, AttrType.DERIVED);
        } else {
            System.err.println("Attribute Type " + type + " is not acceptable.");
            System.err.println("Choose COMPOSITE, SIMPLE, MULTIVALUED, or DERIVED.");
        }//if-else
    }//addAttrToEntity
    
    //setKeyAttrOfEnt: set the key attributes of an entity type
    public void setKeyAttrOfEnt(String eName, ArrayList<String> keys) {
        getEntity(eName).setKeyAttr(keys);
    }//setKeyAttrOfEnt
    
    //organizeRel: organize the relationships by cardinality
    // mainly used for converting into a relational model
    // we don't use identifying relationships, since they are converted in a previous step
    public void organizeRel() {
        bin1to1.clear();
        bin1toN.clear();
        binMtoN.clear();
        binRange.clear();
        for (Relationship r : relationships) {
            if (r.getType().equals("1:1") && !r.isIDRel()) {
                bin1to1.add(r);
            } else if (r.getType().equals("1:N") && !r.isIDRel()) {
                bin1toN.add(r);
            } else if (r.getType().equals("M:N") && !r.isIDRel()) {
                binMtoN.add(r);
            } else if (r.getType().matches("Range$") && !r.isIDRel()) {
                binRange.add(r);
            }//if-else
        }//for-each
    }//organizeRel
    
    public ArrayList<MultivaluedAttrTuple> getMVAT() {
        ArrayList<MultivaluedAttrTuple> mvats = new ArrayList<>();
        for (Entity e : regEntities) {
            mvats.add(e.getMultivaluedAttrTuple());
        }//for-each
        for (Entity e : weakEntities) {
            mvats.add(e.getMultivaluedAttrTuple());
        }//for-each
        return mvats;
    }//getMVAT
    
    ///////////////////////
    // GETTERS + SETTERS //
    ///////////////////////
    
    public Relationship getRelationship(String name) {
        try {
            for (Relationship r : relationships)
                if (r.getName().equals(name))
                    return r;
            System.err.println("Couldn't find Relationship with name " + name + "\nReturning NULL element");
            throw new ElementNotFound("The Relationship with name " + name + " was not found!!");
        } catch (ElementNotFound e) {
            e.printStackTrace();
        }
        return new Relationship("NULL");
    }

    public Entity getEntity(String name) {
        try {
            for (Entity e : regEntities)
                if (e.getName().equals(name))
                    return e;
           for (Entity e : weakEntities)
                if (e.getName().equals(name))
                    return e;
            System.err.println("Couldn't find Entity with name " + name + "\nReturning NULL element");
            throw new ElementNotFound("The Entity with name " + name + " was not found!!");
        } catch (ElementNotFound e) {
            e.printStackTrace();
        }
        return new Entity("NULL", EntityType.NULL);
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

    public ArrayList<Relationship> getBin1to1() {
        return bin1to1;
    }

    public ArrayList<Relationship> getBin1toN() {
        return bin1toN;
    }

    public ArrayList<Relationship> getBinMtoN() {
        return binMtoN;
    }

    public ArrayList<Relationship> getBinRange() {
        return binRange;
    }
    
    @Override
    public String toString() {
        String ret = this.getName() + ":\n\n";
        if (!regEntities.isEmpty()) {
            ret += "***Regular Entities***\n\n";
            for (Entity e : regEntities)
                ret += e.toString() + "\n";
        }//if
        if (!weakEntities.isEmpty()) {
            ret += "***Weak Entities***\n\n";
            for (Entity e : weakEntities)
                ret += e.toString() + "\n";
        }//if
        if (!relationships.isEmpty()) {
            ret += "***Relationships***\n\n";
            for (Relationship r : relationships)
                ret += r.toString() + "\n";
        }//if
        return ret;
    }//toString
    
    public void display() {
        System.err.println("\n" + this.toString());
    }//display
    
    public String toFile() {
        String content = this.name + "\n";

        for (Entity e : regEntities)
            content += e.toFile();

        for (Entity e : weakEntities)
            content += e.toFile();

        for (Relationship r : relationships)
            content += r.toFile();

        return content;
    }//toFile
    
    public static void main(String args[]) {
        ERModel company = new ERModel("Company");
        String employee = "Employee";
        String department = "Department";
        ArrayList<String> deptKey = new ArrayList<>();
        deptKey.add("Dept No.");
        company.addRegEntity(employee);
        company.addRegEntity(department);
        company.addRelationship("Works For", company.getEntity(employee), company.getEntity(department), "Full", "Partial", "1,3", "N");
        company.addAttrToEntity(department, department, "Dept No.", "Simple");
        company.setKeyAttrOfEnt(department, deptKey);
        company.addAttrToEntity(department, department, "Location", "Multivalued");
        company.display();
        company.getEntity("Project");
        company.getRelationship("Works On");
    }
}
