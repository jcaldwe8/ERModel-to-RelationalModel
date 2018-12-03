/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Conversion.Converter;
import EntityRelationship.ERModel;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
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
            System.err.println("You have chosen to start a new model");
            newModel();
        } else if (method.equalsIgnoreCase("F")) {
            System.err.println("You have chosen to load a previous model");
            loadModel();
        } else {
            System.err.println("Please enter either f or n:");
            startup();
        }//if-else
        
    }//startup
 
    private static void addEntity(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String name, weak, addAttr;
        
        System.err.print("What is the name of the Entity ");
        name = scan.next();
        
        System.err.print("Is it a weak Entity? ");
        weak = scan.next();
        if (weak.equalsIgnoreCase("YES") || weak.equalsIgnoreCase("Y")) {
            String idRel;
            System.err.print("What is the name of the identifying relationship? ");
            idRel = scan.next();
            erm.addWeakEntity(name, idRel);
        } else {
            erm.addRegEntity(name);
        }//if-else
        
        System.err.print("Would you like to add an attribute to this Entity? ");
        addAttr = scan.next();
        while (addAttr.equalsIgnoreCase("YES") || addAttr.equalsIgnoreCase("Y")) {
            addAttribute(erm, name, true);
            System.err.print("Would you like to add another attribute to this Entity? ");
            addAttr = scan.next();
        }//while
        
        System.err.print("Would you like to set the primary key for this Entity? ");
        addAttr = scan.next();
        if (addAttr.equalsIgnoreCase("YES") || addAttr.equalsIgnoreCase("Y")) {
            setKeyAttribute(erm, name);
        }//while
    }//addEntity
    
    private static void addRelationship(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String name, LE, RE, LP, RP, LC, RC, addAttr;
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
        
        System.err.print("Would you like to add an attribute to this Relationship? ");
        addAttr = scan.next();
        while (addAttr.equalsIgnoreCase("YES") || addAttr.equalsIgnoreCase("Y")) {
            addAttribute(erm, name, false);
            System.err.print("Would you like to add another attribute to this Relationship? ");
            addAttr = scan.next();
        }//while
    }//addRelationship
    
    private static void addAttribute(ERModel erm, String erName, boolean isEnt) {
        Scanner scan = new Scanner(System.in);
        String aName, attach, type;
        System.err.print("What is the name of the attribute? ");
        aName = scan.next();
        System.err.println("What is the type of the attribute?");
        System.err.print("Options: Simple(S), Composite(C), Multivalued(M), Derived(D) ");
        type = scan.next();
        if (isEnt) {
            System.err.println("What is the name of the item that the attribute is attached to? ");
            System.err.print("(Give the name of the Entity or a Composite Attribute): ");
            attach = scan.next();
            erm.addAttrToEntity(erName, attach, aName, type);
        } else {
            erm.addAttrToRelationship(aName, type, erName);
        }//if-else
    }//addAttribute
    
    private static void addAttribute(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String EntOrRel, erName;
        System.err.print("Is this being added to an Entity(E) or Relationship(R)? ");
        EntOrRel = scan.next();
        if (EntOrRel.equalsIgnoreCase("ENTITY") || EntOrRel.equalsIgnoreCase("E")) {
            System.err.print("To which Entity are we adding an attribute? ");
            erName = scan.next();
            addAttribute(erm, erName, true);
        } else if (EntOrRel.equalsIgnoreCase("RELATIONSHIP") || EntOrRel.equalsIgnoreCase("R")) {
            System.err.print("To which Relationship are we adding an attribute? ");
            erName = scan.next();
            addAttribute(erm, erName, false);
        }//if-else 
        System.err.println();
    }//addAttribute
    
    private static void setKeyAttribute(ERModel erm, String entity) {
        Scanner scan = new Scanner(System.in);
        String attr;
        ArrayList<String> keys = new ArrayList<>();
        
        System.err.println("Add the names of the attributes one by one");
        System.err.println("Enter - to finish");
        attr = scan.next();
        while (!attr.equals("-")){
            keys.add(attr);
            attr = scan.next();
        }//do while
        erm.setKeyAttrOfEnt(entity, keys);
    }//setKeyAttribute
    
    private static void setKeyAttribute(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String entity;

        System.err.print("For which Entity are we setting the key attributes? ");
        entity = scan.next();
        
        setKeyAttribute(erm, entity);
    }//setKeyAttribute
    
    //saveModel: save current model information to file
    private static void saveModel(ERModel erm) throws IOException {
        String filename = erm.getName() + ".dat";
        System.err.println("Saving Entity-Relationship model to filename " + filename);
        FileWriter model = new FileWriter(filename);
        
        model.write(erm.toFile());
        
        model.close();
    }//saveModel
    
    private static void addToModel(ERModel erm) {
        Scanner scan = new Scanner(System.in);
        String action;
        do {
            //wait a sec before continuing
            //give user a chance to breath
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ERMtoRM.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println("What would you like to do with " + erm.getName() + "?");
            System.err.println("Add: Entity(E), Relationship(R), Attribute(A)");
            System.err.println(" Set Key Attribute(K)\n Convert(C)\n Display(D)\n Exit\\Quit");
            action = scan.next();
            
            if (action.equalsIgnoreCase("ENTITY") || action.equalsIgnoreCase("E")) {
                addEntity(erm);
            } else if (action.equalsIgnoreCase("RELATIONSHIP") || action.equalsIgnoreCase("R")) {
                addRelationship(erm);
            } else if (action.equalsIgnoreCase("ATTRIBUTE") || action.equalsIgnoreCase("A")) {
                addAttribute(erm);
            } else if (action.equalsIgnoreCase("SET KEY") || action.equalsIgnoreCase("K")) {
                setKeyAttribute(erm);    
            } else if (action.equalsIgnoreCase("CONVERT") || action.equalsIgnoreCase("C")) {
                Converter.ERtoRel(erm).display();
            } else if (action.equalsIgnoreCase("DISPLAY") || action.equalsIgnoreCase("D")) {
                erm.display();
            } else if (!action.equalsIgnoreCase("EXIT") && !action.equalsIgnoreCase("QUIT")) {
                System.err.println("Please choose one of the listed options:\n");
            }//if-else            
            
        } while (!action.equalsIgnoreCase("exit") && !action.equalsIgnoreCase("quit"));
        
        System.err.print("Would you like to save this model for future use? ");
        action = scan.next();
        if (action.equalsIgnoreCase("YES") || action.equalsIgnoreCase("Y")) {
            try {
                saveModel(erm);
            } catch (IOException ex) {
                Logger.getLogger(ERMtoRM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//if
        System.err.println("Now exiting system...");
    }//addToModel
    
    private static void newModel() {
        Scanner scan = new Scanner(System.in);
        String name;
        System.err.print("What is the name of the model? ");
        name = scan.next();
        ERModel erm = new ERModel(name);
        
        addToModel(erm);
    }//newModel
    
    //readAttribute: read an attribute from a line of input
    private static void readAttribute(ERModel erm, String erName, String line, boolean entity) {
        String attach, attrName, type;
        String attr_type[] = line.split(":");
        String attach_ary[] = attr_type[0].split(">");
        type = attr_type[1];
        if (attach_ary.length == 1) {
            attrName = attach_ary[0];
            attach = erName;
        } else {
            attrName = attach_ary[1];
            attach = attach_ary[0];
        }//if-else
        if (entity) {
            System.err.println("Adding Attribute " + attrName + " to Entity " + erName);
            erm.addAttrToEntity(erName, attach, attrName, type);
        } else {
            System.err.println("Adding Attribute " + attrName + " to Relationship " + erName);
            erm.addAttrToRelationship(attrName, type, erName);
        }//if-else
    }//readAttribute
    
    //readRegEntity: read a regular Entity from file
    private static void readRegEntity(ERModel erm, LineNumberReader reader) throws IOException {
        ArrayList<String> keys = new ArrayList<>();
        String name;
        String line = reader.readLine();
        name = line;
        System.err.println("Adding Entity " + name);
        erm.addRegEntity(name);
        while (!(line = reader.readLine()).equals("A>")) {
            //do nothing
        }//while
        while (!(line = reader.readLine()).equals("<A")) {
            readAttribute(erm, name, line, true);
        }//while
        while (!(line = reader.readLine()).equals("K>")) {
            //do nothing
        }//while
        while (!(line = reader.readLine()).equals("<K")) {
            keys.add(line);
        }//while
        erm.setKeyAttrOfEnt(name, keys);
    }//readRegEntity
    
    //readWeakEntity: read a weak Entity from file
    private static void readWeakEntity(ERModel erm, LineNumberReader reader) throws IOException {
        ArrayList<String> keys = new ArrayList<>();
        String name;
        String line = reader.readLine();
        name = line;
        System.err.println("Adding Entity " + name);
        erm.addWeakEntity(name, reader.readLine());
        while (!(line = reader.readLine()).equals("A>")) {
            //do nothing
        }//while
        while (!(line = reader.readLine()).equals("<A")) {
            readAttribute(erm, name, line, true);
        }//while
        while (!(line = reader.readLine()).equals("K>")) {
            //do nothing
        }//while
        while (!(line = reader.readLine()).equals("<K")) {
            keys.add(line);
        }//while
        erm.setKeyAttrOfEnt(name, keys);
    }//readWeakEntity
    
    //readRelationship: read a relationship from file
    private static void readRelationship(ERModel erm, LineNumberReader reader) throws IOException {
        String name, le, re, lp, rp, lc, rc;
        String line = reader.readLine();
        name = line;
        le = reader.readLine();
        lp = reader.readLine();
        lc = reader.readLine();
        re = reader.readLine();
        rp = reader.readLine();
        rc = reader.readLine();
        System.err.println("Adding Relationship " + name);
        erm.addRelationship(name, erm.getEntity(le), erm.getEntity(re), lp, rp, lc, rc);
        while (!(line = reader.readLine()).equals("A>")) {
            //do nothing
        }//while
        while (!(line = reader.readLine()).equals("<A")) {
            readAttribute(erm, name, line, false);
        }//while
    }//readRelationship
    
    private static void loadModel() {
        Scanner scan = new Scanner(System.in);
        String filename;
        System.err.print("Print name of file for loading: ");
        filename = scan.next();
        
        LineNumberReader reader = null;
        
        try {
            reader = new LineNumberReader(new FileReader(filename));
            String line;
            line = reader.readLine();
            ERModel erm = new ERModel(line);
            
            while ((line = reader.readLine()) != null) {
                if (line.equals("EOF")) {
                    break;
                } else if (line.equals("ER")) {
                    readRegEntity(erm, reader);
                } else if (line.equals("EW")) {
                    readWeakEntity(erm, reader);
                } else if (line.equals("R")) {
                    readRelationship(erm, reader);
                }//if-else
            }//while
            
            System.err.println();
            addToModel(erm);
        } catch (Exception ex){
         ex.printStackTrace();
        } finally {
         //Close the LineNumberReader
            try {
                if (reader != null){
                reader.close();
                }
            } catch (IOException ex){
                ex.printStackTrace();
            }//try-catch
        }//try-catch-finally
        
    }//loadModel
    
}
