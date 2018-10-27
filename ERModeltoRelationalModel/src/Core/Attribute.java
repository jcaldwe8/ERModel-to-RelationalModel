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
 * Attribute: an Attribute has a name and can be simple, composite, or multivalued
 */
public class Attribute {
    
    private String name;
    private AttrType type;
    private final int level;
    private String indent;
    private ArrayList<Attribute> comp; //simple attribute components that make up a composite attribute
    
    public Attribute(String name, AttrType type, int level) {
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
        Attribute a = new Attribute(cname, ctype, level + 1);
        comp.add(a);
    }//addCompAttr
    
    //print information to a string
    @Override
    public String toString() {
        String ret = name + " (" + type.toString() + ")";
        for (Attribute a : comp) {
            ret = ret + "\n" + indent + " -> " + a.toString();
        }
        return ret;
    }//toString

    public void display() {
        System.err.println(this.toString());
    }
    
    public Attribute getSubAttribute(String name) {
        if (this.name.equals(name))
            return this;
        for (Attribute a : comp) {
            if (a.getName().equals(name)) {
                return a;
            } else {
                if (a.getType() == AttrType.COMPOSITE) {
                    Attribute b = a.getSubAttribute(name);
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

    public ArrayList<Attribute> getComp() {
        return comp;
    }
    
    public static void main(String args[]) {
        Attribute location = new Attribute("Location", AttrType.MULTIVALUED, 0);
        location.display();
        Attribute numOfItems = new Attribute("No. of Items", AttrType.DERIVED, 0);
        numOfItems.display();
        
        Attribute addr = new Attribute("Address", AttrType.COMPOSITE, 0);
        addr.addCompAttr("Street", AttrType.SIMPLE);
        addr.addCompAttr("Apt. No.", AttrType.SIMPLE);
        addr.addCompAttr("State", AttrType.SIMPLE);
        addr.addCompAttr("Zip Code", AttrType.SIMPLE);
        addr.display();
        
        Attribute food = new Attribute("Food", AttrType.COMPOSITE, 0);
        
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
