package datamining.datastructure;

import java.util.*;

/***
 * The ItemSet class is used to store information concerning a single transaction.
 * Should not need any changes.
 *
 */
public class ItemSet {
	
	/***
	 * The PRIMES array is internally in the ItemSet-class' hashCode method
	 */
    public final List<String> set;
    public double support;

    /***
     * Creates a new instance of the ItemSet class.
     * @param set Transaction content
     */
    public ItemSet( List<String> set ) {
        this.set = set;
        support = 0;
    }

    /***
     * Creates a new instance of the ItemSet class.
     * @param set Transaction content
     * @param support The support for the itemSet.
     */
    public ItemSet( List<String> set, double support ) {
        this.set = set;
        this.support = support;
    }

    @Override
    /**
     * Used to determine whether two ItemSet objects are equal
     */
    public boolean equals( Object o ) {
        if (!(o instanceof ItemSet)) {
            return false;
        }
        ItemSet other = (ItemSet) o;
        if (other.set.size() != this.set.size()) {
            return false;
        }
        for (int i = 0; i < set.size(); i++) {
            if (!set.get(i).equals(other.set.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemSet clone() {
        List<String> set = new ArrayList<String>();

        for (String i : this.set) {
            set.add(i);
        }

        ItemSet itemSet = new ItemSet(set, this.support);

        return itemSet;
    }

    /**
    * Used to determine whether all elements exept the last in two ItemSet objects are equal
    */
    public boolean exceptLastEquals(ItemSet other) {

        if (other.set.size() != this.set.size()) return false;

        if (this.set.size() == 1) return true;

        for (int i = 0; i < this.set.size() - 1; i++) {
            if (!this.set.get(i).equals(other.set.get(i))) return false;
        }

        return true;
    }

    /**
    * Adds an element to this ItemList object
    * @param i The integer to add to the ItemList
    */
    public void add(String i) {
        this.set.add(i);
    }

    public static String toString(ItemSet itemSet) {
        String result = "[";

        ListIterator it = itemSet.set.listIterator();

        while (it.hasNext()) {
            String item = (String) it.next();

            result += item;
            if (it.hasNext()) result += ", ";
        }

        return result + "]";
    }
}
