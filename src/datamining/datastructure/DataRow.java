package datamining.datastructure;

public class DataRow {
    public enum Gender {MALE, FEMALE, APACHE_HELICOPTER}

    public Person person;
    public ItemSet languages;
    public Interest interest;

    public DataRow(Person person,
                   ItemSet languages,
                   Interest interest) {
        this.person = person;
        this.languages = languages;
        this.interest = interest;
    }

    public static String toString(DataRow data) {
        return "[(" + data.person.gender + ", " + data.person.age + ", " + data.person.height + ", " + data.person.shoeSize + "), " + ItemSet.toString(data.languages) + ", " + Interest.toString(data.interest) + "]";
    }
}
