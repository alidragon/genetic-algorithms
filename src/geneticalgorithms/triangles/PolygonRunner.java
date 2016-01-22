
package geneticalgorithms.triangles;

import geneticalgorithms.Callback;
import geneticalgorithms.Evolvable;
import geneticalgorithms.GeneticAlgorithm;
import geneticalgorithms.Genome;

/**
 *
 * @author Ali Persing
 */
public class PolygonRunner {

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 15, .1, 50000, .10, new PolygonFactory(10, 50), GeneticAlgorithm.SelectionMethod.Tournament, 10, new Callback() {

            @Override
            public void generation(GeneticAlgorithm ga) {
                System.out.println("Best fit: " + ga.maxFitness());
            }
            
        });
        ga.run(new Evolvable() {
            private static final int desiredSides = 3;
            
            @Override
            public double determineFitness(Genome g) {
                if(g.getClass() != PolygonGenome.class) {
                    throw new UnsupportedOperationException("Wrong type");
                }
                Point[] points = ((Polygon)g.getGenes()).getPoints();
                
                double fitness = Math.abs(points.length - desiredSides);
                fitness = fitness > 0 ? 1 / fitness : 2;
                
                
                double[] lengths = new double[points.length];
                double area = 0;
                for(int i = 0; i < points.length - 1; i++) {
                   lengths[i] = Math.sqrt(Math.pow(points[i].getX() 
                           - points[i + 1].getX(), 2) 
                           + Math.pow(points[i].getY() 
                           - points[i + 1].getY(), 2));
                   
                   area += points[i].getX() * points[i + 1].getY() 
                           - points[i].getY() * points[i + 1].getX();
                }
                
                lengths[lengths.length - 1] = Math.sqrt(Math.pow(points[0].getX() 
                        - points[points.length - 1].getX(), 2) 
                        + Math.pow(points[0].getY() 
                        - points[points.length - 1].getY(), 2));
                
                area += points[points.length - 1].getX() * points[0].getY() 
                        - points[points.length - 1].getY() * points[0].getX();
                
                area = Math.abs(area / 2);
                
                fitness += Math.log(area);
    
                double mean = 0;
                for(int i = 0; i < lengths.length; i++) {
                    mean += lengths[i];
                }
                mean /= lengths.length;
                
                double standardDev = 0;
                for(int i = 0; i < lengths.length; i++) {
                    standardDev += Math.pow(lengths[i] - mean, 2);
                }
                standardDev = Math.sqrt(standardDev);
                
                fitness += (standardDev > 0 ? 1 / standardDev : 2) * 2;
                
                return fitness;
            }
        });
        
        System.out.println(ga.getPopulations());   
        double bestFit = 0;
        int best = 0;
        for(int i = 0; i < ga.getPopulations().size(); i++) {
            if(ga.getPopulations().get(i).getFitness() > bestFit) {
                bestFit = ga.getPopulations().get(i).getFitness();
                best = i;
            }
        }
        System.out.println("Best fit: " + ga.getPopulations().get(best));
        
    }
}
