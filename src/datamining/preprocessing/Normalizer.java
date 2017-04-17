package datamining.preprocessing;

import datamining.datastructure.DataRow;
import datamining.datastructure.Person;

import java.util.ArrayList;
import java.util.List;

public class Normalizer {

    /**
     * Normalizes the data
     * @param data
     * @return
     */
	public static List<DataRow> normalizeData(List<DataRow> data) {
        double maxAge = 0;
        double maxHeight = 0.0;
        double maxShoeSize = 0.0;

	    for (DataRow row : data) {
            if (row.person.age > maxAge) maxAge = row.person.age;
            if (row.person.height > maxHeight) maxHeight = row.person.height;
            if (row.person.shoeSize > maxShoeSize) maxShoeSize = row.person.shoeSize;
        }

        List<DataRow> normalizedData = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
	        DataRow row = data.get(i);

	        double normalizedAge = row.person.age/maxAge;
	        double normalizedHeight = row.person.height/maxHeight;
	        double normalizedShoeSize = row.person.shoeSize/maxShoeSize;

	        Person person = new Person(normalizedAge, row.person.gender, normalizedHeight, normalizedShoeSize);

	        normalizedData.add(new DataRow(person, row.languages, row.interest));
        }

        return normalizedData;
    }

}
