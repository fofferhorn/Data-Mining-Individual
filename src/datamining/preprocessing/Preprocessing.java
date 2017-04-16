package datamining.preprocessing;

import datamining.datastructure.*;

import java.io.IOException;
import java.util.*;

public class Preprocessing {

    private static final String DATA_FILE = "resources/data.csv";

    public static void main(String[] args) {
        try {
            List<DataRow> data = preprocessData(DATA_FILE);
            printData(data);
        } catch (IOException e) {
            System.err.println(e.getMessage() + ": " + e.getStackTrace());
        }

    }

    /**
     * Preprocesses the data at a given path
     * @return The preprocessed data
     * @throws IOException Thrown if the data file cannot be found at the given path.
     */
    public static List<DataRow> preprocessData(String dataFilePath) throws IOException {
        // Read in the data. This might throw an IOException.
        String[][] dirtyData = CSVFileReader.readDataFile(dataFilePath,";", "-",false);

        List<DataRow> cleanData = cleanData(dirtyData);

        return cleanData;
    }

    private static void printData(List<DataRow> data) {
        System.out.println("The data set has " + data.size() + " data points.");

        ListIterator it = data.listIterator();

        while (it.hasNext()) {
            DataRow dataRow = (DataRow) it.next();
            System.out.println(DataRow.toString(dataRow));
        }
    }

    // Cleans the data
    private static List<DataRow> cleanData(String[][] data) {
        List<DataRow> dataList = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {

            data[i] = removeQuotationMarks(data[i]);

            // Correct age
            double age = correctAge(data[i][1]);

            // Correct genders
            DataRow.Gender gender = correctGender(data[i][2]);

            // Correct shoe size
            double shoeSize = correctShoeSize(data[i][3]);

            double height = correctHeight(data[i][4]);

            Person person = new Person(age, gender, shoeSize, height);

            // Correct programming languages
            ItemSet languages = correctProgrammingLanguages(data[i][7]);

            // Correct the interests
            StudentLabel studentClass = StudentLabel.guest;
            if (data[i][5].equals("SDT-AC")) studentClass = StudentLabel.ac;
            if (data[i][5].equals("SDT-DT")) studentClass = StudentLabel.dt;
            if (data[i][5].equals("DIM")) studentClass = StudentLabel.dim;
            if (data[i][5].equals("GAMES-T")) studentClass = StudentLabel.games;
            if (data[i][5].equals("Guest Student")) studentClass = StudentLabel.guest;
            if (data[i][5].equals("SWU")) studentClass = StudentLabel.swu;
            if (data[i][5].equals("SDT-SE")) studentClass = StudentLabel.se;

            String[] tmp = data[i][20].split(";");
            List<String> games = new ArrayList<>();
            for (String game : tmp) {
                games.add(game);
            }

            int design_databases = 0;
            int predictive_models = 0;
            int groups_similar_objects = 0;
            int visualization = 0;
            int patterns_sets = 0;
            int patterns_sequences = 0;
            int patterns_graphs = 0;
            int patterns_text = 0;
            int patterns_images = 0;
            int code_algorithms = 0;
            int use_off_the_shelf = 0;

            switch (data[i][9]) {
                case "Not interested": design_databases = 0; break;
                case "Meh": design_databases = 1; break;
                case "Sounds interesting": design_databases = 2; break;
                case "Very interesting": design_databases = 3; break;
            }

            switch (data[i][10]) {
                case "Not interested": predictive_models = 0; break;
                case "Meh": predictive_models = 1; break;
                case "Sounds interesting": predictive_models = 2; break;
                case "Very interesting": predictive_models = 3; break;
            }

            switch (data[i][11]) {
                case "Not interested": groups_similar_objects = 0; break;
                case "Meh": groups_similar_objects = 1; break;
                case "Sounds interesting": groups_similar_objects = 2; break;
                case "Very interesting": groups_similar_objects = 3; break;
            }

            switch (data[i][12]) {
                case "Not interested": visualization = 0; break;
                case "Meh": visualization = 1; break;
                case "Sounds interesting": visualization = 2; break;
                case "Very interesting": visualization = 3; break;
            }

            switch (data[i][13]) {
                case "Not interested": patterns_sets = 0; break;
                case "Meh": patterns_sets = 1; break;
                case "Sounds interesting": patterns_sets = 2; break;
                case "Very interesting": patterns_sets = 3; break;
            }

            switch (data[i][14]) {
                case "Not interested": patterns_sequences = 0; break;
                case "Meh": patterns_sequences = 1; break;
                case "Sounds interesting": patterns_sequences = 2; break;
                case "Very interesting": patterns_sequences = 3; break;
            }

            switch (data[i][15]) {
                case "Not interested": patterns_graphs = 0; break;
                case "Meh": patterns_graphs = 1; break;
                case "Sounds interesting": patterns_graphs = 2; break;
                case "Very interesting": patterns_graphs = 3; break;
            }

            switch (data[i][16]) {
                case "Not interested": patterns_text = 0; break;
                case "Meh": patterns_text = 1; break;
                case "Sounds interesting": patterns_text = 2; break;
                case "Very interesting": patterns_text = 3; break;
            }

            switch (data[i][17]) {
                case "Not interested": patterns_images = 0; break;
                case "Meh": patterns_images = 1; break;
                case "Sounds interesting": patterns_images = 2; break;
                case "Very interesting": patterns_images = 3; break;
            }

            switch (data[i][18]) {
                case "Not interested": code_algorithms = 0; break;
                case "Meh": code_algorithms = 1; break;
                case "Sounds interesting": code_algorithms = 2; break;
                case "Very interesting": code_algorithms = 3; break;
            }

            switch (data[i][19]) {
                case "Not interested": use_off_the_shelf = 0; break;
                case "Meh": use_off_the_shelf = 1; break;
                case "Sounds interesting": use_off_the_shelf = 2; break;
                case "Very interesting": use_off_the_shelf = 3; break;
            }

            Interest interest = new Interest(studentClass,
                    data[i][8],
                    design_databases,
                    predictive_models,
                    groups_similar_objects,
                    visualization,
                    patterns_sets,
                    patterns_sequences,
                    patterns_graphs,
                    patterns_text,
                    patterns_images,
                    code_algorithms,
                    use_off_the_shelf,
                    games);

            // Add the data we have just cleaned to the list with all the clean data.
            dataList.add(new DataRow(person, languages, interest));
        }

        return dataList;
    }

