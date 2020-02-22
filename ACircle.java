  
import java.awt.*;

/**
 * @author AlexK
 * @version 2020-01-18
 */
public class ACircle {



    /*
     **********************
     ***   attributes   ***
     **********************
     */

    private int x;
    private int y;
    private int r;
    
    private Canvas canvas;
    /*
     *********************
     ***  constructors ***
     *********************
     */

    public ACircle(int x,int y,int radius,Canvas canvas){
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.r = radius;
    }

    /*
     ***********************
     ****       getter   ***
     ***********************
     */
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getR(){
        return this.r;
    }
    
    public Canvas getCanvas(){
        return this.canvas;
    }

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
        
    }
    
    public boolean contains(APoint point){
        return(point.getX() >= this.x - this.r &&
               point.getX() <= this.x + this.r &&
               point.getY() >= this.y - this.r &&
               point.getY() <= this.y + this.r);
    }
    
    public boolean intersects(ACircle range){
        return !(range.getX() - range.getR() > this.x + this.r ||
                 range.getX() + this.r < this.x - this.r ||
                 range.getY() - range.getR() > this.y + this.r ||
                 range.getY() + this.r < this.y - this.r);
    }
    
    public void show(){
        this.canvas.setPenColor(Color.RED);
        this.canvas.circle(x, y, r);
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
