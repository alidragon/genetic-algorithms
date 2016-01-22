
package geneticalgorithms.neuralnet;

import geneticalgorithms.strings.StringGenome;
import geneticalgorithms.Genome;
import java.util.ArrayList;

/**
 *
 * @author Ali Persing
 */
public class NeuralGenome extends Genome<NeuralNetwork> {
    
    public NeuralGenome(NeuralNetwork genes) {
        this.setGenes(genes);
    }

    public NeuralGenome() {
        this.setGenes(new NeuralNetwork());
    }


    @Override
    public void mutate(double chance) {
        if(Math.random() < chance) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                //I really don't care
            }
            getGenes().mutateInput();
            getGenes().mutateHidden();
        }
    }

    @Override
    public ArrayList<Genome> crossOver(Genome other, int pos) {
        return crossOver(other);
    }

    @Override
    public ArrayList<Genome> crossOver(Genome other) {
        ArrayList<Genome> toReturn = new ArrayList<>();
        if(other.getClass() != NeuralGenome.class) {
            throw new UnsupportedOperationException();
        }
        NeuralGenome otherNeural = (NeuralGenome) other;
        toReturn.add(new NeuralGenome(new NeuralNetwork(this.getGenes(), otherNeural.getGenes())));        
        toReturn.add(new NeuralGenome(new NeuralNetwork(this.getGenes(), otherNeural.getGenes())));

        return toReturn;
    }
    
}
