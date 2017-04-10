package datamining.classification.knn;

import datamining.classification.knn.enums.Class_Label;

import java.io.IOException;
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

    private static final int K = 4;

	public static void main(String[] args) {
		// First step - Load data and convert to Mushroom objects.
		List<Mushroom> mushrooms = DataManager.LoadData();
		System.out.println("DataManager loaded " + mushrooms.size() + " mushrooms");

        Map<Mushroom, Class_Label> mushMap = new HashMap<>();

        for (Mushroom m1 : mushrooms) {
            Map<Double, Class_Label> nn = new HashMap<>();

            // Find all the nearest neighbors for m1
            for (Mushroom m2 : mushrooms) {
                if (!m1.equals(m2)) {
                    double dist = mushroomDistance(m1, m2);

                    // Just add the first K mushrooms to the nearest neighbors
                    if (nn.size() < K) {
                        nn.put(dist, m2.m_Class);
                        continue;
                    }

                    Iterator it = nn.entrySet().iterator();

                    double highestDist = -1.0;

                    // Find the highest distance in for the nearest neighbors
                    while (it.hasNext()) {
                         Map.Entry pair = (Map.Entry) it.next();
                         if ((double) pair.getKey() > highestDist) {
                             highestDist = (double) pair.getKey();
                         }
                    }

                    // Replace the nearest neighbors with the distance with m2 if m2 is closer by
                    if (dist < highestDist) {
                        nn.remove(highestDist);
                        nn.put(dist, m2.m_Class);
                    }
                }
            }

            int pois = 0;
            int edib = 0;

            // Now find the correct class assignment for m1
            for (Map.Entry<Double, Class_Label> neighbor : nn.entrySet()) {
                if (neighbor.getValue().equals(Class_Label.edible)){
                    edib++;
                } else {
                    pois++;
                }
            }

            List<String> lines = new ArrayList<>();

            if (pois >= edib) {
                lines = Arrays.asList("e");
                System.out.println("This mushroom is edible");
            } else {
                lines = Arrays.asList("p");
                System.out.println("This mushroom is poisonous");
            }
            Path file = Paths.get("the-file-name.txt");
            try {
                Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
	}

    /**
     * Runs the k-nearest-neighbors algorithm.
     * @return A knn which can be used to classify new entries.
     */
	public static Knn knn() {
	    return new Knn();
    }

    /**
     * Classifies a new a new entry based on this kNN.
     */
    public void classifyNew() {

    }

    private static int mushroomDistance(Mushroom m1, Mushroom m2) {
        int dist = 0;

        if (m1.m_bruises != m2.m_bruises) dist++;
        if (m1.m_cap_color != m2.m_cap_color) dist++;
        if (m1.m_cap_shape != m2.m_cap_shape) dist++;
        if (m1.m_cap_surface != m2.m_cap_surface) dist++;
        if (m1.m_gill_attach != m2.m_gill_attach) dist++;
        if (m1.m_gill_color != m2.m_gill_color) dist++;
        if (m1.m_gill_size != m2.m_gill_size) dist++;
        if (m1.m_gill_spacing != m2.m_gill_spacing) dist++;
        if (m1.m_habitat != m2.m_habitat) dist++;
        if (m1.m_odor != m2.m_odor) dist++;
        if (m1.m_population != m2.m_population) dist++;
        if (m1.m_ring_number != m2.m_ring_number) dist++;
        if (m1.m_ring_type != m2.m_ring_type) dist++;
        if (m1.m_spore_color != m2.m_spore_color) dist++;
        if (m1.m_stalk_color_above != m2.m_stalk_color_above) dist++;
        if (m1.m_stalk_color_below != m2.m_stalk_color_below) dist++;
        if (m1.m_stalk_root != m2.m_stalk_root) dist++;
        if (m1.m_stalk_shape != m2.m_stalk_shape) dist++;
        if (m1.m_stalk_surface_above != m2.m_stalk_surface_above) dist++;
        if (m1.m_stalk_surface_below != m2.m_stalk_surface_below) dist++;
        if (m1.m_veil_color != m2.m_veil_color) dist++;
        if (m1.m_veil_type != m2.m_veil_type) dist++;

        return dist/22;
    }

}
