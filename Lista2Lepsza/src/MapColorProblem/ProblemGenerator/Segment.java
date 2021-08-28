package MapColorProblem.ProblemGenerator;

import java.awt.geom.Line2D;
import java.util.Objects;

public class Segment {
    Point a, b;
    public Segment(Point a, Point b){
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return (Objects.equals(a, segment.a) && Objects.equals(b, segment.b)) ||
                (Objects.equals(b, segment.a) && Objects.equals(a, segment.b));
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    public boolean collides(Segment other){
        if(a.equals(other.a) || a.equals(other.b) || b.equals(other.a) || b.equals(other.b)) return false;
        System.out.println("i");
        return Line2D.linesIntersect(a.x, a.y, b.x, b.y, other.a.x, other.a.y, other.b.x, other.b.y);
    }

    @Override
    public String toString() {
        return a + "->" + b;
    }
}
