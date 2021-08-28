package UnitTests;
import CSP.Constraints.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.ConsoleHandler;

public class AC3Tests {

    @Test
    @DisplayName("Diff constraint AC3 test")
    public void testDiffConstraint(){
        DiffConstraint constraint = new DiffConstraint(0, 1, 1);
        var reversed = constraint.reversed();
        assertEquals("Reverse should work", 1, reversed.getFirst());
        assertEquals("Reverse should work", 0, reversed.getSec());

        ArrayList<Integer> dom1 = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        ArrayList<Integer> dom2 = new ArrayList<>(Arrays.asList(3,4,5,20));

        assertTrue("AC3 should work", constraint.AC3Domains(dom1, dom2));
        assertEquals("AC3 dom2", new ArrayList<Integer>(Arrays.asList(3, 4, 5, 20)), dom2);
        assertEquals("AC3 dom1", new ArrayList<Integer>(Arrays.asList(4, 5)), dom1);

        assertTrue("AC3 reversed should work", reversed.AC3Domains(dom2, dom1));
        assertEquals("AC3 dom1 r", new ArrayList<Integer>(Arrays.asList(4, 5)), dom1);
        assertEquals("AC3 dom2 r", new ArrayList<Integer>(Arrays.asList(3, 4)), dom2);

        assertFalse("AC3 reversed should work", reversed.AC3Domains(dom2, dom1));
        assertFalse("AC3 reversed should work", constraint.AC3Domains(dom1, dom2));
    }

    @Test
    @DisplayName("Abs diff constraint AC3 test")
    public void testAbsDiffConstraint(){
        AbsDiffConstraint constraint = new AbsDiffConstraint(0, 1, 1);
        var reversed = constraint.reversed();
        assertEquals("Reverse should work", 1, reversed.getFirst());
        assertEquals("Reverse should work", 0, reversed.getSec());

        ArrayList<Integer> dom1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
        ArrayList<Integer> dom2 = new ArrayList<>(Arrays.asList(3,4,5,20));

        assertTrue("AC3 should work", constraint.AC3Domains(dom1, dom2));
        assertEquals("AC3 dom2", new ArrayList<Integer>(Arrays.asList(3, 4, 5, 20)), dom2);
        assertEquals("AC3 dom1", new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6)), dom1);

        assertTrue("AC3 reversed should work", reversed.AC3Domains(dom2, dom1));
        assertEquals("AC3 dom1 r", new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6)), dom1);
        assertEquals("AC3 dom2 r", new ArrayList<Integer>(Arrays.asList(3, 4, 5)), dom2);

        assertFalse("AC3 reversed should work", reversed.AC3Domains(dom2, dom1));
        assertFalse("AC3 reversed should work", constraint.AC3Domains(dom1, dom2));
    }

    @Test
    @DisplayName("EqualConstraint AC3 test")
    public void testEqualConstraint(){
        EqualConstraint constraint = new EqualConstraint(0, 1);
        var reversed = constraint.reversed();
        assertEquals("Reverse should work", 1, reversed.getFirst());
        assertEquals("Reverse should work", 0, reversed.getSec());

        ArrayList<Integer> dom1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
        ArrayList<Integer> dom2 = new ArrayList<>(Arrays.asList(3,4,5,20));

        assertTrue("AC3 should work", constraint.AC3Domains(dom1, dom2));
        assertEquals("AC3 dom2", new ArrayList<Integer>(Arrays.asList(3, 4, 5, 20)), dom2);
        assertEquals("AC3 dom1", new ArrayList<Integer>(Arrays.asList(3, 4, 5)), dom1);

        assertTrue("AC3 reversed should work", reversed.AC3Domains(dom2, dom1));
        assertEquals("AC3 dom1 r", new ArrayList<Integer>(Arrays.asList(3, 4, 5)), dom1);
        assertEquals("AC3 dom2 r", new ArrayList<Integer>(Arrays.asList(3, 4, 5)), dom2);

        assertFalse("AC3 reversed should work", reversed.AC3Domains(dom2, dom1));
        assertFalse("AC3 reversed should work", constraint.AC3Domains(dom1, dom2));
    }

    @Test
    @DisplayName("NotEqualConstraint AC3 test")
    public void testNotEqualConstraint(){
        NotEqualConstraint constraint = new NotEqualConstraint(0, 1);
        var reversed = constraint.reversed();
        assertEquals("Reverse should work", 1, reversed.getFirst());
        assertEquals("Reverse should work", 0, reversed.getSec());

        ArrayList<Integer> dom1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
        ArrayList<Integer> dom2 = new ArrayList<>(Arrays.asList(3,4,5,20));
        ArrayList<Integer> dom3 = new ArrayList<>(Collections.singletonList(20));
        ArrayList<Integer> dom4 = new ArrayList<>(Collections.singletonList(2));

        assertFalse("AC3 should work 1", constraint.AC3Domains(dom1, dom2));
        assertFalse("AC3 should work 2", constraint.AC3Domains(dom1, dom3));
        assertFalse("AC3 should work 3", constraint.AC3Domains(dom2, dom4));



        assertTrue("AC3 should work 4", constraint.AC3Domains(dom2, dom3));
        assertEquals("AC3 dom3", new ArrayList<Integer>(Collections.singletonList(20)), dom3);
        assertEquals("AC3 dom2 r", new ArrayList<Integer>(Arrays.asList(3, 4, 5)), dom2);

        assertTrue("AC3 should work", constraint.AC3Domains(dom1, dom4));
        assertEquals("AC3 dom4", new ArrayList<Integer>(Collections.singletonList(2)), dom4);
        assertEquals("AC3 dom1", new ArrayList<Integer>(Arrays.asList(1, 3, 4, 5, 6, 7)), dom1);

        assertFalse("AC3  should work", reversed.AC3Domains(dom2, dom3));
        assertFalse("AC3  should work", constraint.AC3Domains(dom1, dom4));
    }
    @Test
    @DisplayName("EqualToValue AC3 test")
    public void testEqualToValueConstraint(){
        EqualToValueConstraint constraint = new EqualToValueConstraint(0, 4);
        ArrayList<Integer> dom1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
        ArrayList<Integer> dom2 = new ArrayList<>(Arrays.asList(3,5,20));

        assertTrue("AC3 should work 1", constraint.AC3Domain(dom1));
        assertEquals("AC3 dom1", new ArrayList<Integer>(Collections.singletonList(4)), dom1);
        assertFalse("AC3 should work 2", constraint.AC3Domain(dom1));

        assertTrue("AC3 should work 3", constraint.AC3Domain(dom2));
        assertEquals("AC3 dom2", new ArrayList<Integer>(), dom2);
        assertFalse("AC3 should work 4", constraint.AC3Domain(dom2));
    }

    @Test
    @DisplayName("EqualTests")
    public void equalTest(){
        BinaryConstraint<Integer> a = new AbsDiffConstraint(1, 2, 3);
        BinaryConstraint<Integer> b = new AbsDiffConstraint(1, 2, 3);
        assertEquals("t1:", a, b);
    }
}
