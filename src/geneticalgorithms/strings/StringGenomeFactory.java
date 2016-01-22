package geneticalgorithms.strings;

import geneticalgorithms.Genome;
import geneticalgorithms.IGenomeFactory;
import geneticalgorithms.strings.StringGenome;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Ali Persing
 */
public class StringGenomeFactory implements IGenomeFactory {
    private int size;
    
    public StringGenomeFactory(int size) {
        this.size = size;
    }

    @Override
    public Genome makeGenome() {
        return new StringGenome(size);
    }

    @Override
    public Genome makeGenome(Genome g) {
        if(g.getClass() == StringGenome.class) {
            return new StringGenome(((StringGenome)g).getGenes());
        } 
        throw new NotImplementedException();
    }

}
