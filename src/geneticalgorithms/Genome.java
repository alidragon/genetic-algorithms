package geneticalgorithms;

import java.util.ArrayList;

/**
 * 
 * @author Ali Persing
 */
public abstract class Genome<T> {
    
    private double fitness;
    private double probability;
    private T genes;
    
    public double getFitness() {
        return fitness;
    }
    
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    public void setProbability(double probability) {
        this.probability = probability;
    }
    
    public double getProbability() {
        return probability;
    }
    
    public T getGenes() {
        return genes;
    }
    
    public void setGenes(T genes) {
        this.genes = genes;
    }
    
    public abstract void mutate(double chance);
    
    public abstract ArrayList<Genome> crossOver(Genome<T> other, int pos);
    
    public abstract ArrayList<Genome> crossOver(Genome<T> other);
    
}
