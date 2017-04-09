package datamining.apriori;

import java.text.DecimalFormat;
import java.util.List;

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
        DecimalFormat df = new DecimalFormat("0.00");

        return rule.known + " => " + rule.implied +
                "\t[" +
                "support = " + df.format(rule.support) + ", " +
                "confidence = " + df.format(rule.confidence) + ", " +
                "correlation = " + df.format(rule.correlation) +
                "]";
    }

    /**
     * Prints the association rules.
     * @param ruleList A list containing the association rules to be printed.
     */
    public static void printAssociationRules(List<AssociationRule> ruleList) {
        System.out.println("The association rules are:");

        for (AssociationRule rule : ruleList) {
            System.out.println(AssociationRule.toString(rule));
        }

        System.out.println();
    }
}
