package CSP.Constraints;

import java.util.ArrayList;

public interface BinaryConstraint<T> extends Constraint<T>{
    int getFirst();
    int getSec();
    //deletes inconsistencies with ONLY dom1 and returns if there were any inconsistencies
    boolean AC3Domains(ArrayList<T> dom1, ArrayList<T> dom2);
    BinaryConstraint<T> reversed();
}
