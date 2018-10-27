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
 * ERModel: an ERModel contains Entities with attributes and Relationships that relate those entities
 */
public class ERModel {
    
    ArrayList<Entity> Entities;
    ArrayList<Relationship> Relationships;
    
    public ERModel() {
        Entities = new ArrayList<>();
        Relationships = new ArrayList<>();
    }//constructor
    
}
