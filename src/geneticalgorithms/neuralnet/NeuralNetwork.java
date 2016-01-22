package geneticalgorithms.neuralnet;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.naming.OperationNotSupportedException;

/**
 * For adding 2 binary numbers
 * @author Ali Persing
 */
public class NeuralNetwork {
    private Node[] inputs;
    private Node[] hidden;
    private Node[] outputs;
    private double THRESHOLD = .5;
    
    private static int HIDDEN_SIZE = 8;
    
    public NeuralNetwork(NeuralNetwork toCopy) {
        inputs = new Node[9];
        outputs = new Node[5];
        hidden = new Node[HIDDEN_SIZE];
        
        for(int i = 0; i < inputs.length; i++) {
            inputs[i] = new Node();
        }
        
        for(int i = 0; i < hidden.length; i++) {
            hidden[i] = new Node();
        }
        
        for(int i = 0; i < outputs.length; i++) {
            outputs[i] = new Node();
        }
        
        for(int i = 0; i < inputs.length; i++) {
            for(int j = 0; j < hidden.length; j++){
                inputs[i].addConnection(hidden[j], toCopy.inputs[i].getConnections().get(toCopy.hidden[j]));
            }
        }
        
        for(int i = 0; i < hidden.length; i++) {
            for(int j = 0; j < outputs.length; j++){
                hidden[i].addConnection(outputs[j], toCopy.hidden[i].getConnections().get(toCopy.outputs[j]));
            }
        }
    }
    
    public NeuralNetwork() {
        inputs = new Node[9];
        outputs = new Node[5];
        hidden = new Node[HIDDEN_SIZE];
        
        for(int i = 0; i < inputs.length; i++) {
            inputs[i] = new Node();
        }
        
        for(int i = 0; i < hidden.length; i++) {
            hidden[i] = new Node();
        }
        
        for(int i = 0; i < outputs.length; i++) {
            outputs[i] = new Node();
        }
        Random r = new Random();
        for(int i = 0; i < inputs.length; i++) {
            for(int j = 0; j < hidden.length; j++){
                double weight = r.nextDouble();
                inputs[i].addConnection(hidden[j], weight);
            }
        }
        
        for(int i = 0; i < hidden.length; i++) {
            for(int j = 0; j < outputs.length; j++){
                double weight = r.nextDouble();
                hidden[i].addConnection(outputs[j], weight);
            }
        }
    }
    
    public NeuralNetwork(NeuralNetwork one, NeuralNetwork two) {
        inputs = new Node[9];
        outputs = new Node[5];
        hidden = new Node[HIDDEN_SIZE];
        
        for(int i = 0; i < inputs.length; i++) {
            inputs[i] = new Node();
        }
        
        for(int i = 0; i < hidden.length; i++) {
            hidden[i] = new Node();
        }
        
        for(int i = 0; i < outputs.length; i++) {
            outputs[i] = new Node();
        }
        
        Random r = new Random();
        NeuralNetwork n;
        for(int i = 0; i < inputs.length; i++) {
            for(int j = 0; j < hidden.length; j++){
                n = (r.nextDouble() < .5) ? one : two;
                inputs[i].addConnection(hidden[j], n.inputs[i].getConnections().get(n.hidden[j]));
            }
        }
        
        for(int i = 0; i < hidden.length; i++) {
            for(int j = 0; j < outputs.length; j++){
                n = (r.nextDouble() < .5) ? one : two;
                hidden[i].addConnection(outputs[j], n.hidden[i].getConnections().get(n.outputs[j]));
            }
        }
    }
    
    public void mutateInput() {
        Random r = new Random();
        int inputToModify = r.nextInt(inputs.length);
        int hiddenPathToModify = r.nextInt(hidden.length);
        double value = r.nextDouble();
        inputs[inputToModify].getConnections().put(hidden[hiddenPathToModify], value);
    }
    
    public void mutateHidden() {
        Random r = new Random();
        int inputToModify = r.nextInt(hidden.length);
        int hiddenPathToModify = r.nextInt(outputs.length);
        double value = r.nextDouble();
        hidden[inputToModify].getConnections().put(outputs[hiddenPathToModify], value);
        
    }
    
    public String run(String inputOne, String inputTwo) {
        if(inputOne.length() != 4 || inputTwo.length() != 4) {
            throw new IllegalArgumentException("Input strings must be 4 characters long");
        }
        //clear all values
        for(int i = 0; i < hidden.length; i++) {
            hidden[i].setValue(0);
        }
        
        for(int i = 0; i < outputs.length; i++) {
            outputs[i].setValue(0);
        }
        
        //add all to input nodes
        for(int i = 0; i < inputs.length; i++) {
            String toUse;
            int index = i;
            if(i < 4) {
                toUse = inputOne;
            } else if(i < 8) {
                toUse = inputTwo;
                index = i - 4;
            } else {
                toUse = "1";
                index = 0;
            }
            
            boolean set = (toUse.charAt(index) == '1');
            inputs[i].setSet(set);
        }
        
        //run through all input nodes, and add values to hidden layer's nodes
        for(int i = 0 ; i < inputs.length; i++) {
            if(inputs[i].isSet()) {
                Map<Node, Double> connections = inputs[i].getConnections();
                for(int j = 0; j < hidden.length; j++) {
                    hidden[j].addValue(connections.get(hidden[j]));
                }
            }
            
        }
        
        //determine set or not set of each hidden layer node
        
        for(int i = 0; i < hidden.length; i++) {
            hidden[i].setSet(hidden[i].getValue() > THRESHOLD);
        }
        
        //run through all hidden layer nodes, and add values to output layer's nodes
        for(int i = 0 ; i < hidden.length; i++) {
            if(hidden[i].isSet()) {
                Map<Node, Double> connections = hidden[i].getConnections();
                for(int j = 0; j < outputs.length; j++) {
                    outputs[j].addValue(connections.get(outputs[j]));
                }
            }
        }
        
        //determine set or not set of each output layer node
        for(int i = 0; i < outputs.length; i++) {
            outputs[i].setSet(outputs[i].getValue() > THRESHOLD);
        }
        
        //create string to return
        String toReturn = "";
        
        for(int i = 0; i < outputs.length; i++) {
            toReturn += ((outputs[i].isSet()) ? '1' : '0');
        }
        
        return toReturn;
        
    }
}
