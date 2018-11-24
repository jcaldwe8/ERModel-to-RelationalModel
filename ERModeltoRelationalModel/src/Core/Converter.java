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
public class Converter {
    
    //ERtoRel: convert an entity-relationship model to a relation model
    public static RelModel ERtoRel(ERModel erm) {
        RelModel rm = new RelModel(erm.getName());
        regEntConvert(erm, rm);
        weakEntConvert(erm, rm);
        binRelConvert(erm, rm);
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
        if (r.getLeftEnt().getName().equals(e.getName())) {
            par = r.getRightEnt();
        } else {
            par = r.getLeftEnt();
        }//if-else
        for (String a : par.getKey()) {
            if (par.getAttr(a).isSimple()) {
                rm.addAttr(eName, a, par.getName(), a);
                parKeys.add(a);
            } else if (par.getAttr(a).isComposite()) {
                parName = par.getName();
                for (EAttribute comp : getSimpleComponents(par.getAttr(a))) {
                    rm.addAttr(eName, comp.getName(), parName, comp.getName());
                    parKeys.add(comp.getName());
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
    
    private static ArrayList<String> addPKasFK(String newR, Relation ref, RelModel rm) {
        ArrayList<String> keys = new ArrayList<>();
        for (String ra : ref.getKey()) {
            rm.addAttr(newR, ra, ref.getName(), ra);
            keys.add(ra);
        }//for-each
        return keys;
    }//addPKasFK
    
    private static void foreignKeyApproach(Relationship r, RelModel rm) {
        
    }//foreignKeyApproach
    
    private static void mergedRelationApproach(Relationship r, RelModel rm) {
        
    }//mergedRelationApproach
    
    private static void crossReferenceApproach(Relationship r, RelModel rm) {
        ArrayList<String> keys = new ArrayList<>();
        rm.addRelation(r.getName());
        keys = addPKasFK(r.getName(), rm.getRelation(r.getLeftEnt().getName()), rm);
        keys.addAll(addPKasFK(r.getName(), rm.getRelation(r.getRightEnt().getName()), rm));
        rm.getRelation(r.getName()).setKeyAttr(keys);
        for (EAttribute ea : r.getAttr()) {
            rm.addAttr(r.getName(), ea.getName());
        }//for-each
    }//crossReferenceApproach
    
    private static void bin1to1Convert(ArrayList<Relationship> rels, RelModel rm) {
        for (Relationship r : rels) {
            if (r.getParticipations().equals("FP")) {
                foreignKeyApproach(r, rm);
            } else if (r.getParticipations().equals("FF")) {
                mergedRelationApproach(r, rm);
            } else if (r.getParticipations().equals("PP")) {
                crossReferenceApproach(r, rm);
            } else {
                System.err.println("Error obtaining participations of " + r.toString());
            }//if-else
        }//for-each
    }//bin1to1Convert
    
    private static void bin1toNConvert(ArrayList<Relationship> rels, RelModel rm) {
        
    }//bin1to1Convert
    
    private static void binMtoNConvert(ArrayList<Relationship> rels, RelModel rm) {
        
    }//bin1to1Convert
    
    private static void binRelConvert(ERModel erm, RelModel rm) {
        erm.organizeRel();
        bin1to1Convert(erm.getBin1to1(), rm);
        bin1toNConvert(erm.getBin1toN(), rm);
        binMtoNConvert(erm.getBinMtoN(), rm);
    }//relConvert
    
    public static void main(String args[]) {
        ERModel er = new ERModel("Company");
        er.addRegEntity("Employee");
        er.addAttrToEntity("Employee", "Employee", "SSN", "S");
        er.addAttrToEntity("Employee", "Employee", "Address", "C");
        er.addAttrToEntity("Employee", "Address", "Street", "S");
        er.addAttrToEntity("Employee", "Address", "House#", "S");
        er.setKeyAttrOfEnt("Employee", "SSN");
        er.addRegEntity("ProductType");
        er.addAttrToEntity("ProductType", "ProductType", "Category", "M");
        er.addAttrToEntity("ProductType", "ProductType", "Type", "S");
        er.setKeyAttrOfEnt("ProductType", "Type");
        er.addWeakEntity("Product", "HasType");
        er.addAttrToEntity("Product", "Product", "Name", "S");
        er.setKeyAttrOfEnt("Product", "Name");
        er.addRelationship("HasType", er.getEntity("ProductType"), er.getEntity("Product"), "Partial", "Full", "N", "1");
        
        er.display();
        Converter.ERtoRel(er).display();
    }
    
}
