package geneticalgorithms.strings;

import geneticalgorithms.Evolvable;
import geneticalgorithms.GeneticAlgorithm;
import geneticalgorithms.Genome;
import geneticalgorithms.strings.StringGenomeFactory;
import geneticalgorithms.strings.StringGenome;

/**
 *
 * @author Ali Persing
 */
public class GeneticAlgorithmTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 15, .1, 1000, .05, new StringGenomeFactory(15), GeneticAlgorithm.SelectionMethod.Tournament);
        ga.run(new Evolvable() {
            @Override
            public double determineFitness(Genome g) {
                if(g.getClass() != StringGenome.class) {
                    throw new UnsupportedOperationException("Wrong type");
                }
                String s = ((StringGenome)g).getGenes();
                double toReturn = 0;
                for(int i = 0; i < s.length(); i++) {
                    if(s.charAt(i) == '1') {
                        toReturn++;
                    }
                }
                return toReturn;
            }
        });
        System.out.println(ga.getPopulations());   
    }
}
