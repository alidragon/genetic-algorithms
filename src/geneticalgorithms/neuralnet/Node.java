package geneticalgorithms.neuralnet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Ali Persing
 */
public class Node {
    private boolean set;
    private Map<Node, Double> connections;
    private double value;
    
    public Node() {
        connections = new HashMap<>();
    }
    
    public void setSet(boolean set) {
        this.set = set;
    }
    
    public boolean isSet() {
        return set;
    }

    public void addConnection(Node node, double weight) {
        connections.put(node, weight);
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    public void addValue(double value) {
        this.value += value;
    }
    
    public double getValue() {
        return value;
    }
    
    public Map<Node, Double> getConnections() {
        return connections;
    }
    
}