    /**
    * Removes all quotation marks from the data.
    * @param row The row from which to remove the quotation marks.
    * @return The row with all the quotation marks removed.
    */
    private static String[] removeQuotationMarks(String[] row) {
        for (int i = 0; i < row.length; i++) {
            row[i] = row[i].replace("\"", "");
        }
        return row;
    }

    /**
    * Finds the correct age for the given age string.
    * @param age The age to correct
    * @return The correct age. If the age is not valid "-" is returned.
    */
    private static double correctAge(String age) {
        // Non-valid input
        if (!isPositiveInteger(age)) return -1.0;
        if (age.length() > 2) return -1.0;

        // The rest of the input is accepted if it can be parsed to an int
        try {
            return Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    private static double correctHeight(String height) {
        // clean the data a bit
        height = height.toLowerCase();
        height = height.replace("cm", "");
        height = height.replace("m", "");

        // Try to parse the number. If it can be parsed just set it to a default value.
        double h;
        try {
            h = Double.parseDouble(height);
        } catch (NumberFormatException e) {
            return 100;
        }

        if (h < 100) {
            h = 100.0;
        } else if (h > 250) {
            h = 250.0;
        }

        return h;
    }

    /**
    * Checks whether or not the string is a positive integer.
    * @param str The string to check for positive integer.
    * @return True if the number is a positive integer. Else false.
    */
    private static boolean isPositiveInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
    * Finds the correct gender for the given gender string.
    * @param gender The gender to correct
    * @return The correct gender. If the gender is not valid "-" is returned.
    */
    private static DataRow.Gender correctGender(String gender) {
        switch (gender.toLowerCase()) {
            // All male cases
            case "boy":     return DataRow.Gender.MALE;
            case "man":     return DataRow.Gender.MALE;
            case "male":    return DataRow.Gender.MALE;
            case "m":       return DataRow.Gender.MALE;
            // All female cases
            case "girl":    return DataRow.Gender.FEMALE;
            case "woman":   return DataRow.Gender.FEMALE;
            case "female":  return DataRow.Gender.FEMALE;
            case "f":       return DataRow.Gender.FEMALE;
            // Default is to set the gender to apache hellicopter
            default:        return  DataRow.Gender.APACHE_HELICOPTER;
        }
    }

    /**
    * Corrects the shoe size.
    * @param size The size of the shoe.
    * @return The correct size of the shoe. If the shoe size is not valid "-" is returned.
    */
    private static double correctShoeSize(String size) {
        size = size.replace(",", ".");

        try {
            double s = Double.parseDouble(size);
            if (s < 0 || s > 60) return -1.0;
            return s;
        } catch (Exception ex) {
            return -1.0;
        }
    }

    /**
     * Creates an ItemSet from the list of programming languages.
     * @param languages The list with all the languages. Seperated by ";", "," or " "
     * @return An ItemSet with all the languages in it
     */
    private static ItemSet correctProgrammingLanguages(String languages) {
        // Clean all characters we don't wanna see in the languages
        languages = languages.toLowerCase();
        languages = languages.replaceAll(";", ",");
        languages = languages.replaceAll(" ", "");
        languages = languages.replaceAll("\\.", "");

        String[] lanArr = languages.split(",");

        List<String> lanList = new ArrayList<>();

        for (int i = 0; i < lanArr.length; i++) {
            //Remove all empty things in the array
            if (!lanArr[i].equals("")) {
                lanList.add(lanArr[i]);
            }
        }

        Collections.sort(lanList);

        return new ItemSet(lanList);
    }
}
