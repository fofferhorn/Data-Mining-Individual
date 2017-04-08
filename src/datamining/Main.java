package datamining;

import datamining.apriori.*;
import datamining.datastructure.*;
import datamining.preprocessing.*;

import java.io.IOException;
import java.util.*;

public class Main {

    private static final String DATA_FILE = "resources/data.csv";
    private static final double SUPPORT_THRESHOLD = 0.30;

    private static List<DataRow> data;

    public static void main(String[] args) {
        try {
            data = Preprocessing.preprocessData(DATA_FILE);

            Apriori apriori = runApriori(SUPPORT_THRESHOLD);

            Apriori.prettyPrintFrequentItemSets(apriori.frequentItemSets, SUPPORT_THRESHOLD);

            for (AssociationRule rule : apriori.assRules) {
                System.out.println(AssociationRule.toString(rule));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Apriori runApriori(double supportThreshold) {
        List<ItemSet> transactions = new ArrayList<>();

        ListIterator it = data.listIterator();

        while (it.hasNext()) {

            ItemSet itemSet = ((DataRow) it.next()).languages;

            transactions.add(itemSet);
        }

        return Apriori.apriori(transactions, supportThreshold);
    }
}
