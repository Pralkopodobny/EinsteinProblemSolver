package CSP.Constraints;

import java.util.ArrayList;
import java.util.LinkedList;

public class MassNotEqualConstraint implements NAryConstraint<Integer> {

    private int[] otherIndexes;
    private int index;
    private ArrayList<Integer> solution;
    private LinkedList<BinaryConstraint<Integer>> binary;
    public MassNotEqualConstraint(int index, int[] otherIndexes){
        this.otherIndexes = otherIndexes;
        this.index = index;
        this.binary = convertToBinary();
    }

    @Override
    public boolean satisfied() {
        for(int i : otherIndexes){
            if(solution.get(i) != null && i != index && solution.get(index).equals(solution.get(i)))
                return false;

        }
        return true;
    }

    @Override
    public void setResultArray(ArrayList<Integer> result) {
        this.solution = result;
    }

    @Override
    public LinkedList<BinaryConstraint<Integer>> getBinary() {
        return binary;
    }

    private LinkedList<BinaryConstraint<Integer>> convertToBinary(){
        LinkedList<BinaryConstraint<Integer>> constraints = new LinkedList<>();
        for(int i = 0; i <otherIndexes.length; i++){
            if(otherIndexes[i] != index)
                constraints.add(new NotEqualConstraint(index, otherIndexes[i]));
        }
        return constraints;
    }

    @Override
    public void simplifyDomains(Integer value, ArrayList<ArrayList<Integer>> domains) {
        for(var b : binary){
            b.simplifyDomains(value, domains);
        }
    }
}
