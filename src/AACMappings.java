import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import structures.AssociativeArray;
import structures.KVPair;

public class AACMappings {
    Path mappingsFilePath = Path.of("../AACMappings.txt");
    AssociativeArray<String, String[]> mappings = new AssociativeArray<>();
    String defaultCategory = "home";
    String currentCategory = defaultCategory;

    public AACMappings(String filename) throws Exception {
        String file;
        try {
            file = Files.readString(mappingsFilePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } // try/catch
        String[] rows = file.split("/n");
        String category = null;
        for (String row: rows) {
            int split_on = row.indexOf(' ') + 1;
            String imagePath = row.substring(0, split_on);
            String text = row.substring(split_on, row.length());
            if (!row.contains(">")) {
                category = text;
                mappings.set(imagePath, new String[]{null, text});
            } else {
                mappings.set(imagePath.substring(1, imagePath.length()),
                    new String[]{category, text});
            } // if
        } // for
    } // AACMappings(String filename) throws RuntimeException

    public String[] getImageLocs() {
        return new String[] { "img/food/icons8-french-fries-96.png", "img/food/icons8-watermelon-96.png" };
    }

    public void writeToFile(String string) throws IOException {
        ArrayList<String> textLines = new ArrayList<>();
        for (KVPair<String, String[]> pair: this.mappings.all()) {
            if (pair.value[0] == null) {
                textLines.add(pair.key + " " + pair.value[1]);
            } else {
                textLines.add(">" + pair.key + " " + pair.value[1]);
            } // if
        } // for
        Files.write(mappingsFilePath, textLines, StandardCharsets.UTF_8);
    } // void writeToFile(String string)

    public void reset() {
        this.currentCategory = defaultCategory;
    }

    public void add(String imageLoc, String text) throws Exception {
        this.mappings.set(imageLoc, new String[]{this.currentCategory, text});
    }

    public String getCurrentCategory() {
        return this.currentCategory;
    }

    public String getText(String imageLoc) throws Exception {
        String[] entry = this.mappings.get(imageLoc);
        String category = entry[0];
        String text = entry[1];
            this.currentCategory = category;
        if (category == null) {
            // If the entry is a category itself
            this.currentCategory = text;
        } // if
        return text;
    }

}