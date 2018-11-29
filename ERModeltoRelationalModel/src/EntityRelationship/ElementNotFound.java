/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityRelationship;

/**
 *
 * @author caldw
 */
public class ElementNotFound extends Exception {

    /**
     * Creates a new instance of <code>ElementNotFound</code> without detail
     * message.
     */
    public ElementNotFound() {
    }

    /**
     * Constructs an instance of <code>ElementNotFound</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ElementNotFound(String msg) {
        super(msg);
    }
}
