package datamining.clustering.kMean;
import java.util.*;

import datamining.clustering.test.*;
import datamining.datastructure.DataRow;
import datamining.datastructure.Person;


public class KMeans {

	public static Map<Person, KMeanCluster> KMeansPartition(int k, List<Person> data)
	{
        Map<Person, KMeanCluster> clustering = new HashMap<>();
        // Make the first k irises the centers for the clusters
        for (int i = 0; i < k; i++) {
            clustering.put(data.get(i), new KMeanCluster());
        }

        for (Person p : data) {
            double minDist = -1.0;
            Person minCluster = new Person(0.0, DataRow.Gender.APACHE_HELICOPTER, 0.0, 0.0);
            for (Map.Entry<Person, KMeanCluster> cluster : clustering.entrySet()) {
                double dist = personDistance(p, cluster.getKey());

                if (dist > minDist) {
                    minDist = dist;
                    minCluster = cluster.getKey();
                }
            }
            clustering.get(minCluster).ClusterMembers.add(p);
        }

        Map<Person, KMeanCluster> newClustering = new HashMap<>();
        Map<Person, KMeanCluster> oldClustering = clustering;

        while (!oldClustering.equals(newClustering)) {
            oldClustering = newClustering;
            // find centers of clusters
            newClustering = new HashMap<>();
            for (Map.Entry<Person, KMeanCluster> cluster : oldClustering.entrySet()) {
                Person center = cluster.getValue().clusterMean();
                newClustering.put(center, new KMeanCluster());
            }

            // fill data into the clusters
            for (Person p : data) {
                double minDist = -1.0;
                Person minCluster = new Person(0.0, DataRow.Gender.APACHE_HELICOPTER, 0.0, 0.0);
                for (Map.Entry<Person, KMeanCluster> cluster : newClustering.entrySet()) {
                    double dist = personDistance(p, cluster.getKey());

                    if (dist > minDist) {
                        minDist = dist;
                        minCluster = cluster.getKey();
                    }
                }
                newClustering.get(minCluster).ClusterMembers.add(p);
            }
        }

		return newClustering;
	}

    private static double personDistance(Person p1, Person p2) {
	    double dist = 0.0;

	    dist += Math.pow(Math.abs(p1.age - p2.age), 2);
	    dist += Math.pow(Math.abs(p1.height - p2.height), 2);
	    dist += Math.pow(Math.abs(p1.shoeSize - p2.shoeSize), 2);
	    if (!p1.gender.equals(p2.gender)) dist++;

	    return Math.sqrt(dist);
    }

    public static void printClustering(Map<Person, KMeanCluster> clustering) {
	    for (Map.Entry<Person, KMeanCluster> cluster : clustering.entrySet()) {
            System.out.println(Person.toString(cluster.getKey()));
            for (Person p : cluster.getValue().ClusterMembers) {
                System.out.println("\t" + Person.toString(p));
            }
        }
    }
}
