/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Core.Cardinality.CardVal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author caldw
 * Relationship: a relationship has a name and relates two entities
 *               the entities both have a participation and a cardinality
 *               the relationship may also have some attributes
 */
public class Relationship {
    
    String name;
    Entity leftEnt, rightEnt;
    Participation leftPar, rightPar;
    Cardinality leftCar, rightCar;
    ArrayList<EAttribute> attr;
    
    public Relationship(String name) {
        this.name = name;
        attr = new ArrayList<>();
        leftEnt = new Entity("NULL1", EntityType.NULL);
        rightEnt = new Entity("NULL2", EntityType.NULL);
        leftPar = Participation.NULL;
        rightPar = Participation.NULL;
        leftCar = new Cardinality(CardVal.NULL);
        rightCar = new Cardinality(CardVal.NULL);
        attr = new ArrayList<>();
    }//constructor
    
    public Relationship(String name, Entity LE, Entity RE) {
        this.name = name;
        leftEnt = LE;
        rightEnt = RE;
        leftPar = Participation.NULL;
        rightPar = Participation.NULL;
        leftCar = new Cardinality(CardVal.NULL);
        rightCar = new Cardinality(CardVal.NULL);
        attr = new ArrayList<>();
    }//constructor
    
    public Relationship(String name, Entity LE, Entity RE, String LP, String RP, String LC, String RC) {
        this.name = name;
        leftEnt = LE;
        rightEnt = RE;
        leftPar = stringToPart(LP);
        rightPar = stringToPart(RP);
        leftCar = stringToCard(LC);
        rightCar = stringToCard(RC);
        attr = new ArrayList<>();
    }//constructor
    
    private Participation stringToPart(String part) {
        if (part.equalsIgnoreCase("PARTIAL") || part.equalsIgnoreCase("P"))
            return Participation.PARTIAL;
        return Participation.FULL; //default to FULL if not specified as partial
    }//stringToPart
    
    private Cardinality stringToCard(String card) {
        if (card.equalsIgnoreCase("N"))
            return new Cardinality(CardVal.N);
        if (card.equalsIgnoreCase("M"))
            return new Cardinality(CardVal.M);
        if (card.equalsIgnoreCase("1") || card.equalsIgnoreCase("ONE"))
            return new Cardinality(CardVal.ONE);
        List<String> minmax = Arrays.asList(card.split(","));
        return new Cardinality(Integer.valueOf(minmax.get(0)), Integer.valueOf(minmax.get(1)));
    }//stringToCard
    
    public void addAttribute(EAttribute a) {
        attr.add(a);
    }//addAttribute
    
    public boolean isIDRel() {
        if (leftEnt.isWeak() && leftEnt.getIDRel().equals(this.getName())) {
            return true;
        } else if (rightEnt.isWeak() && rightEnt.getIDRel().equals(this.getName())) {
            return true;
        }//if-else
        return false;
    }//isIDRel
    
    public String getType() {
        if (leftCar.isOne() && rightCar.isOne()) {
            return "1:1";
        } else if ((leftCar.isOne() && rightCar.isN()) || (leftCar.isN() && rightCar.isOne())) {
            return "1:N";
        } else if (leftCar.isN() && rightCar.isN()) {
            return "M:N";
        } else if (leftCar.isRange()) {
            return rightCar.toString() + ":Range";
        } else if (rightCar.isRange()) {
            return leftCar.toString() + ":Range";
        }//if-else
        
        return "Range:Range";
    }//getType
    
    ///////////////////
    // toString info //
    ///////////////////
    
    @Override
    public String toString() {
        String ret;
        ret = name + "\n= = = = = = =";
        ret = ret + "\nFirst Entity: " + leftEnt.getName() + "(" + leftEnt.getType() + ")";
        ret = ret + "\nParicipation: " + leftPar.toString();
        ret = ret + "\nCardinality: " + leftCar.toString();
        ret = ret + "\n- - - - - - -";
        ret = ret + "\nSecond Entity: " + rightEnt.getName() + "(" + rightEnt.getType() + ")";
        ret = ret + "\nParicipation: " + rightPar.toString();
        ret = ret + "\nCardinality: " + rightCar.toString();
        return ret + "\n";
    }//toString
    
    public String visualString() {
        String LP = participationSymbol(leftPar);
        String RP = participationSymbol(rightPar);
        String ret;
        ret = leftEnt.getName() + "(" + leftEnt.getType() + ")";
        ret += "\n   " + LP + " " + leftCar.toString() + "\n";
        ret += name;
        ret += "\n   " + RP + " " + rightCar.toString() + "\n";
        ret += rightEnt.getName() + "(" + rightEnt.getType() + ")";
        return ret;
    }//visualString
    
    private String participationSymbol(Participation p) {
        if (p == Participation.FULL) {
            return "||";
        } else if (p == Participation.PARTIAL) {
            return " |";
        }
        return "ERROR";
    }
    
    public void display() {
        System.err.println("\n" + this.toString());
    }//display
    
    public void visDisplay() {
        System.err.println("\n" + this.visualString());
    }//visDisplay
    
    ///////////////////////
    // Getters + Setters //
    ///////////////////////
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entity getLeftEnt() {
        return leftEnt;
    }

    public void setLeftEnt(Entity leftEnt) {
        this.leftEnt = leftEnt;
    }

    public Entity getRightEnt() {
        return rightEnt;
    }

    public void setRightEnt(Entity rightEnt) {
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
        worksFor.setLeftEnt(new Entity("Employee", EntityType.REG));
        worksFor.setRightEnt(new Entity("Department", EntityType.REG));
        worksFor.setLeftPar(pWorksForEmp);
        worksFor.setRightPar(pWorksForDep);
        worksFor.setLeftCar(cWorksForEmp);
        worksFor.setRightCar(cWorksForDep);
        worksFor.display();
        worksFor.visDisplay();
    }
    
}
