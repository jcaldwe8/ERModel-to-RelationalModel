/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityRelationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author caldw
 * Entity: an Entity has a name and contains attributes
 *         it can be regular or weak (with an identifying relationship)
 */
public class Entity {
    
    private String name;
    private EntityType type;
    private String idRel; //name of identifying relationship
    private ArrayList<EAttribute> attr;
    private Set<String> keyAttr;
    private MultivaluedAttrTuple mvat;
    private boolean newMVA;
    
    public Entity(String name, EntityType type) {
        this.name = name;
        this.type = type;
        attr = new ArrayList<>();
        keyAttr = new HashSet<>();
        idRel = "";
        mvat = new MultivaluedAttrTuple(new ArrayList<EAttribute>(), new HashSet<>(), "");
        newMVA = false;
    }//constructor(for strong)
    
    public Entity(String name, EntityType type, String idRel) {
        this.name = name;
        this.type = type;
        attr = new ArrayList<>();
        keyAttr = new HashSet<>();
        if (type != EntityType.WEAK)
            return;
        this.idRel = idRel;
        mvat = new MultivaluedAttrTuple(new ArrayList<EAttribute>(), new HashSet<>(), "");
        newMVA = false;
    }//constructor(for weak)
    
    //addAttr: add an attribute either to the Entity or to a Composite EAttribute
    // attach: what the new attribute will be `attached' to
    // aname: name of the added attribute
    // type: type of the added attribute
    public void addAttr(String attach, String aName, AttrType type) {
        if (attach.equals(name)) {
            attr.add(new EAttribute(aName, type, 0));
        } else {
            EAttribute b;
            for (EAttribute a : attr) {
                b = a.getSubAttribute(attach);
                if (b != null && b.isComposite()) {
                    b.addCompAttr(aName, type);
                }//it
            }//for
        }//if-else
        if (type == AttrType.MULTIVALUED)
            newMVA = true; //added a new multivalued attribute
    }//addAttr

    //setKeyAttr: set the key attributes of this entity type
    public void setKeyAttr(ArrayList<String> keys) {
        keyAttr.clear();
        boolean in;
        for (String a : keys) {
            in = false;
            for (EAttribute at : attr) {
                if (at.getName().equals(a)) {
                    in = true;
                    break;
                }//if
            }
            if (in) {
                keyAttr.add(a);
            } else {
                System.err.println("Attribute " + a + " is not currently in the array of attributes!!!\nAdd it before declaring it part of the key for the entity!!!");
            }//if-else
        }//for-each
    }//setKeyAttr
    
    public boolean isWeak() {
        if (this.getType() == EntityType.WEAK)
            return true;
        return false;
    }//isWeak
    
    public String getName() {
        return name;
    }
    
    public EntityType getType() {
        return type;
    }
    
    public ArrayList<EAttribute> getAttr() {
        return attr;
    }
    
    public EAttribute getAttr(String a) {
        try {
            for (EAttribute e : this.getAttr()) {
                //check against e's name and any component attributes
                EAttribute sub = e.getSubAttribute(a); 
                if (sub != null)
                    return sub; //return result if one was found
            }//for-each
            System.err.println("Couldn't find Attribute with name " + a + "\nReturning NULL element");
            throw new ElementNotFound("The Attribute with name " + a + " was not found!!");
        } catch (ElementNotFound e) {
            e.printStackTrace();
        }
        return new EAttribute("NULL", AttrType.NULL, -1);
    }//getAttr
    
    public MultivaluedAttrTuple getMultivaluedAttrTuple() {
        if (newMVA || !mvat.getEntName().equals(this.getName())) {
            ArrayList<EAttribute> mva = new ArrayList<>();
            for (EAttribute e : this.getAttr()) {
                mva = e.compileMVA(mva); //get all mva's associated with attribute e
            }//for-each
            mvat = new MultivaluedAttrTuple(mva, this.getKey(), this.getName());
        }//if
        return mvat;
    }//getMulivaluedAttrTuple
    
    public Set<String> getKey() {
        return keyAttr;
    }
    
    public String getIDRel() {
        return idRel;
    }//getIDRel
    
    @Override
    public String toString() {
        String ret;
        ret = name + "(" + type + ")";
        if (type == EntityType.WEAK) {
            ret = ret + "\nIdentifying Relationship: " + idRel;
        }//if
        ret += "\n- - - - - -";
        String key = "";
        for (EAttribute a : attr) {
            if (keyAttr.contains(a.getName())) { 
                key = "*"; 
            }//if
            ret += "\n" + key + a.toString();
            key = "";
        }//for
        if (attr.isEmpty())
            ret += "\n(no attributes)";
        ret += "\n";
        return ret;
    }//toString
    
    public void display() {
        System.err.println("\n" + this.toString());
    }
    
    //toFile: produce a string version of this entity for a file
    public String toFile() {
        String content;
        
        if (this.isWeak()) {
            content = "EW\n";
            content += this.getName() + "\n";
            content += this.getIDRel() + "\n";
        } else {
            content = "ER\n";
            content += this.getName() + "\n";
        }//if-else
        
        content += "A>\n";
        for (EAttribute a : attr) {
            content += a.toFile();
        }//for-each
        content += "<A\n";
        
        content += "K>\n";
        for (String k : keyAttr) {
            content += k + "\n";
        }//for-each
        content += "<K\n";
        
        return content;
    }//toFile
    
    public static void main(String args[]) {
        Entity car = new Entity("Car", EntityType.REG);
        car.addAttr("Car", "Licence Plate#", AttrType.SIMPLE);
        car.addAttr("Car", "Registration", AttrType.COMPOSITE);
        car.addAttr("Registration", "State", AttrType.SIMPLE);
        car.addAttr("Registration", "Number", AttrType.SIMPLE);
        ArrayList<String> carKey = new ArrayList<>();
        carKey.add("Licence Plate#");
        car.setKeyAttr(carKey);
        car.display();
        
        Entity bankBranch = new Entity("Bank Branch", EntityType.WEAK, "Branch");
        bankBranch.addAttr("Bank Branch", "Branch No.", AttrType.SIMPLE);
        bankBranch.addAttr("Bank Branch", "Location", AttrType.SIMPLE);
        bankBranch.display();
        ArrayList<String> bankBranchKey = new ArrayList<>();
        bankBranchKey.add("Branch No.");
        bankBranchKey.add("Location");
        bankBranch.setKeyAttr(bankBranchKey);
        bankBranch.display();
    }
    
}
