package CSP.Constraints;

import java.util.ArrayList;

public interface Constraint<T> {
    boolean satisfied();
    void setResultArray(ArrayList<T> result);
    void simplifyDomains(T value, ArrayList<ArrayList<T>> domains);
}
