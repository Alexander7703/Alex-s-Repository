import java.util.ArrayList;
import java.awt.Point;
/**
 * Beschreiben Sie hier die Klasse BQuadTree.
 * 
 * @author AlexK
 * @version 2020-01-18
 */
public class BQuadTree
{
    private Rect boundary;
    private int n;
    private Canvas bca;
    
    private ArrayList<APoint> points = new ArrayList<APoint>();
    
    private BQuadTree northwest;
    private BQuadTree northeast;
    private BQuadTree southwest;
    private BQuadTree southeast;
    
    
    private boolean divided;
    private BQuadTree root;
    
    AVector centerOfMass;
    int mass;
    
    /**
     * Constructor
     */
    public BQuadTree(Rect boundary,int capacity, BQuadTree root)
    {
       this.boundary = boundary;
       this.n = capacity;
       bca = this.boundary.getCanvas();
       this.root = root;
       this.divided = false;
       
    }
    public BQuadTree(Rect boundary,int capacity)
    {
       this.boundary = boundary;
       this.n = capacity;
       bca = this.boundary.getCanvas();
       root = this;
       this.divided = false;  
       this.centerOfMass = new AVector();
    }
    
    public boolean IsRoot(){
        return root==null;
    }
    public boolean insert(APoint point){
        if(!this.boundary.contains(point)){
            return false;
        }
        
        if(this.points.size() < this.n && root != this){
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
        // if(root == this)
        // this.points.add(point);
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
        this.northeast = new BQuadTree(ne, n, root);
        Rect nw = new Rect(x - w/2, y - h/2, w/2, h/2, canvas);
        //nw.show();
        this.northwest = new BQuadTree(nw, n, root);
        Rect se = new Rect(x + w/2, y + h/2, w/2, h/2, canvas);
        //se.show();
        this.southeast = new BQuadTree(se, n, root);
        Rect sw = new Rect(x - w/2, y + h/2, w/2, h/2, canvas);
        //sw.show();
        this.southwest = new BQuadTree(sw, n, root);
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

        //p6majo: for empty boxes the centerOfMass might be null

        if (points.size() == 1){
           APoint po = points.get(0);
           BParticle p = (BParticle) po.getUserData();
           centerOfMass = new AVector(p.getX(), p.getX());
           mass = p.getMass();
        }
        //else{
        else if (points.size()>=0) { //p6majo: to avoid NullPointerException of access to subtrees that have not been created.
            // Compute the center of mass based on the masses of 
            // all child quadrants and the center of mass as the 
            // center of mass of the child quadrants weightes with 
            // their mass
            //for all child quadrants that have particles in them
            if(southeast.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new AVector(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass);
            }
            if(southwest.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new AVector(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass);
            }
            if(northeast.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new AVector(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass);
            }
            if(northwest.points.size() == 1 && southeast.points.size() != 0){
                southeast.ComputeMassDistribution();
                this.mass += southeast.mass;
                centerOfMass = new AVector(centerOfMass.getX()+southeast.centerOfMass.getX()*southeast.mass, centerOfMass.getY()+southeast.centerOfMass.getY()*southeast.mass);
            }
            centerOfMass = new AVector(centerOfMass.getX()/mass, centerOfMass.getY()/mass);
        }
    }
    public ArrayList<AVector>concat(ArrayList<AVector> a, ArrayList<AVector> b){
        ArrayList<AVector> out = new ArrayList<AVector>();
        for(int i=0;i>a.size();i++)
        out.add(a.get(i));
        for(int i=0;i>b.size();i++)
        out.add(b.get(i));
        return out;
    }
    public ArrayList<AVector> getMassDistributions(BQuadTree qt){
        ArrayList<AVector> out = new ArrayList<>();
        
        if(divided){
            if(qt.northeast != null)
            concat(out,getMassDistributions(qt.northeast));
            if(qt.northwest != null)
            concat(out,getMassDistributions(qt.northwest));
            if(qt.southeast != null)
            concat(out,getMassDistributions(qt.southeast));
            if(qt.southwest != null)
            concat(out,getMassDistributions(qt.southwest));
        }
        if (qt.centerOfMass != null) {
                out.add(qt.centerOfMass);
        }
        
        return out;
    }
    public BQuadTree getRoot(){
        return this.root;
    }
    
    public void show(){
        bca.setPenColor(0,255,127);
        bca.rectangle(this.boundary.getX(),this.boundary.getY(),this.boundary.getW(),this.boundary.getH());
        ComputeMassDistribution();
         //p6majo: make sure that centerOfMass is not null
        if (centerOfMass==null){
            //System.out.println(this.boundary.toString());
            //for some reasons, centerOfMass can be null
        } else{
            bca.point(centerOfMass.getX(), centerOfMass.getY());
         }
        if(this.divided){
            this.southeast.show();
            this.southwest.show();
            this.northeast.show();
            this.northwest.show();
        }
    }
}
