package CSP.Constraints;

import java.util.LinkedList;

public interface NAryConstraint<T> extends Constraint<T> {
    LinkedList<BinaryConstraint<T>> getBinary();
}
