package datamining;

import datamining.clustering.kMean.KMeanCluster;
import datamining.clustering.kMean.KMeans;
import datamining.pattern_mining.apriori.*;
import datamining.classification.knn.*;
import datamining.datastructure.*;
import datamining.preprocessing.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

    private static final String DATA_FILE = "resources/data.csv";
    private static final double SUPPORT_THRESHOLD = 0.25;

    private static final int TRAINING_SIZE = 50;

    private static List<DataRow> data;

    public static void main(String[] args) {

        try {
            // Do preprocessing
            data = Preprocessing.preprocessData(DATA_FILE);

            // Do pattern mining
            System.out.println("################################################");
            System.out.println("PATTERN MINING");
            System.out.println("################################################");
            Apriori apriori = runApriori(SUPPORT_THRESHOLD);

            ItemSet.printFrequentItemSets(apriori.frequentItemSets, SUPPORT_THRESHOLD);

            for (AssociationRule rule : apriori.assRules) {
                System.out.println(AssociationRule.toString(rule));
            }

            System.out.println();

            // Do classification/supervised learning
            System.out.println("################################################");
            System.out.println("CLASSIFICATION/SUPERVISED LEARNING");
            System.out.println("################################################");
            for (int k = 1; k < 21; k++) {
                Map<Integer, StudentLabel> classification = runKNN(k);
                double correctness = checkClassification(classification);
                DecimalFormat df = new DecimalFormat("#%");

                System.out.println("The classification using knn with k = " + k + " had " + df.format(correctness) + " correct");
            }

            // Do clustering
            System.out.println("################################################");
            System.out.println("CLUSTERING");
            System.out.println("################################################");
            List<Person> personList = new ArrayList<>();
            for (DataRow row : data) {
                personList.add(row.person);
            }
            Map<Person, KMeanCluster> clustering = KMeans.KMeansPartition(3, personList);

            KMeans.printClustering(clustering);

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

    private static Map<Integer, StudentLabel> runKNN(int k) {
        List<Interest> trainData = new ArrayList<>();
        List<Interest> classifyData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i < TRAINING_SIZE) {
                trainData.add(data.get(i).interest);
            } else {
                classifyData.add(data.get(i).interest);
            }
        }

        return Knn.knnInterests(trainData, classifyData, k);
    }

    private static double checkClassification(Map<Integer, StudentLabel> classes) {
        int correct = 0;
        int counter = 0;
        for (Map.Entry<Integer, StudentLabel> entry : classes.entrySet()) {
            StudentLabel correctLabel = data.get(TRAINING_SIZE + counter).interest.studentLabel;
            StudentLabel assignedLabel = entry.getValue();
            if (correctLabel.equals(assignedLabel)) {
                correct++;
            }
            counter++;
        }

        return ((double)correct) / ((double) classes.size());
    }
}
