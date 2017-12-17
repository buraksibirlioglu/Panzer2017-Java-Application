/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

/**
 *
 * @author Burak
 */
public class Score {
    private String name;
    private int score;
    
    public Score( String nm, int scr)
    {
        name=nm;
        score=scr;
    }
    public String getName()
    {
        return name;
    }
    public int getScore()
    {
        return score;
    }
}
