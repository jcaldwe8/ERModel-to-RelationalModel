/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

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
    
    public Relationship(String name) {
        this.name = name;
    }//constructor
    
    public Relationship(String name, Entity LE, Entity RE, Participation LP, Participation RP, Cardinality LC, Cardinality RC) {
        this.name = name;
        leftEnt = LE;
        rightEnt = RE;
        leftPar = LP;
        rightPar = RP;
        leftCar = LC;
        rightCar = RC;
    }//constructor

    @Override
    public String toString() {
        String ret;
        ret = "Relationship:" + name + "\n= = = = = =\n";
        ret = ret + "First Entity: " + leftEnt.getName();
        ret = ret + "Paricipation: " + leftPar.toString();
        ret = ret + "Cardinality: " + leftCar.toString();
        ret = ret + "- - - - - -";
        ret = ret + "Second Entity: " + rightEnt.getName();
        ret = ret + "Paricipation: " + rightPar.toString();
        ret = ret + "Cardinality: " + rightCar.toString();
        return ret;
    }//toString
    
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
    
    
    
}
