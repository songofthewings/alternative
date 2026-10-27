/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import alternative.model.Aim;
import alternative.model.Alternative;
import java.util.Vector;

/**
 *
 * @author jiraffa
 */
public class GlobalResult {
    protected Vector<Double> leafPriorities;
    protected Matrix finalPriorities;
    protected Vector<Double> globalPriorities;
    protected Vector<Aim> leafs;
    protected Vector<Alternative> alternatives;
    
    public GlobalResult(Vector<Double> leafPriorities, Matrix finalPriorities,
                        Vector<Double> globalPriorities,
                        Vector<Aim> leafs, Vector<Alternative> alternatives) {
        this.leafPriorities = leafPriorities;
        this.finalPriorities = finalPriorities;
        this.globalPriorities = globalPriorities;
        this.leafs = leafs;
        this.alternatives = alternatives;
    }

    /**
     * @return the leafPriorities
     */
    public Vector<Double> getLeafPriorities() {
        return leafPriorities;
    }

    /**
     * @return the finalPriorities
     */
    public Matrix getFinalPriorities() {
        return finalPriorities;
    }

    /**
     * @return the globalPriorities
     */
    public Vector<Double> getGlobalPriorities() {
        return globalPriorities;
    }

    /**
     * @return the leafs
     */
    public Vector<Aim> getLeafs() {
        return leafs;
    }

    /**
     * @return the alternatives
     */
    public Vector<Alternative> getAlternatives() {
        return alternatives;
    }

    
}
