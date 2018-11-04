/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Core.Cardinality.CardVal;
import java.util.ArrayList;

/**
 *
 * @author caldw
 * Relationship: a relationship has a name and relates two entities
 *               the entities both have a participation and a cardinality
 *               the relationship may also have some attributes
 */
public class Relationship {
    
    String name;
    String leftEnt, rightEnt;
    Participation leftPar, rightPar;
    Cardinality leftCar, rightCar;
    ArrayList<Attribute> attr;
    
    public Relationship(String name) {
        this.name = name;
        attr = new ArrayList<>();
    }//constructor
    
    public Relationship(String name, String LE, String RE) {
        this.name = name;
        leftEnt = LE;
        rightEnt = RE;
        attr = new ArrayList<>();
    }//constructor
    
    public Relationship(String name, String LE, String RE, Participation LP, Participation RP, Cardinality LC, Cardinality RC) {
        this.name = name;
        leftEnt = LE;
        rightEnt = RE;
        leftPar = LP;
        rightPar = RP;
        leftCar = LC;
        rightCar = RC;
        attr = new ArrayList<>();
    }//constructor

    public void addAttribute(Attribute a) {
        attr.add(a);
    }//addAttribute
    
    @Override
    public String toString() {
        String ret;
        ret = "Relationship: " + name + "\n= = = = = = =";
        ret = ret + "\nFirst Entity: " + leftEnt;
        ret = ret + "\nParicipation: " + leftPar.toString();
        ret = ret + "\nCardinality: " + leftCar.toString();
        ret = ret + "\n- - - - - - -";
        ret = ret + "\nSecond Entity: " + rightEnt;
        ret = ret + "\nParicipation: " + rightPar.toString();
        ret = ret + "\nCardinality: " + rightCar.toString();
        return ret + "\n";
    }//toString
    
    public void display() {
        System.err.println("\n" + this.toString());
    }//display
    
    ///////////////////////
    // Getters + Setters //
    ///////////////////////
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeftEnt() {
        return leftEnt;
    }

    public void setLeftEnt(String leftEnt) {
        this.leftEnt = leftEnt;
    }

    public String getRightEnt() {
        return rightEnt;
    }

    public void setRightEnt(String rightEnt) {
        this.rightEnt = rightEnt;
    }

    public Participation getLeftPar() {
        return leftPar;
    }

    public void setLeftPar(Participation leftPar) {
        this.leftPar = leftPar;
    }

    public Participation getRightPar() {
        return rightPar;
    }

    public void setRightPar(Participation rightPar) {
        this.rightPar = rightPar;
    }

    public Cardinality getLeftCar() {
        return leftCar;
    }

    public void setLeftCar(Cardinality leftCar) {
        this.leftCar = leftCar;
    }

    public Cardinality getRightCar() {
        return rightCar;
    }

    public void setRightCar(Cardinality rightCar) {
        this.rightCar = rightCar;
    }
    
    public static void main(String args[]) {
        Relationship worksFor = new Relationship("Works_For");
        Participation pWorksForEmp = Participation.FULL;
        Participation pWorksForDep = Participation.PARTIAL;
        Cardinality cWorksForEmp = new Cardinality(1,3);
        Cardinality cWorksForDep = new Cardinality(CardVal.N);
        worksFor.setLeftEnt("Employee");
        worksFor.setRightEnt("Department");
        worksFor.setLeftPar(pWorksForEmp);
        worksFor.setRightPar(pWorksForDep);
        worksFor.setLeftCar(cWorksForEmp);
        worksFor.setRightCar(cWorksForDep);
        worksFor.display();
    }
    
}
