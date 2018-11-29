/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Conversion.Converter;
import EntityRelationship.ERModel;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caldw
 */
public class ERMtoRM {
    
    public static void main(String args[]) {
        
        startup();
        
    }
    
    //startup: start the process of model formation
    private static void startup() {
        Scanner scan = new Scanner(System.in);
        
        System.err.println("Would you like to load a file, or start a new model from scratch? (f/n)");
        String method = scan.next();
        if (method.equalsIgnoreCase("N")) {
            System.err.print("You have chosen to start a new model");
            newModel();
        } else if (method.equalsIgnoreCase("F")) {
            System.err.print("You have chosen to load a previous model");
            loadModel();
        } else {
            System.err.println("Please enter either f or n:");
            startup();
        }//if-else
        
    }//startup
 
    private static void addEntity(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String name, weak;
        System.err.print("What is the name of the Entity ");
        name = scan.next();
        System.err.print("Is it a weak identity? ");
        weak = scan.next();
        if (weak.equalsIgnoreCase("YES") || weak.equalsIgnoreCase("Y")) {
            String idRel;
            System.err.print("What is the name of the identifying relationship? ");
            idRel = scan.next();
            erm.addWeakEntity(name, idRel);
        } else {
            erm.addRegEntity(name);
        }//if-else
    }//addEntity
    
    private static void addRelationship(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String name, LE, RE, LP, RP, LC, RC;
        System.err.print("What is the name of the Relationship? ");
        name = scan.next();
        System.err.print("What is the first entity involved? ");
        LE = scan.next();
        System.err.print("Does the entity have full or partial participation? ");
        LP = scan.next();
        System.err.print("What is the entity's cardinality(1,n,m,or min,max)? ");
        LC = scan.next();
        System.err.print("What is the second entity involved? ");
        RE = scan.next();
        System.err.print("Does the entity have full or partial participation? ");
        RP = scan.next();
        System.err.print("What is the entity's cardinality(1,n,m,or min,max)? ");
        RC = scan.next();
        erm.addRelationship(name, erm.getEntity(LE), erm.getEntity(RE), LP, RP, LC, RC);
    }//addRelationship
    
    private static void addAttribute(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String aName, EntOrRel, erName, attach, type;
        System.err.print("What is the name of the attribute? ");
        aName = scan.next();
        System.err.println("What is the type of the attribute?");
        System.err.print("Options: Simple(S), Composite(C), Multivalued(M), Derived(D)");
        type = scan.next();
        System.err.print("Is this being added to an Entity(E) or Relationship(R)? ");
        EntOrRel = scan.next();
        if (EntOrRel.equalsIgnoreCase("ENTITY") || EntOrRel.equalsIgnoreCase("E")) {
            System.err.print("To which Entity are we adding an attribute? ");
            erName = scan.next();
            System.err.println("What is the name of the item that the attribute is attached to? ");
            System.err.print("(Give the name of the Entity or a Composite Attribute): ");
            attach = scan.next();
            erm.addAttrToEntity(erName, attach, aName, type);
        } else if (EntOrRel.equalsIgnoreCase("RELATIONSHIP") || EntOrRel.equalsIgnoreCase("R")) {
            System.err.print("To which Relationship are we adding an attribute? ");
            erName = scan.next();
            erm.addAttrToRelationship(aName, type, erName);
        }//if-else
    }//addAttribute
    
    private static void saveModel(ERModel erm) {
        System.err.println("Saving Entity-Relationship model to filename " + erm.getName() + ".dat");        
    }//saveModel
    
    private static void newModel() {
        Scanner scan = new Scanner(System.in);
        String name, action;
        System.err.print("What is the name of the model? ");
        name = scan.next();
        ERModel erm = new ERModel(name);
        do {
            //wait a sec before continuing
            //give user a chance to breath
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ERMtoRM.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println("What would you like to do with " + name + "?");
            System.err.println("Add: Entity(E), Relationship(R), Attribute(A)");
            System.err.println("Convert(C)\nDisplay(D)\nExit\\Quit");
            action = scan.next();
            
            if (action.equalsIgnoreCase("ENTITY") || action.equalsIgnoreCase("E")) {
                addEntity(erm);
            } else if (action.equalsIgnoreCase("RELATIONSHIP") || action.equalsIgnoreCase("R")) {
                addRelationship(erm);
            } else if (action.equalsIgnoreCase("ATTRIBUTE") || action.equalsIgnoreCase("A")) {
                addAttribute(erm);
            } else if (action.equalsIgnoreCase("CONVERT") || action.equalsIgnoreCase("C")) {
                Converter.ERtoRel(erm);
            } else if (action.equalsIgnoreCase("DISPLAY") || action.equalsIgnoreCase("D")) {
                erm.display();
            } else if (!action.equalsIgnoreCase("EXIT") && !action.equalsIgnoreCase("QUIT")) {
                System.err.println("Please choose one of the listed options:\n");
            }//if-else            
            
        } while (!action.equalsIgnoreCase("exit") && !action.equalsIgnoreCase("quit"));
        
        System.err.print("Would you like to save this model for future use? ");
        action = scan.next();
        if (action.equalsIgnoreCase("YES") || action.equalsIgnoreCase("Y")) {
            saveModel(erm);
        }//if
        System.err.println("Now exiting system...");
    }//newModel
    
    private static void loadModel() {
        
    }//loadModel
    
}
