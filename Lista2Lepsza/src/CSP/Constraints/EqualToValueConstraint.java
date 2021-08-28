package CSP.Constraints;

import java.util.ArrayList;

public class EqualToValueConstraint implements UnaryConstraint<Integer> {
    private int index;
    private ArrayList<Integer> solution;
    private Integer value;

    public EqualToValueConstraint(int index, Integer value){
        this.index = index;
        this.value = value;
    }

    @Override
    public boolean satisfied() {
        if(solution.get(index) == null) return true;
        return solution.get(index).equals(value);
    }

    @Override
    public void setResultArray(ArrayList<Integer> result) {
        this.solution = result;
    }

    @Override
    public boolean AC3Domain(ArrayList<Integer> dom) {
        ArrayList<Integer> domRm = new ArrayList<>(dom.size());
        for(int i = 0; i < dom.size(); i++){
            int val = dom.get(i);
            if(val != value) domRm.add(i);
        }
        if(domRm.size() == 0)
            return false;
        for(int i = domRm.size() - 1; i >= 0; i--){
            int indexToRemove = domRm.get(i);
            dom.remove(indexToRemove);
        }
        return true;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void simplifyDomains(Integer value, ArrayList<ArrayList<Integer>> domains) { };
}
