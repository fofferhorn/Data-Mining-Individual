package datamining.apriori;

import datamining.datastructure.ItemSet;

import java.util.*;
import java.text.*;

import static datamining.datastructure.ItemSet.printFrequentItemSets;


public class Apriori {
    // Test data
    private static final List<ItemSet> TRANSACTIONS = new ArrayList<>(
            Arrays.asList(
                new ItemSet(Arrays.asList("1", "2", "3", "4", "5")),
                new ItemSet(Arrays.asList("1", "3", "5")),
                new ItemSet(Arrays.asList("2", "3", "5")),
                new ItemSet(Arrays.asList("1", "5")),
                new ItemSet(Arrays.asList("1", "3", "4")),
                new ItemSet(Arrays.asList("2", "3", "5")),
                new ItemSet(Arrays.asList("2", "3", "5")),
                new ItemSet(Arrays.asList("3", "4", "5")),
                new ItemSet(Arrays.asList("4", "5")),
                new ItemSet(Arrays.asList("2")),
                new ItemSet(Arrays.asList("2", "3")),
                new ItemSet(Arrays.asList("2", "3", "4")),
                new ItemSet(Arrays.asList("3", "4", "5"))
            )
        );

    public List<ItemSet> frequentItemSets;
    public List<AssociationRule> assRules;

    public Apriori(List<ItemSet> frequentItemSets, List<AssociationRule> assRules) {
        this.frequentItemSets = frequentItemSets;
        this.assRules = assRules;
    }

    /**
     * Just used for testing Apriori
     * @param args No args are used.
     */
    public static void main( String[] args ) {
        // TODO: Select a reasonable support threshold via trial-and-error. Can either be percentage or absolute value.
        final double supportThreshold = 0.40;
        Apriori apriori = apriori( TRANSACTIONS, supportThreshold );

        ItemSet.printFrequentItemSets(apriori.frequentItemSets, supportThreshold);

        AssociationRule.printAssociationRules(apriori.assRules);
    }

    /**
    * Runs the Apriori algorithm on the given transactions with the given support threshold.
    * @param transactions The transactions for witch to run Apriori.
    * @param supportThreshold The support threshold
    * @return A list with all the ItemSets that are above the support threshold.
    */
    public static Apriori apriori(List<ItemSet> transactions, double supportThreshold) {
        int k;
        System.out.println( "Finding frequent itemsets of length 1");
        List<ItemSet> frequentItemSets = generateFrequentItemSetsLevel1( transactions, supportThreshold );
        System.out.println( " found " + frequentItemSets.size() );

        List<ItemSet> result = new ArrayList<>();

        for (ItemSet itemSet : frequentItemSets) {
            result.add(itemSet.clone());
        }
        for (k = 1; frequentItemSets.size() > 0; k++) {
            System.out.println( "Finding frequent itemsets of length " + Integer.toString(k + 1));
            frequentItemSets = generateFrequentItemSets( supportThreshold, transactions, frequentItemSets );
            
            System.out.println( " found " + frequentItemSets.size() );

            for (ItemSet itemSet : frequentItemSets) {
                result.add(itemSet.clone());
            }
        }

        List<AssociationRule> assRules = createAssociationRules(result, transactions, supportThreshold);

        return new Apriori(result, assRules);
    }

    /**
     * Creates association rules.
     * @param frequentItemSets The frequent item set for which to create the association rules.
     * @param transactions All the transactions.
     * @return A list with all the association rules.
     */
    private static List<AssociationRule> createAssociationRules(List<ItemSet> frequentItemSets, List<ItemSet> transactions, double supportThreshold) {
        List<AssociationRule> assRules = new ArrayList<>();

        List<ItemSet> singleItemSetList = createSingleCandList(frequentItemSets);

        System.out.println(singleItemSetList.size());

        for (int i = 0; i < singleItemSetList.size(); i++) {
            ItemSet set1 = singleItemSetList.get(i);

            for (int j = 0; j < singleItemSetList.size(); j++) {
                //Skip to next set if the two sets are the same set.
                if (!set1.equals(singleItemSetList.get(j))) {
                    ItemSet set2 = singleItemSetList.get(j).clone();

                    //Combine the two sets
                    ItemSet comboSet = singleItemSetList.get(i).clone();
                    comboSet.set.addAll(set2.set);

                    //Calculate support
                    double support = countSupport(comboSet, transactions);

                    System.out.println(ItemSet.toString(comboSet) + " " + support);

                    if (support > supportThreshold) {
                        double set1Support = countSupport(set1, transactions);
                        double set2Support = countSupport(set2, transactions);

                        //Calculate confidence
                        double confidence = support / set1Support;

                        //Calculate correlation
                        double correlation = 0.5 * (support / set1Support + support / set2Support);

                        AssociationRule rule = new AssociationRule(set1.set.get(0), set2.set.get(0), support, correlation, confidence);

                        System.out.println(AssociationRule.toString(rule));

                        assRules.add(rule);
                    }
                }
            }
        }

        return assRules;
    }

