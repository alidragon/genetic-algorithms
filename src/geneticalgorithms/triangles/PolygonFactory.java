
package geneticalgorithms.triangles;

import geneticalgorithms.Genome;
import geneticalgorithms.IGenomeFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Ali Persing
 */
public class PolygonFactory implements IGenomeFactory {
    private int maxNumberPoints;
    private int maxPointLocation;
    
    public PolygonFactory(int maxNumberPoints, int maxPointLocation) {
        this.maxNumberPoints = maxNumberPoints;
        this.maxPointLocation = maxPointLocation;
    }

    @Override
    public Genome makeGenome() {
        return new PolygonGenome(maxNumberPoints, maxPointLocation);
    }

    @Override
    public Genome makeGenome(Genome g) {
        if(g.getClass() == PolygonGenome.class) {
            return new PolygonGenome((Polygon)g.getGenes(), maxNumberPoints, maxPointLocation);
        } 
        throw new NotImplementedException();
    }

}
