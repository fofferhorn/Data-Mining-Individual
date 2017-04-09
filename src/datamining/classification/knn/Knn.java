package datamining.classification.knn;

import java.util.ArrayList;

/**
 * Knn class to run program from.
 */
public class Knn {



	public static void main(String[] args) {
		// First step - Load data and convert to Mushroom objects.
		ArrayList<Mushroom> mushrooms = DataManager.LoadData();
		System.out.println("DataManager loaded "+mushrooms.size() + " mushrooms");


	}

    /**
     * Runs the k-nearest-neighbors algorithm.
     * @return A knn which can be used to classify new entries.
     */
	public static Knn knn() {
	    return new Knn();
    }

    /**
     * Classifies a new a new entry based on this kNN.
     */
    public void classifyNew() {

    }

}