    /**
    * Generates the itemSets for the first level.
    * @param transactions All the transactions.
    * @param supportThreshold The support threshold.
    * @return The itemSets for the first level.
    */
    private static List<ItemSet> generateFrequentItemSetsLevel1( List<ItemSet> transactions, double supportThreshold ) {
        List<ItemSet> frequentItemSets = createSingleCandList(transactions);

        // Remove the candidates with too low of a support.
        removeCandidates(frequentItemSets, transactions, supportThreshold);

        return frequentItemSets;
    }

    /**
     * Creates a list of all unique single item item sets from the transactions.
     * @param transactions The list with all the item sets.
     * @return A list of all unique single item item sets from the transactions.
     */
    private static List<ItemSet> createSingleCandList(List<ItemSet> transactions) {
        List<ItemSet> singleItemSetList = new ArrayList<>();

        // Find all the candidates
        for (ItemSet itemSet : transactions) {
            for (String i : itemSet.set) {
                ItemSet item = new ItemSet(new ArrayList<>());
                item.set.add(i);
                if (!singleItemSetList.contains(item)) singleItemSetList.add(item);
            }
        }

        return singleItemSetList;
    }

    /**
    * Generates the frequent ItemSets using the frequent itemSets from the previous level.
    * @param supportThreshold The supportThreshold
    * @param transactions All the transactions
    * @param lowerLevelItemSets The frequent ItemSets from the previous level.
    * @return The frequent ItemSets
    */
    private static List<ItemSet> generateFrequentItemSets( double supportThreshold, List<ItemSet> transactions,
                    List<ItemSet> lowerLevelItemSets ) {

        List<ItemSet> frequentItemSets = new ArrayList<>();

        for (int i = 0; i < lowerLevelItemSets.size() - 1; i++) {
            ItemSet itemSet1 = lowerLevelItemSets.get(i);

            for (int j = i + 1; j < lowerLevelItemSets.size(); j++) {
                ItemSet itemSet2 = lowerLevelItemSets.get(j).clone();

                if (itemSet1.exceptLastEquals(itemSet2)) {
                    ItemSet cand = itemSet1.clone();
                    String lastItemFromItemSet2 = itemSet2.set.get(itemSet2.set.size()-1);
                    cand.add(lastItemFromItemSet2);
                    frequentItemSets.add(cand);
                }
            }
        }

        // Remove the candidates with too low of a support.
        removeCandidates(frequentItemSets, transactions, supportThreshold);

        return frequentItemSets;
    }

    /**
     * Removes all the item sets that have a support that is too low.
     * @param frequentItemSets The list from which to remove the item sets.
     * @param transactions A list containing all the transactions.
     * @param supportThreshold The support threshold.
     */
    private static void removeCandidates(List<ItemSet> frequentItemSets, List<ItemSet> transactions, double supportThreshold) {
        ListIterator<ItemSet> it = frequentItemSets.listIterator();

        while (it.hasNext()) {
            ItemSet itemSet = it.next();
            double  support = countSupport(itemSet, transactions);

            if (support >= supportThreshold) {
                itemSet.support = support;
                it.set(itemSet);
            } else {
                it.remove();
            }
        }
    }

    /**
    * Takes two ItemSets which only differ in the last element.
    * @param first The first ItemSet
    * @param second The second ItemSet'
    * @return The join of the two ItemSets, where the length-2 element is the
    * last element from first and length-1 is the last elements from the second.
    */
    private static ItemSet joinSets( ItemSet first, ItemSet second ) {

        ItemSet joinedSet = new ItemSet(new ArrayList<>());

        for (int i = 0; i < first.set.size(); i++) {
            joinedSet.set.add(first.set.get(i));

            if (!first.set.get(i).equals(second.set.get(i))) joinedSet.set.add(second.set.get(i));
        }

        return joinedSet;
    }

    /**
    * Calculates the support in percentage for an itemSet in transactions.
    * @param itemSet The itemSet we want the support for.
    * @param transactions The transactions we are finding the support for.
    * @return returns the support in percentage.
    */
    private static double countSupport( ItemSet itemSet, List<ItemSet> transactions ) {
        // Assumes that items in ItemSets and transactions are both unique

        // The amount of occurances of the itemSet in transactions
        int count = 0;

        for (ItemSet transSet : transactions) {
            if (transSet.set.containsAll(itemSet.set)) count++;
        }

        return (double)count/(double)transactions.size();
    }
}
