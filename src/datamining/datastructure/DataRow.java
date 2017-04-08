package datamining.datastructure;

/**
 * Created by Christoffer on 08-04-2017.
 */
public class DataRow {
    public enum Gender {MALE, FEMALE, APACHE_HELICOPTER}

    public int age;
    public Gender gender;
    public double shoeSize;
    public ItemSet languages;

    public DataRow(int age,
                   Gender gender,
                   double shoeSize,
                   ItemSet languages) {
        this.age = age;
        this.gender = gender;
        this.shoeSize = shoeSize;
        this.languages = languages;
    }

    public static String toString(DataRow data) {
        return "[" + data.age + ", " + data.gender + ", " + data.shoeSize + ", (" + ItemSet.toString(data.languages) + ")]";
    }
}
