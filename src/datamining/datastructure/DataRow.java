package datamining.datastructure;

public class DataRow {
    public enum Gender {MALE, FEMALE, APACHE_HELICOPTER}

    public int age;
    public Gender gender;
    public double shoeSize;
    public ItemSet languages;
    public Interest interest;

    public DataRow(int age,
                   Gender gender,
                   double shoeSize,
                   ItemSet languages,
                   Interest interest) {
        this.age = age;
        this.gender = gender;
        this.shoeSize = shoeSize;
        this.languages = languages;
        this.interest = interest;
    }

    public static String toString(DataRow data) {
        return "[" + data.age + ", " + data.gender + ", " + data.shoeSize + ", " + ItemSet.toString(data.languages) + ", " + Interest.toString(data.interest) + "]";
    }
}
