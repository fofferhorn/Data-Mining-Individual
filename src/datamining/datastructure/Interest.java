package datamining.datastructure;

import java.util.List;
import java.util.ListIterator;

public class Interest {
    public StudentLabel studentLabel;
    public String phone_os;
    public String design_databases;
    public String predictive_models;
    public String groups_similar_objects;
    public String visualization;
    public String patterns_sets;
    public String patterns_sequences;
    public String patterns_graphs;
    public String patterns_text;
    public String patterns_images;
    public String code_algorithms;
    public String use_off_the_shelf;
    public List<String> games_played;

    public Interest(StudentLabel studentLabel,
            String phone_os,
            String design_databases,
            String predictive_models,
            String groups_similar_objects,
            String visualization,
            String patterns_sets,
            String patterns_sequences,
            String patterns_graphs,
            String patterns_text,
            String patterns_images,
            String code_algorithms,
            String use_off_the_shelf,
            List<String> games_played) {
        this.studentLabel = studentLabel;
        this.phone_os = phone_os;
        this.design_databases = design_databases;
        this.predictive_models = predictive_models;
        this.groups_similar_objects = groups_similar_objects;
        this.visualization = visualization;
        this.patterns_sets = patterns_sets;
        this.patterns_sequences = patterns_sequences;
        this.patterns_graphs = patterns_graphs;
        this.patterns_text = patterns_text;
        this.patterns_images = patterns_images;
        this.code_algorithms = code_algorithms;
        this.use_off_the_shelf = use_off_the_shelf;
        this.games_played = games_played;
    }

    public static String toString(Interest interest) {
        String result = "[" +
                interest.studentLabel + ", " +
                interest.phone_os + ", " +
                interest.design_databases + ", " +
                interest.predictive_models + ", " +
                interest.groups_similar_objects + ", " +
                interest.visualization + ", " +
                interest.patterns_sets + ", " +
                interest.patterns_sequences + ", " +
                interest.patterns_graphs + ", " +
                interest.patterns_text + ", " +
                interest.patterns_images + ", " +
                interest.code_algorithms + ", " +
                interest.use_off_the_shelf + ", (";

        ListIterator it = interest.games_played.listIterator();

        while (it.hasNext()) {
            String item = (String) it.next();

            result += item;
            if (it.hasNext()) result += ", ";
        }

        return result + ")]";
    }
}
