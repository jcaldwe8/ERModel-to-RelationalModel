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
    
}
