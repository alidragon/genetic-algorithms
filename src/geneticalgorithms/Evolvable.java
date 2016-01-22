package geneticalgorithms;

/**
 * Defines how the Genetic algorithm will decide how fit a given genome is.
 * A larger value for the fitness is a more fit individual
 * 
 * @author Ali Persing
 */
public interface Evolvable {
    
    public double determineFitness(Genome g);

}
