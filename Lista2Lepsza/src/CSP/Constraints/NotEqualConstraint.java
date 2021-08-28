package CSP.Constraints;

import java.util.ArrayList;
import java.util.Objects;

public class NotEqualConstraint implements BinaryConstraint<Integer> {
    private int first, sec;
    private ArrayList<Integer> solution;

    public NotEqualConstraint(int first, int sec) {
        this.first = first;
        this.sec = sec;
    }

    @Override
    public boolean satisfied() {
        if(solution.get(first) == null || solution.get(sec) == null) return true;
        return !solution.get(first).equals(solution.get(sec));
    }

    @Override
    public void setResultArray(ArrayList<Integer> result) {
        this.solution = result;
    }

    @Override
    public String toString() {
        return "NEC(" + first + ", " + sec + ")";
    }

    @Override
    public int getFirst() {
        return first;
    }

    @Override
    public int getSec() {
        return sec;
    }

    @Override
    public boolean AC3Domains(ArrayList<Integer> dom1, ArrayList<Integer> dom2) {
        boolean rm1;
        rm1 = AC3DomainsStep(dom1, dom2);
        return rm1;
    }

    private boolean AC3DomainsStep(ArrayList<Integer> dom1, ArrayList<Integer> dom2){
        if(dom2.size() != 1){
            return false;
        }
        ArrayList<Integer> dom1rm = new ArrayList<>(dom1.size());
        for(int i = 0; i < dom1.size(); i++){
            boolean remove = true;
            int val = dom1.get(i);
            if(val == dom2.get(0)) dom1rm.add(i);
        }
        if(dom1rm.size() == 0){
            return false;
        }
        else{
            for(int i = dom1rm.size() - 1; i >=0; i--){
                int indexToRemove = dom1rm.get(i);
                dom1.remove(indexToRemove);
            }
            return true;
        }
    }

    @Override
    public BinaryConstraint<Integer> reversed() {
        return new NotEqualConstraint(sec, first);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotEqualConstraint that = (NotEqualConstraint) o;
        return first == that.first &&
                sec == that.sec;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, sec);
    }

    @Override
    public void simplifyDomains(Integer value, ArrayList<ArrayList<Integer>> domains) {
        var domain = domains.get(sec);
        ArrayList<Integer> dom2rm = new ArrayList<>(domain.size());

        for(int i = 0; i < domain.size(); i++){
            if(domain.get(i).equals(value))
                dom2rm.add(i);
        }
        for(int i = dom2rm.size() - 1; i >=0; i--){
            int index2remove = dom2rm.get(i);
            domain.remove(index2remove);
        }
    }
}
