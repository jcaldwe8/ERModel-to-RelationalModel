/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Relational;

/**
 *
 * @author caldw
 * ForeignKey: an attribute may reference a `foreign' relation and attribute, this is the foreign key
 *             the relation and attribute are represented by strings, making sure they are sensible is
 *             ensured at the higher level
 */
public class ForeignKey {
    private String relRef; //attribute in relation
    private String attrRef; //attribute referenced by attr
    
    public ForeignKey(String relRef, String attrRef) {
        this.relRef = relRef;
        this.attrRef = attrRef;
    }//Constructor
    
    public String getRelRef() {
        return relRef;
    }
    
    public String getAttrRef() {
        return attrRef;
    }
    
}
