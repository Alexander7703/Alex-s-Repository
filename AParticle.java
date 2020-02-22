 import java.awt.*;
 import java.util.*;

/**
 * @author AlexK
 * @version 2020-01-18
 */
public class AParticle {



    /*
     **********************
     ***   attributes   ***
     **********************
     */
    
    private int  x;
    private int  y;
    private int  r = 2;
    private int mass;
    
    private boolean highlight;
    
    private Random rnd;
    
    private Canvas canvas;
    /*
     *********************
     ***  constructors ***
     *********************
     */

    public AParticle(int x, int y,int mass, Canvas canvas){
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.mass = mass;
        rnd = new Random();
        
        highlight = false;
    }

    /*
     ***********************
     ****       getter   ***
     ***********************
     */
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getR(){return this.r;}
    public int getMass(){return this.mass;}
    public Canvas getCanvas(){return this.canvas;}
    /*
     ************************
     ****       setter    ***
     ************************
     */

    /*
     *****************************
     ***     public methods    ***
     *****************************
     */

    public void update(){
        this.x += (int)(Math.random() * ((1 - -1) + 1)) + -1;
        this.y += (int)(Math.random() * ((1 - -1) + 1)) + -1;
    }
    
    public boolean intersects(AParticle other){
        double d = Math.sqrt((this.x-other.getX())*(this.x-other.getX())+(this.y-other.getY())*(this.y-other.getY())); //Math.sqrt((this.x-other.getX())^2+(this.y-other.getY())^2);
        return(d<this.r+other.getR());
    }
    
    public void setHighlight(boolean value){
        this.highlight = value;
    }
    
    public APoint toAPoint(){
        return new APoint(this.x,this.y,this,this.canvas);
    }

    public void show(){
        if(highlight)
        this.canvas.setPenColor(Color.CYAN);
        else
        this.canvas.setPenColor(Color.BLUE);
         this.canvas.filledCircle(x, y, r);
    }
    /*
     ******************************
     ****     private methods   ***
     ******************************
     */


    /*
     ******************************
     ****     overrides         ***
     ******************************
     */

    /*
     ******************************
     ****     toString()        ***
     ******************************
     */

    @Override
    public String toString() {
        return super.toString();
    }

}
