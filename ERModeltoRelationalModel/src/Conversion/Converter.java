/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversion;

import Relational.RAttribute;
import Relational.RelModel;
import Relational.Relation;
import EntityRelationship.ERModel;
import EntityRelationship.Relationship;
import EntityRelationship.Participation;
import EntityRelationship.EAttribute;
import EntityRelationship.Entity;
import EntityRelationship.MultivaluedAttrTuple;
import java.util.ArrayList;

/**
 *
 * @author caldw
 */
public class Converter {
    
    //ERtoRel: convert an entity-relationship model to a relation model
    public static RelModel ERtoRel(ERModel erm) {
        RelModel rm = new RelModel(erm.getName());
        regEntConvert(erm, rm); //convert regular entities into relations
        weakEntConvert(erm, rm); //convert weak entities into relations
        binRelConvert(erm, rm); //convert relationships into relations
        multivaluedAttrConvert(erm, rm); //convert multivalued attributes into relations
        return rm;
    }//ERtoRel
    
    private static ArrayList<EAttribute> getSimpleComponents(EAttribute attr) {
        ArrayList<EAttribute> simple = new ArrayList<>();
        for (EAttribute a : attr.getComp()) {
            if (a.isComposite()) {
                simple.addAll(getSimpleComponents(a));
            } else if (a.isSimple()) {
                simple.add(a);
            }//if-else
        }//for-each
        return simple;
    }//getSimpleComponents
    
    private static void extractAttributes(RelModel rm, Entity e, ArrayList<String> keys) {
        String name = e.getName();
        boolean key = false;
        rm.addRelation(name);
            for (EAttribute ea : e.getAttr()) { //add all simple attributes in an entity
                if (e.getKey().contains(ea.getName()))
                    key = true; //is this attribute a key for the entity
                if (ea.isSimple()) { //add the attribute if it's simple
                    rm.addAttr(name, ea.getName());
                    if (key) 
                        keys.add(ea.getName());
                } else if (ea.isComposite()) { //add the simple components from a composite attribute
                    for (EAttribute comp : getSimpleComponents(ea)) {
                        rm.addAttr(name, comp.getName());
                        if (key)
                            keys.add(comp.getName());
                    }//for-each
                }//if-else
                key = false;
            }//for-each
    }//extractAttributes
    
    private static void regEntConvert(ERModel erm, RelModel rm) {
        for (Entity e : erm.getRegEntities()) {
            ArrayList<String> keys = new ArrayList<>();
            extractAttributes(rm, e, keys);
            rm.getRelation(e.getName()).setKeyAttr(keys); //set the keys for this relation
        }//for-each
    }//regEntConvert
    
    private static ArrayList<String> includeIDRel(RelModel rm, ERModel erm, Entity e) {
        Relationship r = erm.getRelationship(e.getIDRel());
        ArrayList<String> parKeys = new ArrayList<>();
        Entity par;
        String eName = e.getName();
        String parName;
        String newName = r.getName() + "_";
        if (r.getLeftEnt().getName().equals(e.getName())) {
            par = r.getRightEnt();
        } else {
            par = r.getLeftEnt();
        }//if-else
        for (String a : par.getKey()) {
            if (par.getAttr(a).isSimple()) {
                rm.addAttr(eName, newName + a, par.getName(), a);
                parKeys.add(newName + a);
            } else if (par.getAttr(a).isComposite()) {
                parName = par.getName();
                for (EAttribute comp : getSimpleComponents(par.getAttr(a))) {
                    rm.addAttr(eName, newName + comp.getName(), parName, comp.getName());
                    parKeys.add(newName + comp.getName());
                }//for-each
            }//if-else
        }//for-each
        
        return parKeys;
    }//includeIDRel
    
    private static void weakEntConvert(ERModel erm, RelModel rm) {
        for (Entity e : erm.getWeakEntities()) {
            ArrayList<String> keys = new ArrayList<>();
            extractAttributes(rm, e, keys);
            keys.addAll(includeIDRel(rm, erm, e));
            rm.getRelation(e.getName()).setKeyAttr(keys);
        }//for-each
    }//weakEntConvert
    
    //includePKasFK: include the PK of partial in full as a FK
    // also include any attributes specific to the relationship
    private static void includePKasFK(String full, String partial, Relationship r, RelModel rm) {
        String newName = r.getName() + "_";
        for (String key : rm.getRelation(partial).getKey()) {
            rm.getRelation(full).addAttr(newName + key, partial, key);
        }//for-each
        for (EAttribute ea : r.getAttr()) {
            rm.addAttr(r.getName(), ea.getName()); //add attributes specific to the relationship
        }//for-each
    }//includePKasFK
    
