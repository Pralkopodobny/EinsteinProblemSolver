package CSP.Constraints;

import java.util.ArrayList;

public interface UnaryConstraint<T> extends Constraint<T> {
    boolean AC3Domain(ArrayList<T> dom);
    int getIndex();
}
