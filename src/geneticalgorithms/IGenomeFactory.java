package geneticalgorithms;

/**
 * A factory for the genetic algorithm to create new genomes
 * @author Ali Persing
 */
public interface IGenomeFactory {
    
    /**
     * Create a new randomly initialized genome
     * @return a new genome
     */
    public Genome makeGenome();
    
    /**
     * Create a copy of the passed in genome
     * @param g
     * @return a copy of the passed in genome
     */
    public Genome makeGenome(Genome g);

}