    //foreignKeyApproach: include as a foreign key, the primary key of the partial part., to the full part.
    // r: the relationship that will become the third relation
    // rm: the relational model the new relation is added to
    // 
    // take the key from the relation with partial participation
    // add the key attributes as foreign keys to the relation with full participation
    private static void foreignKeyApproach(Relationship r, RelModel rm) {
        String Lname = r.getLeftEnt().getName();
        String Rname = r.getRightEnt().getName();
        //choose the relation with full participation
        if (r.getLeftPar() == Participation.FULL) {
            if (r.getRightPar() == Participation.FULL) {
                if (r.getLeftCar().isN()) {
                    includePKasFK(Lname, Rname, r, rm);
                } else {
                    includePKasFK(Rname, Lname, r, rm);
                }//if-else
            } else {
                includePKasFK(Lname, Rname, r, rm);
            }//if-else
        } else {
            includePKasFK(Rname, Lname, r, rm);
        }//if-else
    }//foreignKeyApproach
    
    //isKey: is the attribute (name) in the key of relation r?
    private static boolean isKey(Relation r, String name) {
        if (r.getKey().contains(name))
            return true;
        return false;
    }//isKey
    
    //copyAttr: copy all attributes 'from' one relation 'to' another, keeping track of the primary key
    private static void copyAttr(String from, String to, ArrayList<String> keys, RelModel rm) {
        for (RAttribute ra : rm.getRelation(from).getAttr()) {
            rm.addAttr(to, ra.getName());
            if (isKey(rm.getRelation(from), ra.getName()))
                keys.add(ra.getName());
        }//for-each
    }//copyAttr
    
    //mergedRelationApproach: merge the two relations into one
    // r: the relationship that will become the third relation
    // rm: the relational model the new relation is added to
    //
    // form a new relation, named based on both relations and the relationship
    // copy all attributes from both relations
    // set the primary key
    // add all attributes specific to the relationship
    // delete original relations from the relational model
    private static void mergedRelationApproach(Relationship r, RelModel rm) {
        ArrayList<String> keys = new ArrayList<>();
        String name = r.getLeftEnt().getName() + "_" + r.getName() + "(merged)_" + r.getRightEnt().getName();
        String Lname = r.getLeftEnt().getName();
        String Rname = r.getRightEnt().getName();
        rm.addRelation(name);
        copyAttr(Lname, name, keys, rm);
        copyAttr(Rname, name, keys, rm);
        rm.getRelation(r.getName()).setKeyAttr(keys); //set the primary key
        for (EAttribute ea : r.getAttr()) {
            rm.addAttr(r.getName(), ea.getName()); //add attributes specific to the relationship
        }//for-each
        rm.deleteRelation(Lname); //remove the relations after merging is complete
        rm.deleteRelation(Rname); //all functionality is covered in new relation
    }//mergedRelationApproach
    
    //addPKasFK: add the primary keys of ref to the new relation as foreign keys
    // newR: name of new relation
    // ref: reference relation
    // rm: relational model
    private static ArrayList<String> addPKasFK(String newR, Relation ref, RelModel rm, String addon) {
        ArrayList<String> keys = new ArrayList<>();
        for (String ra : ref.getKey()) {
            rm.addAttr(newR, newR + addon + "_" + ra, ref.getName(), ra);
            keys.add(newR + addon + "_" + ra);
        }//for-each
        return keys;
    }//addPKasFK
    
    //crossReferenceApproach: create a third relation that serves as a 'cross-reference'
    // r: the relationship that will become the third relation
    // rm: the relational model the new relation is added to
    //
    // the primary keys of both entities in r are added as foreign keys for the new relation
    // these become the primary key of the relation
    // any attributes specific to the relationship are added to the relation
    private static void crossReferenceApproach(Relationship r, RelModel rm) {
        ArrayList<String> keys;
        rm.addRelation(r.getName()); //define a new relation
        //add the primary keys of each entity to the new relation
        String rName = r.getName();
        Relation LER = rm.getRelation(r.getLeftEnt().getName()); //left entity relation
        Relation RER = rm.getRelation(r.getRightEnt().getName()); // right entity relation
        String lAddon = "("+r.getLeftCar().toString()+")";
        String rAddon = "("+r.getRightCar().toString()+")";
        if (r.getLeftEnt().equals(r.getRightEnt())) {
            keys = addPKasFK(rName, LER, rm, lAddon);
            keys.addAll(addPKasFK(rName, RER, rm, rAddon));
        } else {
            keys = addPKasFK(rName, LER, rm, "");
            keys.addAll(addPKasFK(rName, RER, rm, ""));
        }//if-else
        rm.getRelation(r.getName()).setKeyAttr(keys); //set the primary key
        for (EAttribute ea : r.getAttr()) {
            rm.addAttr(rName, ea.getName()); //add attributes specific to the relationship
        }//for-each
    }//crossReferenceApproach
    
