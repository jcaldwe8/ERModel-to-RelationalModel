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
 * EAttribute: an EAttribute has a name and can be simple, composite, or multivalued
 */
public class EAttribute {
    
    private String name;
    private AttrType type;
    private final int level;
    private String indent;
    private ArrayList<EAttribute> comp; //simple attribute components that make up a composite attribute
    
    public EAttribute(String name, AttrType type, int level) {
        this.name = name; //name of the attribute
        this.type = type; //type of the attribute
        comp = new ArrayList<>(); //initialize a composite's sub-attributes if needed
        this.level = level;
        indent = "";
        for (int i = 0; i < level; i++)
            indent = indent + "  ";
    }//constructor
    
    //alterType: change the type of the attribute if needed
    public void alterType(AttrType newType) { type = newType; }
    
    //alterName: change the name if desired
    public void alterName(String newName) { name = newName; }
    
    //addCompAttr: add an attribute to a composite's parts
    public void addCompAttr(String cname, AttrType ctype) {
        if (type != AttrType.COMPOSITE) {
            System.err.println("Cannot add branching attributes - Not a COMPOSITE attribute");
            return;
        }
        EAttribute a = new EAttribute(cname, ctype, level + 1);
        comp.add(a);
    }//addCompAttr
    
    //isSimple: is the attribute simple?
    public boolean isSimple() {
        if (this.getType() == AttrType.SIMPLE)
            return true;
        return false;
    }//isSimple
    
    //isComposite: is the attribute composite?
    public boolean isComposite() {
        if (this.getType() == AttrType.COMPOSITE)
            return true;
        return false;
    }//isComposite
    
    //isMultiValued: is the attribute multivalued?
    public boolean isMultiValued() {
        if (this.getType() == AttrType.MULTIVALUED)
            return true;
        return false;
    }//isMultiValued
    
    //print information to a string
    @Override
    public String toString() {
        String ret = name + " (" + type.toString() + ")";
        for (EAttribute a : comp) {
            ret = ret + "\n" + indent + " -> " + a.toString();
        }
        return ret;
    }//toString

    public void display() {
        System.err.println(this.toString());
    }
    
    public EAttribute getSubAttribute(String name) {
        if (this.name.equals(name))
            return this;
        for (EAttribute a : comp) {
            if (a.getName().equals(name)) {
                return a;
            } else {
                if (a.getType() == AttrType.COMPOSITE) {
                    EAttribute b = a.getSubAttribute(name);
                    if (b != null)
                        return b;
                }
            }//if
        }//for
        return null;
    }//getSubAttribute
    
    public String getName() {
        return name;
    }

    public AttrType getType() {
        return type;
    }

    public ArrayList<EAttribute> getComp() {
        return comp;
    }
    
    public static void main(String args[]) {
        EAttribute location = new EAttribute("Location", AttrType.MULTIVALUED, 0);
        location.display();
        EAttribute numOfItems = new EAttribute("No. of Items", AttrType.DERIVED, 0);
        numOfItems.display();
        
        EAttribute addr = new EAttribute("Address", AttrType.COMPOSITE, 0);
        addr.addCompAttr("Street", AttrType.SIMPLE);
        addr.addCompAttr("Apt. No.", AttrType.SIMPLE);
        addr.addCompAttr("State", AttrType.SIMPLE);
        addr.addCompAttr("Zip Code", AttrType.SIMPLE);
        addr.display();
        
        EAttribute food = new EAttribute("Food", AttrType.COMPOSITE, 0);
        
        food.addCompAttr("Fats", AttrType.COMPOSITE);
        food.getSubAttribute("Fats").addCompAttr("Candy", AttrType.COMPOSITE);
        food.getSubAttribute("Candy").addCompAttr("Chocolate", AttrType.SIMPLE);
        food.getSubAttribute("Candy").addCompAttr("Sugar", AttrType.SIMPLE);
        food.getSubAttribute("Fats").addCompAttr("Fried", AttrType.SIMPLE);
        
        food.addCompAttr("Grains", AttrType.COMPOSITE);
        food.getSubAttribute("Grains").addCompAttr("Bread", AttrType.SIMPLE);
        food.getSubAttribute("Grains").addCompAttr("Pasta", AttrType.SIMPLE);
        food.getSubAttribute("Grains").addCompAttr("Starch", AttrType.SIMPLE);
        
        food.addCompAttr("Dairy", AttrType.COMPOSITE);
        food.getSubAttribute("Dairy").addCompAttr("Milk", AttrType.SIMPLE);
        food.getSubAttribute("Dairy").addCompAttr("Yogurt", AttrType.SIMPLE);
        food.getSubAttribute("Dairy").addCompAttr("Cheese", AttrType.SIMPLE);
        
        food.addCompAttr("Meat", AttrType.COMPOSITE);
        food.getSubAttribute("Meat").addCompAttr("Beef", AttrType.SIMPLE);
        food.getSubAttribute("Meat").addCompAttr("Chicken", AttrType.SIMPLE);
        food.getSubAttribute("Meat").addCompAttr("Pork", AttrType.SIMPLE);
        food.getSubAttribute("Meat").addCompAttr("Beans", AttrType.SIMPLE);
        food.getSubAttribute("Meat").addCompAttr("Legumes", AttrType.SIMPLE);
        
        food.addCompAttr("Vegetables", AttrType.COMPOSITE);
        food.getSubAttribute("Vegetables").addCompAttr("Root", AttrType.SIMPLE);
        food.getSubAttribute("Vegetables").addCompAttr("Leafy Green", AttrType.SIMPLE);
        food.getSubAttribute("Vegetables").addCompAttr("Cruciferous", AttrType.SIMPLE);
        food.getSubAttribute("Vegetables").addCompAttr("Edible Plant Stem", AttrType.SIMPLE);
        food.getSubAttribute("Vegetables").addCompAttr("Marrow", AttrType.SIMPLE);
        food.getSubAttribute("Vegetables").addCompAttr("Allium", AttrType.SIMPLE);
        
        food.addCompAttr("Fruits", AttrType.COMPOSITE);
        food.getSubAttribute("Fruits").addCompAttr("Stone", AttrType.SIMPLE);
        food.getSubAttribute("Fruits").addCompAttr("Berry", AttrType.SIMPLE);
        food.getSubAttribute("Fruits").addCompAttr("Hard", AttrType.SIMPLE);
        food.getSubAttribute("Fruits").addCompAttr("Citrus", AttrType.SIMPLE);
        
        food.display();
    }
    
}
