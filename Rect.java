  
import java.awt.*;

/**
 * @author AlexK
 * @version 2020-01-18
 */
public class Rect {



    /*
     **********************
     ***   attributes   ***
     **********************
     */

    private int x;
    private int y;
    private int w;
    private int h;
    
    private Canvas canvas;
    /*
     *********************
     ***  constructors ***
     *********************
     */

    public Rect(int x,int y,int width,int height,Canvas canvas){
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
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
    
    public int getW(){
        return this.w;
    }
    
    public int getH(){
        return this.h;
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
        return(point.getX() >= this.x - this.w &&
               point.getX() <= this.x + this.w &&
               point.getY() >= this.y - this.h &&
               point.getY() <= this.y + this.h);
    }
    
    public boolean intersects(Rect range){
        return !(range.getX() - range.getW() > this.x + this.w ||
                 range.getX() + this.w < this.x - this.w ||
                 range.getY() - range.getH() > this.y + this.h ||
                 range.getY() + this.h < this.y - this.h);
    }
    
    public boolean cIntersects(ACircle range){
        return !(range.getX() - range.getR() > this.x + this.w ||
                 range.getX() + this.w < this.x - this.w ||
                 range.getY() - range.getR() > this.y + this.h ||
                 range.getY() + this.h < this.y - this.h);
    }
    
    public void show(){
        this.canvas.setPenColor(Color.RED);
        this.canvas.rectangle(this.x,this.y,this.w,this.h);
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
