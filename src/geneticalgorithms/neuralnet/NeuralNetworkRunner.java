package geneticalgorithms.neuralnet;

import geneticalgorithms.Evolvable;
import geneticalgorithms.GeneticAlgorithm;
import geneticalgorithms.Genome;
import javax.sound.midi.Soundbank;

/**
 *
 * @author Ali Persing
 */
public class NeuralNetworkRunner {
    
    private static double fourBitNum = Math.pow(2, 4);

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 1, .1, 5000, .5, new NeuralFactory(), GeneticAlgorithm.SelectionMethod.Tournament);
        ga.run(new Evolvable() {

            @Override
            public double determineFitness(Genome g) {
                if(g.getClass() != NeuralGenome.class) {
                    throw new UnsupportedOperationException();
                }
                NeuralGenome genome = (NeuralGenome)g;
                double toReturn = 0;
                
                for(int i = 0; i < fourBitNum; i++) {
                    for(int j = 0; j < fourBitNum; j++) {
                        String out = genome.getGenes().run(makeLen(Integer.toBinaryString(i), 4), makeLen(Integer.toBinaryString(j), 4));
                        int answerInt = i + j;
                        String answer = makeLen(Integer.toBinaryString(answerInt), 5);
                        
                        for(int k = 0; k < answer.length(); k++) {
                            if(out.charAt(k) == answer.charAt(k)) {
                                toReturn++;
                            }
                        }
                    }
                }
                
                return toReturn / 1280;
            }
            
            String makeLen(String s, int i) {
                String toReturn = s;
                while(toReturn.length() < i) {
                    toReturn = "0" + toReturn;
                }
                return toReturn;
            }
        });
    }
}
