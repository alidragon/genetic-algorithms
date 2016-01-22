
package geneticalgorithms.triangles;

import geneticalgorithms.Genome;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ali Persing
 */
public class PolygonGenome extends Genome<Polygon> {
    
    private int maxPointSize;
    private int maxPointLocation;
    
    public PolygonGenome(int maxPointSize, int maxPointLocation) {
        int size = (int)(Math.random() * (maxPointSize - 3) + 3);
        Point[] points = new Point[size];
        for(int i = 0; i < size; i++) {
            //generate new point
            points[i] = new Point((int)(Math.random() * maxPointLocation), (int)(Math.random() * maxPointLocation));
        }
        setGenes(new Polygon(points));
        this.maxPointLocation = maxPointLocation;
        this.maxPointSize = maxPointSize;
    }

    public PolygonGenome(Polygon polygon, int maxPointSize, int maxPointLocation) {
        setGenes(polygon);
        this.maxPointLocation = maxPointLocation;
        this.maxPointSize = maxPointSize;
    }

    @Override
    public void mutate(double chance) {
        if(Math.random() < chance / 2) {
            //change number of points
            int currentSize = getGenes().getPoints().length;
            int newSize = (int)(Math.random() * (maxPointSize - 3) + 3);
            
            Point[] newPoints = new Point[newSize];
            if(newSize > currentSize) {
                for(int i = 0; i < currentSize; i++) {
                    newPoints[i] = getGenes().getPoints()[i];
                }
                for(int i = currentSize; i < newSize; i++) {
                    //generate new point
                    newPoints[i] = new Point((int)(Math.random() * maxPointLocation), (int)(Math.random() * maxPointLocation));
                }
            } else {
                for(int i = 0; i < newSize; i++) {
                    newPoints[i] = getGenes().getPoints()[i];
                }
            }
            getGenes().setPoints(newPoints);
        }
        if(Math.random() < chance) {
            //change x of a random point
            getGenes().getPoints()[(int)(Math.random() * getGenes().getPoints().length)].setX((int)(Math.random() * maxPointLocation));
        }
        
        if(Math.random() < chance) {
            //change y of a random point
            getGenes().getPoints()[(int)(Math.random() * getGenes().getPoints().length)].setY((int)(Math.random() * maxPointLocation));
            
        }
        
    }

    @Override
    public ArrayList<Genome> crossOver(Genome<Polygon> other, int pos) {
        return crossOver(other);
    }

    @Override
    public ArrayList<Genome> crossOver(Genome<Polygon> other) {
        PolygonGenome one = generateNewGenome(this.getGenes(), other.getGenes());
        PolygonGenome two = generateNewGenome(this.getGenes(), other.getGenes());
        ArrayList<Genome> toReturn = new ArrayList<Genome>();
        toReturn.add(one);
        toReturn.add(two);
        return toReturn;
    }
    
    private PolygonGenome generateNewGenome(Polygon one, Polygon two) {
        int max = one.getPoints().length > two.getPoints().length? one.getPoints().length : two.getPoints().length;
        int min = one.getPoints().length <= two.getPoints().length? one.getPoints().length : two.getPoints().length;
        int size = (int) (Math.random() * (max - min) + min);
        
        Point[] p = new Point[size];
        for(int i = 0; i < size; i++) {
            Point[] toUse;
            if(Math.random() < .5) {
                toUse = one.getPoints();
            } else {
                toUse = two.getPoints();
            }
            
            p[i] = new Point(toUse[(int) (Math.random() * toUse.length)]);
        }
        
        return new PolygonGenome(new Polygon(p), this.maxPointLocation, this.maxPointSize);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Polygon [");
        for(int i = 0; i < getGenes().getPoints().length; i++) {
            sb.append(getGenes().getPoints()[i].toString());
        }
        sb.append("]");
        return sb.toString();
    }

}
