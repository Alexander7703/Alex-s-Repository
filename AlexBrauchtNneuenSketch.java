    import java.awt.*;
    import java.util.*;
    
       /**
       * @author AlexK
       * @version 2020-01-18
       */
        public class AlexBrauchtNneuenSketch extends CodingTrainGui {
    
    
    
        /*
         **********************
         ***   attributes   ***
         **********************
         */
    
        public Random rnd;
        
        private static int width;
        private static int height;
        
        double ie = 0;
        
        BQuadTree qt;
        private Rect boundary;
        
        private AVector attractor;
        private BParticle particle;
        private ArrayList<APoint> punkte;
        AVector zeiter = new AVector(0,0);
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
        public AlexBrauchtNneuenSketch(int width, int height, int frameRate) {
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
        
        
        /*
         ******************************
         ****     private methods   ***
         ******************************
         */
        private void zeiters(){
            zeiter.set(ie,0);
            if(zeiter.getX()>400){
                System.out.println("e"); 
                zeiter.set(0, 0);
                ie = 0;
            }
            this.canvas.point(zeiter.getX(), zeiter.getY());
        }
        private APoint newRndParticle(){
            rnd = new Random();
            //System.out.println(this.height+" "+this.width);
            int x = rnd.nextInt(this.width);
            int y = rnd.nextInt(this.height);
            //System.out.println(x+" "+y);
            APoint out;
            out = new APoint(x,y,new BParticle(x,y,this.canvas),this.canvas);
            return out;
        }
        
    
        /*
         ******************************
         ****     overrides         ***
         ******************************
         */
    
        @Override
        public void setup() {
           punkte = new ArrayList<>();    
           
           this.boundary = new Rect(this.width/2,this.height/2,this.width/2,this.height/2,this.canvas);
           this.qt = new BQuadTree(boundary, 1);
           
           canvas.setBackground(Color.BLACK);
            
           // for(int i = 0;i<2;i++){
               // APoint p = newRndParticle();
               // this.punkte.add(p);
               // qt.insert(p);
           // }
           APoint p1 = new APoint(300, 300,new BParticle(300,300,this.canvas),this.canvas);
           APoint p2 = new APoint(100, 100,new BParticle(100,100,this.canvas),this.canvas);
           APoint p3 = new APoint(100, 300,new BParticle(100,300,this.canvas),this.canvas);
           APoint p4 = new APoint(300, 100,new BParticle(300,100,this.canvas),this.canvas);
           qt.insert(p1);
           qt.insert(p2);
           qt.insert(p3);
           qt.insert(p4);
           punkte.add(p1);
           punkte.add(p2);
           punkte.add(p3);
           punkte.add(p4);
           
           
           
           //this.canvas.clear(new Color(51,51,51));
        }
    
        @Override
        public void draw() {
            this.canvas.clear(new Color(51,51,51));
            ie+=4E-2;
            zeiters();

            this.qt.ComputeMassDistribution();
            ArrayList<AVector> attractors = qt.getMassDistributions(this.qt);
            
            this.canvas.setPenColor(Color.CYAN);
            //this.canvas.point(attractor.getX(), attractor.getY());
            //this.particle.attracted(attractor);
            //this.particle.update();
            //this.particle.show();
            for(int i = 0;i<punkte.size();i++){
                 //qt.insert(this.punkte.get(i).toAPoint());
                 BParticle p = (BParticle) this.punkte.get(i).getUserData();
                 //this.partikel.get(i).update();
                 
                 for(AVector a :attractors){
                     p.attracted(a);
                 }
                 p.update();
                 p.show();
            }
            //this.qt.ComputeMassDistribution();
            this.qt.show();
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
            height = 400; 
            width = 400;
            new AlexBrauchtNneuenSketch(width,height,244);
        }

}
