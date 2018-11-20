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
    
    private static void regEntConvert(ERModel erm, RelModel rm) {
        String name;
        boolean key = false;
        for (Entity e : erm.getRegEntities()) {
            ArrayList<String> keys = new ArrayList<>();
            name = e.getName();
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
            rm.getRelation(name).setKeyAttr(keys); //set the keys for this relation
        }//for-each
    }//regEntConvert
    
    private static void weakEntConvert(ERModel erm, RelModel rm) {
        
    }//weakEntConvert
    
    
    
    public static void main(String args[]) {
        ERModel er = new ERModel("Company");
        er.addRegEntity("Employee");
        er.addAttrToEntity("Employee", "Employee", "SSN", "S");
        er.addAttrToEntity("Employee", "Employee", "Address", "C");
        er.addAttrToEntity("Employee", "Address", "Street", "S");
        er.addAttrToEntity("Employee", "Address", "House#", "S");
        er.addRegEntity("ProductType");
        er.addAttrToEntity("ProductType", "ProductType", "Category", "M");
        er.addWeakEntity("Product", "HasType");
        er.addAttrToEntity("Product", "Product", "Name", "S");
        
        Converter.ERtoRel(er).display();
    }
    
}
