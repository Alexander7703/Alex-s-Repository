    import java.awt.*;
    import java.util.*;
    
    /**
     * @author AlexK
     * @version 2020-01-18
     */
    public class Sketch extends CodingTrainGui {
    
    
    
        /*
         **********************
         ***   attributes   ***
         **********************
         */
    
        public Random rnd;
        
        private static int width;
        private static int height;
        
        private QuadTree qt;
        
        private Rect boundary;
        private Rect range; 
        
        private ArrayList<APoint> punkte;
        private ArrayList<AParticle> partikel;
        private ArrayList<APoint> fp;
        /*
         *********************
         ***  constructors ***
         *********************
         */
    
        /**
         * Create a CodingTrainGui that consists of a Canvas, where graphical objects can be drawn.
         * The parameters define the width and the height of the canvas and the frameRate determines, how often
         * the method draw() is called within a second. A framerate of 0 causes the method draw() to be called only once.
         *
         * @param width     with of the canvas
         * @param height    height of the canvas
         * @param frameRate frequency with which the draw() method is called.
         */
        public Sketch(int width, int height, int frameRate) {
            super(width, height, frameRate);
            // this.width = width;
            // this.height = height;
           
            
        }
    
        /*
         ***********************
         ****       getter   ***
         ***********************
         */
    
    
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
        /**
         * Gibt einen zufälligen APoint im berreich des Fensters wieder.
         */
        public APoint newRndPoint(){
            rnd = new Random();
            //System.out.println(this.height+" "+this.width);
            int x = rnd.nextInt(this.height);
            int y = rnd.nextInt(this.width);
            //System.out.println(x+" "+y);
            APoint out;
            out = new APoint(x,y,null,this.canvas);
            return out;
        }
        /**
         * Gibt einen zufälligen Partikel(Particle) im berreich des Fensters wieder.
         */
        public AParticle newRndParticle(){
            rnd = new Random();
            //System.out.println(this.height+" "+this.width);
            int x = rnd.nextInt(this.width);
            int y = rnd.nextInt(this.height);
            //System.out.println(x+" "+y);
            AParticle out;
            out = new AParticle(x,y,2,this.canvas);
            return out;
        }
        
        /*
         ******************************
         ****     private methods   ***
         ******************************
         */
        private void ssearch(AParticle p){
            for(AParticle other : partikel)
            if(p != other && p.intersects(other))
            p.setHighlight(true);
        }
        private void optisearch(AParticle p){
           ACircle range = new ACircle(p.getX(),p.getY(),p.getR()*2,this.canvas);
           punkte = qt.circularquery(range);
           for(APoint point : punkte){
               AParticle other = (AParticle) point.getUserData();
               if(p != other && p.intersects(other))
               p.setHighlight(true);
           }
        }
        
    
        /*
         ******************************
         ****     overrides         ***
         ******************************
         */
    
        @Override
        public void setup() {
           partikel = new ArrayList<>();
           punkte = new ArrayList<>();    
           //this.width = 400; 
           //this.height = 400;
            
           canvas.setBackground(Color.BLACK);
           
            
           for(int i = 0;i<10000;i++){
               AParticle p = newRndParticle();
               this.partikel.add(p);
               //qt.insert(p.toAPoint());
           }
          
           // this.range = new Rect(150,150,107,60, this.canvas);
           // fp = qt.query(range);
           // for(int i=0;i<fp.size();i++){
               // System.out.println(fp.get(i).getX() + " "+ fp.get(i).getY());
            // }
            
            ///TODO
            // (X) unten bei draw => qt.show(); Mach mal bitte in QT klasse zukunft
        }
    
        @Override
        public void draw() {
            this.canvas.clear(new Color(51,51,51));
            
            this.boundary = new Rect(this.width/2,this.height/2,this.width/2,this.height/2,this.canvas);
            this.qt = new QuadTree(boundary, 4);
           
            this.qt.show();
            this.boundary.show();
            //this.range.show();
            
            for(int i = 0;i<partikel.size();i++){
                 qt.insert(this.partikel.get(i).toAPoint());
                
                 this.partikel.get(i).update();
                 this.partikel.get(i).show();
                 this.partikel.get(i).setHighlight(false);
            }
            for(AParticle p : partikel){
                 optisearch(p);
            }
        
        
            //this.snake.update();
            //this.snake.show();
         }

    
   
    @Override
    public void keyAction(char keyChar) {
        
    }


    /*
     ******************************
     ****     main              ***
     ******************************
     */


    public static void main(String[] args) {
        //starte hier die Klasse mit einer Bildbreite, Bildhoehe und Bildrate (0 fuer keine Wiederholung, 60 fuer 60 Bilder pro Sekunde)
        height = 1000; 
        width = 850;
        new Sketch(width,height,244);
    }

}
