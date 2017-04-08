package datamining.apriori;

/**
 * Created by Christoffer on 08-04-2017.
 */
public class AssociationRule {
    public String known;
    public String implied;
    public double support;
    public double correlation;
    public double confidence;

    public AssociationRule(String known, String implied, double support, double correlation, double confidence) {
        this.known = known;
        this.implied = implied;
        this.support = support;
        this.correlation = correlation;
        this.confidence = confidence;
    }

    public static String toString(AssociationRule rule) {
        return rule.known + " => " + rule.implied + "[support = " + rule.support + ", confidence = " + rule.confidence + ", correlation = " + rule.correlation + "]";
    }
}
