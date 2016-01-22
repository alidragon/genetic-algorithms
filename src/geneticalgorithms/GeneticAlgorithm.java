package geneticalgorithms;

import java.util.ArrayList;

/**
 * The actual code for running the genetic algorithm
 * 
 * @author Ali Persing
 */
public class GeneticAlgorithm {
    
    private ArrayList<Genome> population;
    private int populationSize; 
    private int inputLength;
    private double numKept;
    private int numGenerations;
    private double mutateChance;
    private int genNumber;
    private SelectionMethod method;
    private int gensToCallback;
    private Callback callback;
    
    private IGenomeFactory factory;
    
    public enum SelectionMethod {
        Tournament, Roulette
    }
    
    public GeneticAlgorithm(int populationSize, int inputLength, 
            double numKept, int numGenerations, double mutateChance, 
            IGenomeFactory factory, SelectionMethod method) {
        this.populationSize = populationSize;
        this.inputLength = inputLength;
        this.numKept = numKept;
        this.numGenerations = numGenerations;
        this.mutateChance = mutateChance;
        this.factory = factory;
        this.method = method;
        population = new ArrayList<>();
    }
    
    /**
     * 
     * @param populationSize
     * @param inputLength
     * @param numKept
     * @param numGenerations
     * @param mutateChance
     * @param factory
     * @param method
     * @param gensToCallback 
     * @param callback this method will be called every <gensToCallback> generations, to allow for visualization or manipulation of data
     */
    public GeneticAlgorithm(int populationSize, int inputLength, 
            double numKept, int numGenerations, double mutateChance, 
            IGenomeFactory factory, SelectionMethod method, int gensToCallback, Callback callback) {
        this.populationSize = populationSize;
        this.inputLength = inputLength;
        this.numKept = numKept;
        this.numGenerations = numGenerations;
        this.mutateChance = mutateChance;
        this.factory = factory;
        this.method = method;
        this.gensToCallback = gensToCallback;
        this.callback = callback;
        population = new ArrayList<>();
    }
    
    /**
     * fill the population with <populationSize> number of Genomes, 
     * which are randomly created with <inputLength> number of bits
     */
    public void makeInitialPopulation() {
        for(int i = 0; i < populationSize; i++) {
            population.add(factory.makeGenome());
        }
    }
    
    /**
     * selects a random genome using roulette selection
     * @return 
     */
    public Genome selectChromosome() {
        double something = Math.random();
        setProbabilities(totalFitness());
        for(int i = 0; i < population.size(); i++) {
            if(i == population.size() - 1 || 
                    (something > population.get(i).getProbability() && 
                    something < population.get(i + 1).getProbability())) {
                return population.get(i);
            }
        }
        return null;
    }
    
    public Genome tournamentSelect() {
        ArrayList<Genome> temp = new ArrayList<>();
        for(int i = 0; i < population.size() / 10; i++) {
            int rand = (int) (Math.random() * population.size());
            temp.add(population.get(rand));
        }
        //System.out.println(temp);
        return getMax(temp);
    }
    
    public Genome getMax(ArrayList<Genome> ar) {
        if(ar == null || ar.size() < 1) {
            return null;
        }
        Genome toReturn = ar.get(0);
        ArrayList<Genome> max = new ArrayList<>();
        for(int i = 1; i < ar.size(); i++) {
            if(toReturn.getFitness() < ar.get(i).getFitness()) {
                toReturn = ar.get(i);
            }
        }
        for(int i = 0; i < ar.size(); i++) {
            if(toReturn.getFitness() == ar.get(i).getFitness()) {
                max.add(ar.get(i));
            }
        }
        if(max.size() > 1) {
            toReturn = max.get((int) (Math.random() * max.size()));
        }
        return toReturn;
    }
    
    /**
     * creates two new children from the selected chromosomes
     * @return 
     */
    public ArrayList<Genome> generateNew() {
        Genome one = null;
        Genome two = null;
        switch(method) {
            case Tournament:
                one = tournamentSelect();
                two = tournamentSelect();
                break;
            case Roulette:
                one = selectChromosome();
                two = selectChromosome();
        }
        
        ArrayList<Genome> ar = new ArrayList<>();
        if(one.getFitness() == getMax(one, two).getFitness()) {
            ar.addAll(one.crossOver(two, inputLength / 2));
        } else {
            ar.addAll(two.crossOver(one, inputLength / 2));
        }
        ar.get(0).mutate(mutateChance /* *(1 - (genNumber / numGenerations))*/);
        ar.get(1).mutate(mutateChance /* *(1 - (genNumber / numGenerations))*/);
        //System.out.println(ar);
        return ar;
    }
    
    private Genome getMax(Genome one, Genome two) {
        return one.getFitness() > two.getFitness() ? one : two;
    }
    
    /**
     * 
     * @return total fitness of the entire population
     */
    private double totalFitness() {
        double sum = 0;
        for(Genome g : population) {
            sum += g.getFitness();
        }
        return sum;
    }
    
    private void setProbabilities(double total) {
        double sumProb = 0;
        for(Genome g : population) {
            double temp = sumProb;
            g.setProbability(temp);
            sumProb = temp  + (g.getFitness() / total);
        }
    }
    
    private ArrayList<Genome> newGen() {
        ArrayList<Genome> ar = new ArrayList<>();
        for(int i = 0; i < ((populationSize - 
                (int)(numKept * populationSize))) / 2; i++) {
            ArrayList<Genome> a2 = generateNew();
            ar.add(a2.get(0));
            ar.add(a2.get(1));
        }
        int s = ar.size();
        for(int i = 0; i < populationSize - s; i++) {
            Genome toKeep = null;;
            switch(method) {
                case Tournament:
                    toKeep = tournamentSelect();
                    break;
                case Roulette:
                    toKeep = selectChromosome();
                    break;
            }
            ar.add(factory.makeGenome(toKeep));
            //population.remove(toKeep);
        }
        return ar;
    }
    
    public double avgFitness() {
        return totalFitness() / (inputLength * populationSize);
    }
    
    public double maxFitness() {
        double max = 0;
        for(Genome g : population) {
            if(g.getFitness() > max) {
                max = g.getFitness();
            }
        }
        return max / inputLength;
    }
    
    public double minFitness() {
        double min = Double.MAX_VALUE;
        for(Genome g : population) {
            if(g.getFitness() < min) {
                min = g.getFitness();
            }
        }
        return min / inputLength;
    }
    
    public void run(Evolvable e) {
        makeInitialPopulation();
        //System.out.println(population);
        genNumber = 0;
        for(int i = 0; i < numGenerations; i++) {
            genNumber++;
            for(Genome g : population) {
                g.setFitness(e.determineFitness(g));
            }            
            
            if(callback != null && i % gensToCallback == 0) {
                callback.generation(this);
            }
            
            population = newGen();
        }
        
        for(Genome g : population) {
            g.setFitness(e.determineFitness(g));
        }            
    }
    
    public ArrayList<Genome> getPopulations() {
        return population;
    }

}
