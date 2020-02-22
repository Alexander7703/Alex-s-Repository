import java.util.ArrayList;
import java.awt.Point;
/**
 * Beschreiben Sie hier die Klasse QuadTree.
 * 
 * @author AlexK
 * @version 2020-01-18
 */
public class QuadTree
{
    private Rect boundary;
    private int n;
    private Canvas bca;
    
    private ArrayList<APoint> points = new ArrayList<APoint>();
    
    private QuadTree northwest;
    private QuadTree northeast;
    private QuadTree southwest;
    private QuadTree southeast;
    
    private boolean divided;
    private QuadTree root;
    
    APoint centerOfMass;
    int mass;
    
    /**
     * Constructor
     */
    public QuadTree(Rect boundary,int capacity, QuadTree root)
    {
       this.boundary = boundary;
       this.n = capacity;
       bca = this.boundary.getCanvas();
       this.root = root;
       this.divided = false;
    }
    public QuadTree(Rect boundary,int capacity)
    {
       this.boundary = boundary;
       this.n = capacity;
       bca = this.boundary.getCanvas();
       root = null;
       this.divided = false;
    }
    
    public boolean IsRoot(){
        return root==null;
    }
    public boolean insert(APoint point){
        if(!this.boundary.contains(point)){
            return false;
        }
        
        if(this.points.size() < this.n){
                this.points.add(point);
        }else{
                if(!this.divided){
                    this.subdivide();
                } 
                if(this.northeast.insert(point))
                return true;
                else if(this.northwest.insert(point))
                return true;
                else if(this.southeast.insert(point))
                return true;
                else if(this.southwest.insert(point))
                return true;
                
        }
        return false;
    }
    private void subdivide(){
        int x = this.boundary.getX();
        int y = this.boundary.getY();
        int w = this.boundary.getW();
        int h = this.boundary.getH();
        Canvas canvas = this.boundary.getCanvas();;
        
        Rect ne = new Rect(x + w/2, y - h/2, w/2, h/2, canvas);
        //ne.show();
        this.northeast = new QuadTree(ne, n, this);
        Rect nw = new Rect(x - w/2, y - h/2, w/2, h/2, canvas);
        //nw.show();
        this.northwest = new QuadTree(nw, n, this);
        Rect se = new Rect(x + w/2, y + h/2, w/2, h/2, canvas);
        //se.show();
        this.southeast = new QuadTree(se, n, this);
        Rect sw = new Rect(x - w/2, y + h/2, w/2, h/2, canvas);
        //sw.show();
        this.southwest = new QuadTree(sw, n, this);
                            this.divided = true;

    }
    public ArrayList<APoint> query(Rect range){
        ArrayList<APoint> found = new ArrayList<APoint>();
        
        if(!this.boundary.intersects(range)){
            return found;
        }else{
            for(int i = 0; i < this.points.size(); i++){
                APoint p = points.get(i);
                if(range.contains(p)){
                    found.add(p);
                }
            }
            if(this.divided){
                found.addAll(this.northwest.query(range));
                found.addAll(this.northeast.query(range));
                found.addAll(this.southwest.query(range));
                found.addAll(this.southeast.query(range));
            }
            return found;
        }
    }
    public ArrayList<APoint> circularquery(ACircle range){
        ArrayList<APoint> found = new ArrayList<APoint>();
        
        if(!this.boundary.cIntersects(range)){
            return found;
        }else{
            for(int i = 0; i < this.points.size(); i++){
                APoint p = points.get(i);
                if(range.contains(p)){
                    found.add(p);
                }
            }
            if(this.divided){
                found.addAll(this.northwest.circularquery(range));
                found.addAll(this.northeast.circularquery(range));
                found.addAll(this.southwest.circularquery(range));
                found.addAll(this.southeast.circularquery(range));
            }
            return found;
        }
    }
    public void ComputeMassDistribution()
    {
        if (points.size() == 1){
           APoint po = points.get(0);
           AParticle p = (AParticle) po.getUserData();
           centerOfMass = new APoint(p.getX(), p.getX(), p.getCanvas());
           mass = p.getMass();
        }
        else{
            // Compute the center of mass based on the masses of 
            // all child quadrants and the center of mass as the 
            // center of mass of the child quadrants weightes with 
            // their mass
            //for all child quadrants that have particles in them
            if(southeast.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new APoint(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass, bca);
            }
            if(southwest.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new APoint(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass, bca);
            }
            if(northeast.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new APoint(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass, bca);
            }
            if(northwest.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new APoint(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass, bca);
            }
            centerOfMass = new APoint(centerOfMass.getX()/mass, centerOfMass.getY()/mass, bca);
        }
    }
    public QuadTree getRoot(){
        return this.root;
    }
    public void show(){
        bca.setPenColor(0,255,127);
        bca.rectangle(this.boundary.getX(),this.boundary.getY(),this.boundary.getW(),this.boundary.getH());
        if(this.divided){
            this.southeast.show();
            this.southwest.show();
            this.northeast.show();
            this.northwest.show();
        }
    }
}
