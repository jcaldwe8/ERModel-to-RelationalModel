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
 * FunctDep: a functional dependency
 */
public class FunctDep {
    
    private ArrayList<String> lhs;
    private ArrayList<String> rhs;
    
    public FunctDep() {
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
    }//constructor
    
    public void addLHS(String... F) {
        for (String fd : F) {
            lhs.add(fd);
        }//for-each
    }//addLHS
    
    public void addRHS(String... F) {
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
    
    
    
}
