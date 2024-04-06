// RepurposingSuggestionsManager.java
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Manage repurposing suggestions including storage, retrieval, and searching
 */
public class RePurposingSuggestionsManager {
    private static List<RePurposingSuggestion> suggestions;
    private File dataFile;

    /**
     * Constructs a RepurposingSuggestionsManager with the specified filename
     * @param filename the name of the file to load suggestions from
     */
    public RePurposingSuggestionsManager(String filename) {
        suggestions = new ArrayList<>();
        dataFile = new File(filename);
    }

    // Load suggestions from file

    /**
     *
     */
    public void loadSuggestions() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Line read from file: " + line); // Debugging statement
            String[] parts = line.split(";");
            System.out.println("Number of parts: " + parts.length); // Debugging statement
            RePurposingSuggestion suggestion = new RePurposingSuggestion(parts[0], parts[1], parts[2], parts[3]);
            suggestions.add(suggestion);
        }
        reader.close();
    }

    /**
     * Other methods for adding, removing, searching suggestions
     * Save suggestions to file
     * @throws IOException
     */
    public void saveSuggestions() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
        for (RePurposingSuggestion suggestion : suggestions) {
            writer.write(suggestion.getTitle() + ";" + suggestion.getImageLocation() + ";" +
                    suggestion.getWebLink() + ";" + suggestion.getPrimaryMaterials() + ";" +
                    suggestion.getConstructionHints() + "\n");
        }
        writer.close();
    }


    /**
     * Add a new suggestion to manager
     * @param suggestion the suggestion to add
     */
    public void addSuggestion(RePurposingSuggestion suggestion) {

        suggestions.add(suggestion);
        suggestions.sort(Comparator.comparing(RePurposingSuggestion::getTitle)); // sort suggestions by idea title
    }

    /**
     * Searches for a suggestion by idea title using binary search
     * @param title the title of the suggestion to search for
     * @return the suggestion with the specified title, or null if not found
     */
    public RePurposingSuggestion searchByTitleBinary(String title){
        int index = binarySearchByTitle(title);
        return index != -1 ? suggestions.get(index) : null;
    }

    private int binarySearchByTitle(String title) {
        int low = 0;
        int high = suggestions.size() - 1;

        while (low <= high) {
            int mid = low + (high - low)/2;
            RePurposingSuggestion midSuggestion = suggestions.get(mid);
            int cmp = midSuggestion.getTitle().compareTo(title);

            if(cmp == 0){
                return mid;
            } else if (cmp < 0) {
                low = mid + 1; // search
            } else {
                high = mid -1;
            }
        }
        return -1;
    }

    // Remove a suggestion
    public boolean removeSuggestion(RePurposingSuggestion suggestion) {
        return suggestions.remove(suggestion);
    }

    // Search for suggestions by title
    public static List<RePurposingSuggestion> searchByTitle(String title) {
        List<RePurposingSuggestion> results = new ArrayList<>();
        for (RePurposingSuggestion suggestion : suggestions) {
            if (suggestion.getTitle().equalsIgnoreCase(title)) {
                results.add(suggestion);
            }
        }
        return results;
    }

    /**
     * Search for suggestions by primary material
     * @param material
     * @return
     */
    public List<RePurposingSuggestion> searchByPrimaryMaterial(String material) {
        List<RePurposingSuggestion> results = new ArrayList<>();
        for (RePurposingSuggestion suggestion : suggestions) {
            if (suggestion.getPrimaryMaterials().toLowerCase().contains(material.toLowerCase())) {
                results.add(suggestion);
            }
        }
        return results;
    }

    public List<RePurposingSuggestion> getAllSuggestions() {
        return Collections.unmodifiableList(suggestions);
    }
    static void displayAllSuggestions(RePurposingSuggestionsManager manager) {
        List<RePurposingSuggestion> suggestions = manager.getAllSuggestions();
        if (suggestions.isEmpty()) {
            System.out.println("No suggestions found.");
        } else {
            System.out.println("All Suggestions:");
            for (RePurposingSuggestion suggestion : suggestions) {
                System.out.println(suggestion);
            }
        }
    }

    static void searchByTitle(RePurposingSuggestionsManager manager, BufferedReader reader) throws IOException {
        System.out.print("Enter the title to search: ");
        String title = reader.readLine();
        List<RePurposingSuggestion> suggestions = searchByTitle(title);
        if (suggestions.isEmpty()) {
            System.out.println("No suggestions found with the title '" + title + "'.");
        } else {
            System.out.println("Suggestions with the title '" + title + "':");
            for (RePurposingSuggestion suggestion : suggestions) {
                System.out.println(suggestion);
            }
        }
    }

    static void searchByMaterial(RePurposingSuggestionsManager manager, BufferedReader reader) throws IOException {
        System.out.print("Enter the primary material to search: ");
        String material = reader.readLine();
        List<RePurposingSuggestion> suggestions = manager.searchByPrimaryMaterial(material);
        if (suggestions.isEmpty()) {
            System.out.println("No suggestions found with the primary material '" + material + "'.");
        } else {
            System.out.println("Suggestions with the primary material '" + material + "':");
            for (RePurposingSuggestion suggestion : suggestions) {
                System.out.println(suggestion);
            }
        }
    }

}
