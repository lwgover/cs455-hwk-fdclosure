import java.util.Set;
import java.util.HashSet;

/**
 * Some useful methods on FD sets
 * 
 * @author David
 * @version 5/18/2022
 */
public class FDUtil {

  /**
   * Resolves trivial FDs in the given FD set and combines with the given FD set
   * 
   * @param fdset (Immutable) FD Set
   * @return the given FD unioned with its trivial FDs
   */
  public static FDSet trivial(FDSet fdset) {
    // TODO: Obtain the power set of each FD's left-hand attributes. For each
    // element in the power set, create a new FD and add it to the result.

    return null;
  }

  /**
   * Augments every FD in the given set with all subsets of the given attributes
   * 
   * @param fdset (Immutable) FD Set
   * @param attrs a set of attributes with which to augment FDs
   * @return the given FD unioned with the set of augmented FDs
   */
  public static FDSet augment(FDSet fdset, Set<String> attrs) {
    // TODO: Clone each FD and then union of both sides with the given set of
    // attributes, and add this new FD to the result.

    return null;
  }

  /**
   * Resolves transitive FDs and combines them with the given FD set
   * 
   * @param fdset (Immutable) FD Set
   * @return the given FD unioned with all transitive FDs
   */
  public static FDSet transitive(FDSet fdset) {
    // TODO: Examine each pair of FDs in the given set. If the transitive property
    // holds on the pair of FDs, then generate the new FD and add it to the result.
    // Repeat until no new transitive FDs are found.

    return null;
  }

  /**
   * Generates the closure of the given FD Set
   * 
   * @param fset (Immutable) FD Set
   * @return the closure of the given FD Set
   */
  public static FDSet fdSetClosure(FDSet fdset) {
    // TODO: Generate new FDs by applying Trivial and Augmentation Rules, followed
    // by Transitivity Rule, and add new FDs to the result.
    // Repeat until no further changes are detected.

    return null;
  }

  /**
   * Generates the power set of the given set of elements
   * 
   * @param set Any set of elements (Immutable)
   * @return the power set of the input set
   */
  @SuppressWarnings("unchecked")
  public static <E> Set<Set<E>> powerSet(Set<E> set) {

    // base case: power set of the empty set is the set containing the empty set
    if (set.size() == 0) {
      Set<Set<E>> basePset = new HashSet<>();
      basePset.add(new HashSet<>());
      return basePset;
    }

    // remove the first element from the current set
    E[] attrs = (E[]) set.toArray();
    set.remove(attrs[0]);

    // recurse and obtain the power set of the reduced set of elements
    Set<Set<E>> currentPset = FDUtil.powerSet(set);

    // restore the element from input set
    set.add(attrs[0]);

    // iterate through all elements of current power set and union with first
    // element
    Set<Set<E>> otherPset = new HashSet<>();
    for (Set<E> attrSet : currentPset) {
      Set<E> otherAttrSet = (HashSet<E>) ((HashSet<E>) attrSet).clone();
      otherAttrSet.add(attrs[0]);
      otherPset.add(otherAttrSet);
    }
    currentPset.addAll(otherPset);
    return currentPset;
  }
}