 import java.awt.*;

/**
 * @author AlexK
 * @version 2020-01-18
 */
public class AVector {



    /*
     **********************
     ***   attributes   ***
     **********************
     */

    private double x;
    private double y;
    
    /*
     *********************
     ***  constructors ***
     *********************
     */

    public AVector(double x, double y){
        this.x = x;
        this.y = y;
    }
    public AVector(AVector v){
        this.x =v.x;
        this.y =v.x;
    }
    public AVector(){
        this.x =0;
        this.y =0;
    }
    public AVector (AVector v, AVector w){
        this.x = v.x-w.x;
        this.y = v.y-w.y;
    }

    /*
     ***********************
     ****       getter   ***
     ***********************
     */
    public double getX(){return this.x;}
    public double getY(){return this.y;}
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

    public void add(AVector v){
        this.x += v.x;
        this.y += v.y;
    }
    public void sub(AVector v){
        this.x -= v.x;
        this.y -= v.y;
    }
    private void normalize(){
        double a = Math.sqrt(x*x+y*y);
        this.x /= a;
        this.y /= a;
    }
    private void mult(double n){
        this.x *= n;
        this.y *= n;
    }
    public void setMag(double mag){
        normalize();
        mult(mag);
    }
    public double mag(){
        return Math.sqrt(x*x + y*y);
    }
    public double magSq(){
        return (x*x + y*y);
    }
    public void rotate(float theta){
        this.x = (Math.cos(theta)-Math.sin(theta))*this.x;
        this.y = (Math.cos(theta)-Math.sin(theta))*this.y;
    }
    public void set(double x,double y){
        this.x = x;
        this.y = y;
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
