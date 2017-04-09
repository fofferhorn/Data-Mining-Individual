package datamining.clustering.kMean;
import java.util.ArrayList;

import datamining.clustering.test.*;

public class KMeanCluster {

	public ArrayList<Iris> ClusterMembers;
	
	public KMeanCluster()
	{
		this.ClusterMembers = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		String toPrintString = "-----------------------------------CLUSTER START------------------------------------------" + System.getProperty("line.separator");
		
		for(Iris i : this.ClusterMembers)
		{
			toPrintString += i.toString() + System.getProperty("line.separator");
		}
		toPrintString += "-----------------------------------CLUSTER END-------------------------------------------" + System.getProperty("line.separator");
		
		return toPrintString;
	}

	public Iris clusterMean() {
		float sepalLengthSum = 0;
		float sepalWidthSum  = 0;
		float petalLengthSum = 0;
		float petalWidthSum  = 0;

		// For counting which is the most common iris in the cluster.
		int setosa     = 0;
		int versicolor = 0;
		int virginica  = 0;

		for (Iris i : this.ClusterMembers) {
			sepalLengthSum += i.Sepal_Length;
			sepalWidthSum  += i.Sepal_Width;
			petalLengthSum += i.Petal_Length;
			petalWidthSum  += i.Petal_Width;
			switch (i.Class) {
				case Iris_setosa: setosa++; break;
				case Iris_versicolor: versicolor++; break;
				case Iris_virginica: virginica++; break;
				default: break;
			} 
		}

		IrisClass irisClass;

		// Finding the most common iris in the cluster.
		if (setosa >= versicolor && setosa >= virginica) {
			irisClass = IrisClass.Iris_setosa;
		} else if (versicolor >= setosa && versicolor >= virginica) {
			irisClass = IrisClass.Iris_versicolor;
		} else {
			irisClass = IrisClass.Iris_virginica;
		}

		int n = this.ClusterMembers.size();

		Iris meanIris = new Iris(sepalLengthSum/n, sepalWidthSum/n, petalLengthSum/n, petalWidthSum/n, irisClass);

		return meanIris;
	}
}
