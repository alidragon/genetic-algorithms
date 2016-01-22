
package geneticalgorithms.triangles;

/**
 *
 * @author Ali Persing
 */
public class Polygon {
    
    private Point[] points;

    public Polygon(Point ... points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }
    
    

}
