import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
        FDSet correct = new FDSet(new FD(Arrays.asList("A","B"), List.of("A"))); // final answer should contain AB -> A
        correct.add(new FD(Arrays.asList("A","B"), List.of("B"))); //                                           AB -> B
        correct.add(new FD(Arrays.asList("A","B"), List.of("A","B")));//                                        AB -> AB
        assertTrue(trivial.getSet().containsAll(correct.getSet()));
    }

    /**
     * Unit test for Augment
     */
    @Test
    public void testAugment() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDSet augment = FDUtil.augment(fdset,Set.of("A","B","C"));
        FDSet correct = new FDSet(new FD(Arrays.asList("A","B"), List.of("A","C"))); // final answer should contain AB -> AC
        correct.add(new FD(Arrays.asList("A","B"), List.of("B","C"))); //                                           AB -> BC
        correct.add(new FD(Arrays.asList("A","B","C"), List.of("C")));//                                                AB -> C
        assertTrue(augment.getSet().containsAll(correct.getSet()));
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
        FDSet correct = new FDSet(new FD(Arrays.asList("A","B"), List.of("D"))); // final answer should contain AB -> AC
        assertTrue(transitive.getSet().containsAll(correct.getSet()));
    }

    @Test
    public void trivialNoModificaiton() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDSet fdset2 = new FDSet(fd1);
        FDSet trivial = FDUtil.trivial(fdset);
        assertEquals(fdset, fdset2);
    }

    @Test
    public void augmentNoModificaiton() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDSet fdset2 = new FDSet(fd1);
        FDSet augment = FDUtil.augment(fdset,Set.of("A","B","C"));
        assertEquals(fdset, fdset2);
    }

    @Test
    public void transitiveNoModificaiton() {
        FD fd1 = new FD(Arrays.asList("A","B"), List.of("C"));
        fdset = new FDSet(fd1);
        FDSet fdset2 = new FDSet(fd1);
        FDSet transitive = FDUtil.transitive(fdset);
        assertEquals(fdset,  fdset2);
    }


}