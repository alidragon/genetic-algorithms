package geneticalgorithms.strings;

import geneticalgorithms.Genome;
import java.util.ArrayList;
/**
 *
 * @author Ali Persing
 */
public class StringGenome extends Genome<String> {
    
    /**
     * create a genome with a random string of bits of the given length
     * @param length 
     */
    public StringGenome(int length) {
        setGenes(generateRandomString(length));
        //fitness = determineFitness();
    }
    
    /**
     * create a new genome which will use the given string as its value
     * if it is not only bits, it will generate a random one instead
     * @param s 
     */
    public StringGenome(String s) {
        boolean accepted = true;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != '0' && s.charAt(i) != '1') {
                accepted = false;
            }
        }
        if(!accepted) {
            setGenes(generateRandomString(s.length()));
        } else {
            setGenes(s);
        }
    }
    
    /**
     * 
     * @param chance a value between 1 and 0 which indicates the chance that a gene
     * will be mutated
     */
    public void mutate(double chance) {
        if(Math.random() < chance) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                //I really don't care
            }
            int toChange = (int) (Math.random() * getGenes().length());
            char current = getGenes().charAt(toChange);
            current = (current == '0')? '1' : '0';
            setGenes(getGenes().substring(0, toChange) + current + getGenes().substring(toChange + 1));
        }
    }
    
    /**
     * cross the two strings at the given point
     * @param other
     * @param pos 
     */
    public ArrayList<Genome> crossOver(Genome<String> other, int pos) {
        String firstSub = this.getGenes().substring(pos);
        String secondSub = other.getGenes().substring(pos);
        
        StringGenome one = new StringGenome(this.getGenes().substring(0, pos) + secondSub);
        StringGenome two = new StringGenome(other.getGenes().substring(0, pos) + firstSub);
        ArrayList<Genome> toReturn = new ArrayList<Genome>();
        toReturn.add(one);
        toReturn.add(two);
        return toReturn;
    }
    
    /**
;     * cross the two strings at a random location
     * @param other 
     */
    public ArrayList<Genome> crossOver(Genome<String> other) {
        int rand = (int) (Math.random() * this.getGenes().length());
        return crossOver(other, rand);
    }
    
    /**
     * generates a random string of bits of the given length
     * @param length
     * @return 
     */
    public static String generateRandomString(int length) {
        String toReturn = "";
        for(int i = 0; i < length; i++) {
            toReturn += (Math.random() < .5)? '0' : '1';
        }
        return toReturn;
    }
    
    public String toString() {
        return getGenes();
    }
    
    public boolean equals(Genome<String> g) {
        return this.getGenes().equals(g.getGenes());
    }

}
