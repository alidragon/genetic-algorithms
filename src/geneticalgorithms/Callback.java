package geneticalgorithms;

/**
 * Will be called by the genetic algorithm to perform other functions while the genetic algorithm is running
 * @author Ali Persing
 */

public interface Callback {
    public void generation(GeneticAlgorithm ga);
}
