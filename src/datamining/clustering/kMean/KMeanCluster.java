package datamining.clustering.kMean;
import java.util.ArrayList;

import datamining.clustering.test.*;
import datamining.datastructure.DataRow;
import datamining.datastructure.Person;

public class KMeanCluster {

	public ArrayList<Person> ClusterMembers;
	
	public KMeanCluster()
	{
		this.ClusterMembers = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		String toPrintString = "-----------------------------------CLUSTER START------------------------------------------" + System.getProperty("line.separator");
		
		for(Person i : this.ClusterMembers)
		{
			toPrintString += Person.toString(i) + System.getProperty("line.separator");
		}
		toPrintString += "-----------------------------------CLUSTER END-------------------------------------------" + System.getProperty("line.separator");
		
		return toPrintString;
	}

	public Person clusterMean() {
		double ageSum = 0.0;
		double heightSum = 0.0;
		double shoeSizeSum = 0.0;
		int maleSum = 0;
		int femaleSum = 0;
		int apacheSum = 0;

		for (Person p : ClusterMembers) {
			ageSum += p.age;
			heightSum += p.height;
			shoeSizeSum += p.shoeSize;

			if (p.gender.equals(DataRow.Gender.MALE)) {
				maleSum++;
			} else if (p.gender.equals(DataRow.Gender.FEMALE)) {
				femaleSum++;
			} else if (p.gender.equals(DataRow.Gender.APACHE_HELICOPTER)) {
				apacheSum++;
			}
		}

		DataRow.Gender gender = DataRow.Gender.APACHE_HELICOPTER;
		if (maleSum >= femaleSum && maleSum >= apacheSum) {
			gender = DataRow.Gender.MALE;
		} else if (femaleSum >= maleSum && femaleSum >= apacheSum) {
			gender = DataRow.Gender.FEMALE;
		}

		return new Person(ageSum/ClusterMembers.size(), gender, shoeSizeSum/ClusterMembers.size(), heightSum/ClusterMembers.size());
	}
}
