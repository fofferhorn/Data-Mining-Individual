package datamining.classification.knn;

import datamining.classification.knn.enums.Class_Label;
import datamining.datastructure.Interest;
import datamining.datastructure.StudentLabel;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Knn class to run program from.
 */
public class Knn {

    private static final int K = 3;

    public static class MushroomDistance {
        public int dist;
        public Mushroom mushroom;

        public MushroomDistance(int dist, Mushroom mush) {
            this.dist = dist;
            this.mushroom = mush;
        }
    }

    public static class InterestDistance {
        public double dist;
        public Interest interest;

        public InterestDistance(double dist, Interest interest) {
            this.dist = dist;
            this.interest = interest;
        }
    }

	public static void main(String[] args) {
		// First step - Load data and convert to Mushroom objects.
		List<Mushroom> mushrooms = DataManager.LoadData();
		System.out.println("DataManager loaded " + mushrooms.size() + " mushrooms");

        Map<Integer, Class_Label> mushMap = new HashMap<>();

        try{
            PrintWriter writer = new PrintWriter("resources/mushrooms.txt", "UTF-8");

            for (Mushroom m1 : mushrooms) {
                List<MushroomDistance> nn = new ArrayList<>();

                // Find all the nearest neighbors for m1
                for (Mushroom m2 : mushrooms) {
                    if (!m1.equals(m2)) {
                        int dist = mushroomDistance(m1, m2);

                        MushroomDistance mush = new MushroomDistance(dist, m2);

                        // Just add the first K mushrooms to the nearest neighbors
                        if (nn.size() < K) {
                            nn.add(mush);
                            continue;
                        }

                        Iterator it = nn.listIterator();

                        MushroomDistance highestDistanceMushroom = new MushroomDistance(-1, null);

                        // Find the highest distance in for the nearest neighbors
                        while (it.hasNext()) {
                            MushroomDistance listMush = (MushroomDistance) it.next();
                            if (listMush.dist > highestDistanceMushroom.dist) {
                                highestDistanceMushroom = listMush;
                            }
                        }

                        // Replace the nearest neighbors with the distance with m2 if m2 is closer by
                        if (dist < highestDistanceMushroom.dist) {
                            nn.remove(highestDistanceMushroom);
                            nn.add(mush);
                        }
                    }
                }

                int pois = 0;
                int edib = 0;

                // Now find the correct class assignment for m1
                for (MushroomDistance neighbor : nn) {
                    if (neighbor.mushroom.m_Class.equals(Class_Label.edible)) {
                        edib++;
                    } else {
                        pois++;
                    }
                }

                if (pois >= edib) {
                    mushMap.put(1, Class_Label.poisonous);
                    writer.println("e");
                    System.out.println("This mushroom is poisonous");
                } else {
                    mushMap.put(1, Class_Label.poisonous);
                    writer.println("p");
                    System.out.println("This mushroom is edible");
                }
            }

            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
	}

    public static Map<Integer, StudentLabel> knnInterests(List<Interest> trainData, List<Interest> classifyData, int k) {
        Map<Integer, StudentLabel> interestMap = new HashMap<>();

        try{
            PrintWriter writer = new PrintWriter("resources/students.txt", "UTF-8");

            int counter = 0;

            for (Interest i1 : classifyData) {
                List<InterestDistance> nn = new ArrayList<>();

                // Find all the nearest neighbors for i1
                for (Interest i2 : trainData) {
                    double dist = interestDistance(i1, i2);

                    InterestDistance interest = new InterestDistance(dist, i2);

                    // Just add the first K interests to the nearest neighbors
                    if (nn.size() < k) {
                        nn.add(new InterestDistance(dist, i2));
                        continue;
                    }

                    Iterator it = nn.listIterator();

                    InterestDistance highestDistanceInterest = new InterestDistance(-1, null);

                    // Find the highest distance in for the nearest neighbors
                    while (it.hasNext()) {
                        InterestDistance listInterest = (InterestDistance) it.next();
                        if (listInterest.dist > highestDistanceInterest.dist) {
                            highestDistanceInterest = listInterest;
                        }
                    }

                    // Replace the nearest neighbors with the distance with i2 if i2 is closer by
                    if (dist < highestDistanceInterest.dist) {
                        nn.remove(highestDistanceInterest);
                        nn.add(interest);
                    }
                }

                int ac = 0;
                int dt = 0;
                int games = 0;
                int dim = 0;
                int se = 0;
                int swu = 0;
                int guest = 0;

                // Now find the correct class assignment for i1
                for (InterestDistance neighbor : nn) {
                    if (neighbor.interest.studentLabel.equals(StudentLabel.ac)) {
                        ac++;
                    } else if (neighbor.interest.studentLabel.equals(StudentLabel.dt)) {
                        dt++;
                    } else if (neighbor.interest.studentLabel.equals(StudentLabel.games)) {
                        games++;
                    } else if (neighbor.interest.studentLabel.equals(StudentLabel.dim)) {
                        dim++;
                    } else if (neighbor.interest.studentLabel.equals(StudentLabel.se)) {
                        se++;
                    } else if (neighbor.interest.studentLabel.equals(StudentLabel.swu)) {
                        swu++;
                    } else if (neighbor.interest.studentLabel.equals(StudentLabel.guest)) {
                        guest++;
                    }
                }

                if (ac >= dt && ac >= games && ac >= dim && ac >= se && ac >= swu && ac >= guest) {
                    interestMap.put(counter, StudentLabel.ac);
                    writer.println((51 + counter) + " AC");
                } else if (dt >= ac && dt >= games && dt >= dim && dt >= se && dt >= swu && dt >= guest) {
                    interestMap.put(counter, StudentLabel.dt);
                    writer.println((51 + counter) + " DT");
                } else if (games >= ac && games >= dt && games >= dim && games >= se && games >= swu && games >= guest) {
                    interestMap.put(counter, StudentLabel.games);
                    writer.println((51 + counter) + " games");
                } else if (dim >= ac && dim >= dt && dim >= games && dim >= se && dim >= swu && dim >= guest) {
                    interestMap.put(counter, StudentLabel.dim);
                    writer.println((51 + counter) + " dim");
                } else if (se >= ac && se >= dt && se >= dim && se >= games && se >= swu && se >= guest) {
                    interestMap.put(counter, StudentLabel.se);
                    writer.println((51 + counter) + " SE");
                } else if (swu >= ac && swu >= dt && swu >= dim && swu >= se && swu >= games && swu >= guest) {
                    interestMap.put(counter, StudentLabel.swu);
                    writer.println((51 + counter) + " SWU");
                } else {
                    interestMap.put(counter, StudentLabel.guest);
                    writer.println((51 + counter) + " guest");
                }

                counter++;

            }
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return interestMap;
    }

    private static int mushroomDistance(Mushroom m1, Mushroom m2) {
        int dist = 0;

        if (!m1.m_bruises.equals(m2.m_bruises)) dist++;
        if (!m1.m_cap_color.equals(m2.m_cap_color)) dist++;
        if (!m1.m_cap_shape.equals(m2.m_cap_shape)) dist++;
        if (!m1.m_cap_surface.equals(m2.m_cap_surface)) dist++;
        if (!m1.m_gill_attach.equals(m2.m_gill_attach)) dist++;
        if (!m1.m_gill_color.equals(m2.m_gill_color)) dist++;
        if (!m1.m_gill_size.equals(m2.m_gill_size)) dist++;
        if (!m1.m_gill_spacing.equals(m2.m_gill_spacing)) dist++;
        if (!m1.m_habitat.equals(m2.m_habitat)) dist++;
        if (!m1.m_odor.equals(m2.m_odor)) dist++;
        if (!m1.m_population.equals(m2.m_population)) dist++;
        if (!m1.m_ring_number.equals(m2.m_ring_number)) dist++;
        if (!m1.m_ring_type.equals(m2.m_ring_type)) dist++;
        if (!m1.m_spore_color.equals(m2.m_spore_color)) dist++;
        if (!m1.m_stalk_color_above.equals(m2.m_stalk_color_above)) dist++;
        if (!m1.m_stalk_color_below.equals(m2.m_stalk_color_below)) dist++;
        if (!m1.m_stalk_root.equals(m2.m_stalk_root)) dist++;
        if (!m1.m_stalk_shape.equals(m2.m_stalk_shape)) dist++;
        if (!m1.m_stalk_surface_above.equals(m2.m_stalk_surface_above)) dist++;
        if (!m1.m_stalk_surface_below.equals(m2.m_stalk_surface_below)) dist++;
        if (!m1.m_veil_color.equals(m2.m_veil_color)) dist++;
        if (!m1.m_veil_type.equals(m2.m_veil_type)) dist++;

        return dist;
    }


    private static double interestDistance(Interest i1, Interest i2) {
        double dist = 0;

        if (!i1.code_algorithms.equals(i2.code_algorithms)) dist++;
        if (!i1.design_databases.equals(i2.design_databases)) dist++;
        if (!i1.groups_similar_objects.equals(i2.groups_similar_objects)) dist++;
        if (!i1.patterns_graphs.equals(i2.patterns_graphs)) dist++;
        if (!i1.patterns_images.equals(i2.patterns_images)) dist++;
        if (!i1.patterns_sequences.equals(i2.patterns_sequences)) dist++;
        if (!i1.patterns_sets.equals(i2.patterns_sets)) dist++;
        if (!i1.patterns_text.equals(i2.patterns_text)) dist ++;
        if (!i1.phone_os.equals(i2.phone_os)) dist++;
        if (!i1.predictive_models.equals(i2.predictive_models)) dist++;
        if (!i1.visualization.equals(i2.visualization)) dist++;
        if (!i1.use_off_the_shelf.equals(i2.use_off_the_shelf)) dist++;

        for (String game : i1.games_played) {
            if (!i2.games_played.contains(game)) dist++;
        }

        for (String game : i2.games_played) {
            if (!i1.games_played.contains(game)) dist++;
        }

        return dist;
    }

}
