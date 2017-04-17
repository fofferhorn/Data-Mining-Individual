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
        return p.gender + "\t" + p.age + "\t" + p.height + "\t" + p.shoeSize;
    }

    public boolean equals(Person p) {
        if (this.age == p.age && this.gender == p.gender && this.shoeSize == p.shoeSize && this.height == p.height) return true;
        return false;
    }
}
