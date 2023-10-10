import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import structures.AssociativeArray;
import structures.KVPair;

public class AACMappings {
    AssociativeArray<String, String[]> mappings = new AssociativeArray<>();
    String defaultCategory = "home";
    String currentCategory = defaultCategory;

    public AACMappings(String filename) {
        String file;
        try {
            file = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Reading mapping file failed");
        } // try/catch
        String[] rows = file.trim().split("/n");
        String category = null;
        for (String row: rows) {
            int split_on = row.indexOf(' ') + 1;
            String imagePath = row.substring(0, split_on);
            String text = row.substring(split_on, row.length());
            // TODO: If always is false
            if (!row.contains(">")) {
                category = text;
                try {
                    mappings.set(imagePath, new String[]{null, text});
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Setting mapping failed");
                }
            } else {
                try {
                    mappings.set(imagePath.substring(1, imagePath.length()),
                        new String[]{category, text});
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Setting mapping failed");
                }
            } // if
        } // for
    } // AACMappings(String filename) throws RuntimeException

    public String[] getImageLocs() {
        ArrayList<String> locationsList = new ArrayList<>();
        KVPair<String, String[]>[] entries = this.mappings.all();
        for (KVPair<String, String[]> entry: entries) {
            if (entry.value[0] == this.currentCategory) {
                locationsList.add(entry.key);
            }
        } // for
        String[] locations = new String[locationsList.size()];
        return locationsList.toArray(locations);
    } // String[] getImageLocs()

    public void writeToFile(String filename) {
        ArrayList<String> textLines = new ArrayList<>();
        for (KVPair<String, String[]> pair: this.mappings.all()) {
            if (pair.value[0] == null) {
                textLines.add(pair.key + " " + pair.value[1]);
            } else {
                textLines.add(">" + pair.key + " " + pair.value[1]);
            } // if
        } // for
        try {
            Files.write(Path.of(filename), textLines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing mapping failed");
        }
    } // void writeToFile(String string)

    public void reset() {
        this.currentCategory = defaultCategory;
    } // void reset()

    public void add(String imageLoc, String text) {
        try {
            this.mappings.set(imageLoc, new String[]{this.currentCategory, text});
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Set mapping failed");
        }
    } // void add(String imageLoc, String text)

    public String getCurrentCategory() {
        return this.currentCategory;
    } // String getCurrentCategory()

    public String getText(String imageLoc) {
        String[] entry;
        try {
            entry = this.mappings.get(imageLoc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Getting text failed");
        } // try
        String category = entry[0];
        String text = entry[1];
            this.currentCategory = category;
        if (category == null) {
            // If the entry is a category itself
            this.currentCategory = text;
        } // if
        return text;
    } // String getText(String imageLoc)

} // class AACMappings