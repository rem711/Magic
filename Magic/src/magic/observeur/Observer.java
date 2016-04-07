/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.observeur;

/**
 *
 * @author rem711
 */
public interface Observer {
    
    // passer les arguments inutiles à null
    public void update(int code, String s1, String s2, String s3, String s4, int x1, int y1, int x2, int y2);
    
}
