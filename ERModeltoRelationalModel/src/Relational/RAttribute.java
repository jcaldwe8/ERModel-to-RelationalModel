/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Relational;

/**
 *
 * @author caldw
 */
public class RAttribute {
    
    private String name;
    private ForeignKey FK;
    
    public RAttribute(String name) {
        this.name = name;
        FK = new ForeignKey("NULL", "NULL");
    }//Constructor
    
    public RAttribute(String name, String relRef, String attrRef) {
        this.name = name;
        this.FK = new ForeignKey(relRef, attrRef);
    }//Constructor
    
    @Override
    public String toString() {
        String ret;
        ret = name;
        if (!FK.getAttrRef().equals("NULL")) {
            ret += " -> " + FK.getRelRef() + "." + FK.getAttrRef();
        }
        return ret;
    }//toString
    
    public void display() {
        System.err.println("\n" + this.toString());
    }
    
    public String getName() {
        return name;
    }
    
    public ForeignKey getFK() {
        return FK;
    }
    
    public static void main(String args[]) {
        RAttribute ssn = new RAttribute("SSN");
        RAttribute essn = new RAttribute("Essn", "Employee", "SSN");
        ssn.display();
        essn.display();
    }
    
}
