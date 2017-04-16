package datamining.datastructure;


public class Person {
    public double age;
    public DataRow.Gender gender;
    public double shoeSize;
    public double height;

    public Person(double age, DataRow.Gender gender, double shoeSize, double height) {
        this.age = age;
        this.gender = gender;
        this.shoeSize = shoeSize;
        this.height = height;
    }

    public static String toString(Person p) {
        return "(" + p.gender + ", " + p.age + ", " + p.height + ", " + p.shoeSize + ")";
    }
}
