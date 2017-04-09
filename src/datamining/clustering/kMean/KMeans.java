package datamining.clustering.kMean;
import java.util.*;

import datamining.clustering.test.*;


public class KMeans {

	public static ArrayList<KMeanCluster> KMeansPartition(int k, ArrayList<Iris> data)
	{
        HashMap<Iris, ArrayList<Iris>> clustering = new HashMap<>();
        // Make the first k irises the centers for the clusters
        for (int i = 0; i < k; i++) {
            clustering.put(data.get(i), new ArrayList<>());
        }



		return null;
		
	}

}
