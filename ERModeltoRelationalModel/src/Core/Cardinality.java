/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 *
 * @author caldw
 * Cardinality: can be of the form 1, n, m, or (min, max)
 */


public class Cardinality {
    
    enum CardVal {
        N, M, ONE;
    }
    
    private CardVal value;
    private int min, max;
    
    public Cardinality(CardVal value) {
        this.value = value;
        min = -1;
        max = -1;
    }//constructor
    
    public Cardinality(int min, int max) {
        this.min = min;
        this.max = max;
    }//constructor
    
    @Override
    public String toString() {
        String ret;
        if (min == -1 || max == -1) {
            ret = value.toString();
        } else {
            ret = "(" + min + ", " + max + ")";
        }
        return ret;
    }//toString
    
}
