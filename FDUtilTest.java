import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Tests your FDUtil Class
 *
 * @author Lucas Gover
 * @version 10 / 26 / 2022
 */

public class FDUtilTest {
    FDSet fdset;

    @Before
    public void setUp(){}


    /**
     * Unit test for Trivial
     */
    @Test
    public void testTrivial() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDSet trivial = FDUtil.trivial(fdset);
        assertTrue(trivial.getSet().contains(new FD(Arrays.asList("A","B"), List.of("A")))); // trivial should contain A B -> A
        assertTrue(trivial.getSet().contains(new FD(Arrays.asList("A","B"), List.of("B")))); // trivial should contain A B -> B
        assertTrue(trivial.getSet().contains(new FD(Arrays.asList("A","B"), List.of("A","B"))));//      should contain A B -> A B
        assertEquals(trivial.size(),3); // check you didn't add any extras
    }

    /**
     * Unit test for Augment
     */
    @Test
    public void testAugment() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDSet augment = FDUtil.augment(fdset,Set.of("A","B","C"));
        assertTrue(augment.getSet().contains(new FD(Arrays.asList("A","B"), List.of("A","C")))); // augment's return should include AB -> AC
        assertTrue(augment.getSet().contains(new FD(Arrays.asList("A","B"), List.of("B","C")))); // augment's return should include AB -> BC
        assertTrue(augment.getSet().contains(new FD(Arrays.asList("A","B","C"), List.of("C")))); // augment's return should include ABC -> C
        assertEquals(augment.size(),3);
    }

    /**
     * Unit test for Transitive
     */
    @Test
    public void testTransitive() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        FD fd2 = new FD(List.of("C"), List.of("D"));
        fdset = new FDSet(fd1);
        fdset.add(fd2);
        FDSet transitive = FDUtil.transitive(fdset);
        assertTrue(transitive.getSet().contains(new FD(Arrays.asList("A","B"), List.of("D")))); // transitive's return should include AB -> D
        assertEquals(transitive.size(),1); // shouldn't return anything else
    }
    /**
     * Trivial doesn't modify input. If you fail this test you probably made a shallow copy somewhere.
     */
    @Test
    public void trivialNoModificaiton() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDUtil.trivial(fdset);
        assertEquals(fdset.size(), 1); // check input size is the same
        assertTrue(fdset.getSet().contains(new FD(Arrays.asList("A","B"), List.of("C")))); // checks that elements are the same
    }

    /**
     * Augment doesn't modify input. If you fail this test you probably made a shallow copy somewhere.
     */
    @Test
    public void augmentNoModificaiton() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDUtil.augment(fdset,Set.of("A","B","C"));
        assertEquals(fdset.size(), 1); // check input size is the same
        assertTrue(fdset.getSet().contains(new FD(Arrays.asList("A","B"), List.of("C")))); // checks that elements are the same
    }

    /**
     * transitive doesn't modify input. If you fail this test you probably made a shallow copy somewhere.
     */
    @Test
    public void transitiveNoModificaiton() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        FD fd2 = new FD(List.of("C"), List.of("D"));
        fdset = new FDSet(fd1,fd2);
        FDUtil.transitive(fdset);
        assertEquals(fdset.size(), 2); // check input size is the same
        assertTrue(fdset.getSet().contains(new FD(Arrays.asList("A","B"), List.of("C")))); // checks that elements are the same
        assertTrue(fdset.getSet().contains(new FD(List.of("C"), List.of("D")))); // checks that elements are the same
    }

    /**
     * Unit test for SetClosure
     */
    @Test
    public void fdSetClosure() {
        FD fd1 = new FD(List.of("A"), List.of("B")); // A --> B
        FD fd2 = new FD(Arrays.asList("A", "B"), List.of("C"));  // AB --> C
        fdset = new FDSet(fd1,fd2);
        FDSet setClosure = FDUtil.fdSetClosure(fdset);
        assertTrue(setClosure.getSet().contains(new FD(List.of("A"), List.of("A")))); //        A --> A
        assertTrue(setClosure.getSet().contains(new FD(List.of("A"), List.of("B"))));//        A --> B
        assertTrue(setClosure.getSet().contains(new FD(List.of("A"), List.of("B"))));//        A --> C
        assertTrue(setClosure.getSet().contains(new FD(List.of("A"), List.of("A","B"))));//        A --> AB
        assertTrue(setClosure.getSet().contains(new FD(List.of("A"), List.of("A","C"))));//        A --> AC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A"), List.of("B","C"))));//        A --> BC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A"), List.of("A","B","C"))));//        A --> ABC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B"), List.of("A"))));//        AB --> A
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B"), List.of("B"))));//        AB --> B
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B"), List.of("C"))));//        AB --> C
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B"), List.of("A","B"))));//        AB --> AB
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B"), List.of("A","C"))));//        AB --> AC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B"), List.of("B","C"))));//        AB --> BC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B"), List.of("A","B","C"))));//        AB --> ABC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","C"), List.of("A"))));//        AC --> A
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","C"), List.of("B"))));//        AC --> B
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","C"), List.of("C"))));//        AC --> C
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","C"), List.of("A","B"))));//        AC --> AB
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","C"), List.of("A","C"))));//        AC --> AC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","C"), List.of("B","C"))));//        AC --> BC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","C"), List.of("A","B","C"))));//        AC --> ABC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B","C"), List.of("A"))));//        ABC --> A
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B","C"), List.of("B"))));//        ABC --> B
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B","C"), List.of("C"))));//        ABC --> C
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B","C"), List.of("A","B"))));//        ABC --> AB
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B","C"), List.of("A","C"))));//        ABC --> AC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B","C"), List.of("B","C"))));//        ABC --> BC
        assertTrue(setClosure.getSet().contains(new FD(List.of("A","B","C"), List.of("A","B","C"))));//        ABC --> ABC
        assertEquals(setClosure.size(),28); // check you didn't add any extras
    }
}