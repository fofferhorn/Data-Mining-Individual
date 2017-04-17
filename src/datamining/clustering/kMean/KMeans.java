package datamining.clustering.kMean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import datamining.datastructure.DataRow;
import datamining.datastructure.Person;


public class KMeans {

	public static Map<Person, KMeanCluster> KMeansPartition(int k, List<Person> data) {
        Map<Person, KMeanCluster> clustering = new HashMap<>();
        // Make the first k irises the centers for the clusters
        for (int i = 0; i < k; i++) {
            clustering.put(data.get(i), new KMeanCluster());
        }

        for (Person p : data) {
            double minDist = Double.MAX_VALUE;
            Person minCluster = new Person(0.0, DataRow.Gender.APACHE_HELICOPTER, 0.0, 0.0);
            for (Map.Entry<Person, KMeanCluster> cluster : clustering.entrySet()) {
                double dist = personDistance(p, cluster.getKey());

                if (dist < minDist) {
                    minDist = dist;
                    minCluster = cluster.getKey();
                }
            }

//            System.out.println("person: " + Person.toString(p) + " cluster: " + Person.toString(minCluster));

            clustering.get(minCluster).ClusterMembers.add(p);
        }

//        for (Map.Entry<Person, KMeanCluster> clust : clustering.entrySet()) {
//            System.out.println("\n\n\n");
//            System.out.println(Person.toString(clust.getKey()));
//            System.out.println(clust.getValue().toString());
//        }

        Map<Person, KMeanCluster> newClustering = clustering;
        Map<Person, KMeanCluster> oldClustering;

        boolean oldEqualsNew = false;

        while (!oldEqualsNew) {
//            System.out.println("-------------------------------------------------------------------------");
            oldClustering = newClustering;

//            for (Map.Entry<Person, KMeanCluster> clust : oldClustering.entrySet()) {
//                System.out.println("\n\n\n");
//                System.out.println(Person.toString(clust.getKey()));
//                System.out.println(clust.getValue().toString());
//            }


            // find centers of clusters
            newClustering = new HashMap<>();
            for (Map.Entry<Person, KMeanCluster> cluster : oldClustering.entrySet()) {
                Person center = cluster.getValue().clusterMean();
                newClustering.put(center, new KMeanCluster());
            }

            // fill data into the clusters
            for (Person p : data) {
                double minDist = Double.MAX_VALUE;
                Person minCluster = new Person(0.0, DataRow.Gender.APACHE_HELICOPTER, 0.0, 0.0);
                for (Map.Entry<Person, KMeanCluster> cluster : newClustering.entrySet()) {
                    double dist = personDistance(p, cluster.getKey());

                    if (dist < minDist) {
                        minDist = dist;
                        minCluster = cluster.getKey();
                    }
                }
                newClustering.get(minCluster).ClusterMembers.add(p);
            }

            // Check if the two clusterings are the same now
            oldEqualsNew = true;
            for (Map.Entry<Person, KMeanCluster> n : newClustering.entrySet()) {
                KMeanCluster o = oldClustering.get(n.getKey());
                if (o == null) {
                    oldEqualsNew = false;
                    break;
                } else {
                    if (n.getValue().ClusterMembers.size() != o.ClusterMembers.size()) {
                        oldEqualsNew = false;
                        break;
                    }
                    for (int i = 0; i < o.ClusterMembers.size(); i++) {
                        Person p1 = n.getValue().ClusterMembers.get(i);
                        Person p2 = o.ClusterMembers.get(i);

                        if (!p1.equals(p2)) {
                            oldEqualsNew = false;
                            break;
                        }
                    }
                    if (!oldEqualsNew) break;

                    if (!n.getValue().ClusterMembers.equals(o.ClusterMembers)) {
                        oldEqualsNew = false;
                        break;
                    }
                }
            }

            try {
                PrintWriter writer = new PrintWriter("resources/gender.txt", "UTF-8");
                for (Map.Entry<Person, KMeanCluster> n : newClustering.entrySet()) {
                    writer.println(Person.toString(n.getKey()));
                    for (Person p : n.getValue().ClusterMembers) {
                        writer.println("\t\t" + Person.toString(p));
                    }
                }
                writer.close();
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }

		return newClustering;
	}

    /**
     * Calculates the distance between two persons.
     * @param p1
     * @param p2
     * @return
     */
    private static double personDistance(Person p1, Person p2) {
	    double dist = 0.0;

	    dist += Math.pow(Math.abs(p1.age - p2.age), 2);
	    dist += Math.pow(Math.abs(p1.height - p2.height), 2);
	    dist += Math.pow(Math.abs(p1.shoeSize - p2.shoeSize), 2);
	    //if (!p1.gender.equals(p2.gender)) dist++;

	    return Math.sqrt(dist);
    }

    /**
     * Prints a clustering
     * @param clustering
     */
    public static void printClustering(Map<Person, KMeanCluster> clustering) {
	    for (Map.Entry<Person, KMeanCluster> cluster : clustering.entrySet()) {
            System.out.println("----------------------------------------------------------------------");
            System.out.println(Person.toString(cluster.getKey()));
            for (Person p : cluster.getValue().ClusterMembers) {
                System.out.println("\t" + Person.toString(p));
            }
            System.out.println("----------------------------------------------------------------------");
        }
    }
}
