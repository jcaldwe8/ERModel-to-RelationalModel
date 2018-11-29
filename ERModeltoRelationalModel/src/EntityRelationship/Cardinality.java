/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityRelationship;

/**
 *
 * @author caldw
 * Cardinality: can be of the form 1, n, m, or (min, max)
 */


public class Cardinality {
    
    protected enum CardVal {
        N, M, ONE, NULL;
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
        value = CardVal.NULL;
    }//constructor
    
    //changeValueToRange: switch from using a single value to a range
    public void changeValueToRange(int min, int max) {
        this.min = min;
        this.max = max;
        value = CardVal.NULL;
    }//changeValueToRange
    
    public void changeRangeToValue(CardVal value) {
        this.value = value;
        min = -1;
        max = -1;
    }//changeRangeToValue
    
    public void alterValue(CardVal value) {
        if (this.value == CardVal.NULL){
            System.out.println("Using Range, not Value - use changeRangeToValue");
            return;
        }//if            
        this.value = value;
    }//alterValue
    
    public void alterRange(int min, int max) {
        if (this.min == -1 || this.max == -1) {
            System.out.println("Using Value, not Range - use changeValueToRange");
            return;
        }//if
        if (min < 0 || max < 0) {
            System.out.println("Must enter non-negative numbers!!");
            return;
        }
        this.min = min;
        this.max = max;
    }//alterRange
    
    public boolean isOne() {
        if (value == CardVal.ONE)
            return true;
        return false;
    }//isOne
    
    public boolean isN() {
        if (value == CardVal.N || value == CardVal.M)
            return true;
        return false;
    }//isN
    
    public boolean isRange() {
        if (min > -1 && max > -1) 
            return true;
        return false;
    }//isRange
    
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