    //bin1to1Convert: convert 1-1 relationships into relations
    // relationships with a full and partial participation apply the foreign key approach
    // relationships with both full participation apply the merged relation approach
    // relationships with both partial participation apply the cross reference approach
    private static void bin1to1Convert(ArrayList<Relationship> rels, RelModel rm) {
        String participation;
        for (Relationship r : rels) {
            participation = r.getParticipations();
            if (participation.equals("FP")) {
                foreignKeyApproach(r, rm);
            } else if (participation.equals("FF")) {
                mergedRelationApproach(r, rm);
            } else if (participation.equals("PP")) {
                crossReferenceApproach(r, rm);
            } else {
                System.err.println("Error obtaining participations of " + r.toString());
            }//if-else
        }//for-each
    }//bin1to1Convert
    
    //bin1toNConvert: convert 1-N relationships into relations
    // relationships with a full-participation entity apply the foreign key approach
    // relationships with both entities partial participation apply the cross reference approach
    private static void bin1toNConvert(ArrayList<Relationship> rels, RelModel rm) {
        String participation;
        for (Relationship r : rels) {
            participation = r.getParticipations();
            if (participation.equals("FP") || participation.equals("FF")) {
                foreignKeyApproach(r, rm);
            } else {
                crossReferenceApproach(r, rm);
            }//if-else
        }//for-each
    }//bin1to1Convert
    
    //binMtoNConvert: convert M-N relationships into relations
    // only the cross reference approach may be applied to relationships of this type
    private static void binMtoNConvert(ArrayList<Relationship> rels, RelModel rm) {
        for (Relationship r : rels) {
            crossReferenceApproach(r, rm);
        }//for-each
    }//bin1to1Convert
    
    //binRelConvert: convert relationships into relations
    // call separate functions for each cardinality combo
    //  + 1-1 relationships
    //  + 1-N relationships
    //  + M-N relationships
    private static void binRelConvert(ERModel erm, RelModel rm) {
        erm.organizeRel();
        bin1to1Convert(erm.getBin1to1(), rm);
        bin1toNConvert(erm.getBin1toN(), rm);
        binMtoNConvert(erm.getBinMtoN(), rm);
    }//relConvert
    
    //multivaluedAttrConvert: convert multivalued attributes into relations
    private static void multivaluedAttrConvert(ERModel erm, RelModel rm) {
        ArrayList<String> keys;
        for (MultivaluedAttrTuple mvat : erm.getMVAT()) {
            for (EAttribute e : mvat.getMva()) {
                rm.addRelation(e.getName());
                keys = addPKasFK(e.getName(), rm.getRelation(mvat.getEntName()), rm, "");
                rm.addAttr(e.getName(), e.getName() + "(attr)");
                keys.add(e.getName() + "(attr)");
                rm.getRelation(e.getName()).setKeyAttr(keys);
            }//for-each
        }//for-each
    }//multivaluedAttrConvert
    
    public static void main(String args[]) {
        ERModel er = new ERModel("Company");
        ArrayList<String> keys = new ArrayList<>();
        er.addRegEntity("Employee");
        er.addAttrToEntity("Employee", "Employee", "SSN", "S");
        er.addAttrToEntity("Employee", "Employee", "Address", "C");
        er.addAttrToEntity("Employee", "Address", "Street", "S");
        er.addAttrToEntity("Employee", "Address", "House#", "S");
        keys.add("SSN");
        er.setKeyAttrOfEnt("Employee", keys);
        er.addRegEntity("ProductType");
        er.addAttrToEntity("ProductType", "ProductType", "Category", "M");
        er.addAttrToEntity("ProductType", "ProductType", "Type", "S");
        keys.clear();
        keys.add("Type");
        er.setKeyAttrOfEnt("ProductType", keys);
        er.addWeakEntity("Product", "HasType");
        er.addAttrToEntity("Product", "Product", "Name", "S");
        er.addAttrToEntity("Product", "Product", "AvailableCountries", "M");
        keys.clear();
        keys.add("Name");
        er.setKeyAttrOfEnt("Product", keys);
        er.addRelationship("HasType", er.getEntity("ProductType"), er.getEntity("Product"), "Partial", "Full", "1", "N");
        er.addRelationship("WorksOn", er.getEntity("Employee"), er.getEntity("Product"), "Full", "Partial", "N", "M");
        er.addAttrToRelationship("WorksOn", "WorksOn", "Hours", "Simple");
        er.addRelationship("Supervises", er.getEntity("Employee"), er.getEntity("Employee"), "Full", "Partial", "1", "N");
        er.addRegEntity("Department");
        er.addAttrToEntity("Department", "Department", "DeptNo", "S");
        keys.clear();
        keys.add("DeptNo");
        er.setKeyAttrOfEnt("Department", keys);
        er.addRelationship("WorksFor", er.getEntity("Employee"), er.getEntity("Department"), "Full", "Full", "N", "1");
        er.addRelationship("Manages", er.getEntity("Employee"), er.getEntity("Department"), "Partial", "Full", "1", "1");
        
        er.display();
        Converter.ERtoRel(er).display();
    }
    
}
