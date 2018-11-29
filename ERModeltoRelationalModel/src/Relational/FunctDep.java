/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Relational;

import java.util.ArrayList;

/**
 *
 * @author caldw
 * FunctDep: a functional dependency
 */
public class FunctDep {
    
    private ArrayList<String> lhs;
    private ArrayList<String> rhs;
    
    public FunctDep() {
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
    }//constructor
    
    public void addLHS(ArrayList<String> F) {
        for (String fd : F) {
            lhs.add(fd);
        }//for-each
    }//addLHS
    
    public void addRHS(ArrayList<String> F) {
        for (String fd : F) {
            rhs.add(fd);
        }//for-each
    }//addRHS

    public ArrayList<String> getLhs() {
        return lhs;
    }

    public ArrayList<String> getRhs() {
        return rhs;
    }
    
    @Override
    public String toString() {
        String ret = "";
        ret += itemize(lhs);
        ret += " -> ";
        ret += itemize(rhs);
        return ret;
    }
    
    public String itemize(ArrayList<String> list) {
        String item = "";
        boolean first = true;
        for (String a : list) {
            if (first) {
                item += a;
                first = false;
            } else {
                item += ", " + a;
            }//if-else
        }//for-each
        return item;
    } 
    
}
