package geneticalgorithms.neuralnet;

import geneticalgorithms.Genome;
import geneticalgorithms.IGenomeFactory;

/**
 *
 * @author Ali Persing
 */
class NeuralFactory implements IGenomeFactory {

    public NeuralFactory() {
    }

    @Override
    public Genome makeGenome() {
        return new NeuralGenome();
    }

    @Override
    public Genome makeGenome(Genome g) {
        if(g.getClass() != NeuralGenome.class) {
            throw new UnsupportedOperationException();
        }
        return new NeuralGenome(new NeuralNetwork(((NeuralGenome)g).getGenes()));
    }

}
