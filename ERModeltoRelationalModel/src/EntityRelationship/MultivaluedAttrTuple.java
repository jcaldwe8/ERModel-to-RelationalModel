/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityRelationship;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author caldw
 */
public class MultivaluedAttrTuple {
 
    private ArrayList<EAttribute> mva;
    private Set<String> keyAttr;
    private String entName;
    
    public MultivaluedAttrTuple(ArrayList<EAttribute> mva, Set<String> keyAttr, String entName) {
        this.mva = mva;
        this.keyAttr = keyAttr;
        this.entName = entName;
    }//constructor

    public ArrayList<EAttribute> getMva() {
        return mva;
    }

    public Set<String> getKeyAttr() {
        return keyAttr;
    }

    public String getEntName() {
        return entName;
    }
    
}
