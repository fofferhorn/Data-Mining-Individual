package datamining.datastructure;

import java.text.DecimalFormat;
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
        List<String> set = new ArrayList<>();

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

    /**
     * Used for pretty printing the frequent item sets.
     * @param frequentItemSets A list with all the frequent item sets.
     *                         Will be printed in the same order they are in the list
     * @param supportThreshold The support threshold.
     */
    public static void printFrequentItemSets(List<ItemSet> frequentItemSets, double supportThreshold) {
        DecimalFormat df = new DecimalFormat("#%");

        System.out.println("\nThe ItemSets with support higher than " + df.format(supportThreshold) + " are:");

        ListIterator it = frequentItemSets.listIterator();

        System.out.println("---------------------------------------------");
        System.out.println("Set\t\t\tSupport");
        System.out.println("---------------------------------------------");

        while (it.hasNext()) {
            ItemSet itemSet = (ItemSet) it.next();
            System.out.print(padRight(Arrays.toString(itemSet.set.toArray()), 20) + "\t");
            System.out.println(df.format(itemSet.support));
        }
        System.out.println();
    }

    private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

}
