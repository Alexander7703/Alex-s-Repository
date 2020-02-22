 import java.awt.*;
 import java.util.*;
 import java.math.BigDecimal;
/**
 * @author AlexK
 * @version 2020-01-18
 */
public class BParticle {



    /*
     **********************
     ***   attributes   ***
     **********************
     */
    Canvas canvas;
    Random rnd = new Random();
    
    AVector pos;
    AVector vel;
    AVector acc;
    
    int mass = 1;
    /*
     *********************
     ***  constructors ***
     *********************
     */

    public BParticle(int x, int y, Canvas canvas){
        this.canvas = canvas;
        
        pos = new AVector(x,y);
        vel = new AVector(rnd.nextDouble(),rnd.nextDouble());
        vel.rotate(rnd.nextFloat());
        acc = new AVector(0,0);
    }

    /*
     ***********************
     ****       getter   ***
     ***********************
     */
    public double getX(){return this.pos.getX();}
    public double getY(){return this.pos.getY();}    
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
    public void attracted(AVector target){
        AVector force = new AVector(target,this.pos);
        double dsquared = force.magSq();
        dsquared = limit(dsquared, 25, 500);
        double G = 1;//6.67408*10E-1;
        double strength;
        strength = G / dsquared;
        force.setMag(strength);
        acc = force;
    }
    public double limit(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
    public void update(){
        this.vel.add(this.acc);
        this.pos.add(this.vel);
    }
    public void show(){
        this.canvas.setPenColor(Color.RED);
        this.canvas.filledCircle(getX(),getY(), 2);
    }
    public void setPosition(int x, int y){
        this.pos = new AVector(x,y);
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
