 import java.awt.*;

/**
 * @author AlexK
 * @version 2020-01-18
 */
public class APoint<T> {



    /*
     **********************
     ***   attributes   ***
     **********************
     */

private int  x;
private int  y;
private T userData;

private Canvas canvas;
    /*
     *********************
     ***  constructors ***
     *********************
     */

    public APoint(int x, int y,T userData, Canvas canvas){
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.userData = userData;
    }
    public APoint(int x, int y, Canvas canvas){
        this.canvas = canvas;
        this.x = x;
        this.y = y;
    }

    /*
     ***********************
     ****       getter   ***
     ***********************
     */
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public T getUserData(){return this.userData;}
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
        //
    }
    public double distanceTo(APoint p){
        return Math.sqrt((x-p.x)^2+(y-p.y)^2);
    }
    public void show(){
        this.canvas.setPenColor(Color.BLUE);
        this.canvas.filledRectangle(this.x,this.y,1,1);
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
