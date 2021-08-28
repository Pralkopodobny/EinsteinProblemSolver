package CSP.Heuristics;

public class NaiveHeuristic implements Heuristic {
    int index = 0;

    @Override
    public int next() {
        return ++index;
    }

    @Override
    public void back() {
        index--;
    }
    @Override
    public int first(){
        return 0;
    }
}
