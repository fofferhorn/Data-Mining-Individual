package datamining.datastructure;

import java.util.List;
import java.util.ListIterator;

public class Interest {
    public StudentLabel studentLabel;
    public String phone_os;
    public int design_databases;
    public int predictive_models;
    public int groups_similar_objects;
    public int visualization;
    public int patterns_sets;
    public int patterns_sequences;
    public int patterns_graphs;
    public int patterns_text;
    public int patterns_images;
    public int code_algorithms;
    public int use_off_the_shelf;
    public List<String> games_played;

    public Interest(StudentLabel studentLabel,
                    String phone_os,
                    int design_databases,
                    int predictive_models,
                    int groups_similar_objects,
                    int visualization,
                    int patterns_sets,
                    int patterns_sequences,
                    int patterns_graphs,
                    int patterns_text,
                    int patterns_images,
                    int code_algorithms,
                    int use_off_the_shelf,
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
