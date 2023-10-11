import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import structures.AssociativeArray;
import structures.KeyNotFoundException;

public class AACMappings {
  AssociativeArray<String, String> images = new AssociativeArray<>();
  AssociativeArray<String, AACCategory> categories = new AssociativeArray<>();
  AACCategory homeCategory;
  AACCategory currentCategory;

  public AACMappings(String filename) {
    String file;
    try {
      file = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Reading mappings file failed.");
    } // try/catch
    String[] rows = file.trim().split("\n");
    this.homeCategory = new AACCategory("home");
    for (String row: rows) {
      int splitOn = row.indexOf(' ');
      String imagePath = row.substring(0, splitOn);
      String text = row.substring(splitOn + 1, row.length());
      if (!row.contains(">")) {
        this.currentCategory = new AACCategory(text);
        this.categories.set(imagePath, this.currentCategory);
        this.homeCategory.addItem(imagePath, text);
      } else {
        // Remove ">"
        imagePath = imagePath.substring(1, imagePath.length());
        this.currentCategory.addItem(imagePath, text);
      } // if
    } // for
    this.currentCategory = this.homeCategory;
  } // AACmapping(String filename)

  public String[] getImageLocs() {
    return this.currentCategory.getImages();
  } // String[] getImageLocs()

  public void writeToFile(String filename) {
    // TODO
  } // void writeToFile(String string)

  public void reset() {
    this.currentCategory = this.homeCategory;
  } // void reset()

  public void add(String imageLoc, String text) {
    this.currentCategory.addItem(imageLoc, text);
  } // void add(String imageLoc, String text)

  public String getCurrentCategory() {
    return this.currentCategory.getCategory();
  } // String getCurrentCategory()

  public boolean isCategory​(String imageLoc) {
    return categories.hasKey(imageLoc);
  } // boolean isCategory​(String imageLoc)

  public String getText(String imageLoc) {
    String text = this.currentCategory.getText(imageLoc);
    try {
      this.currentCategory = categories.get(imageLoc);
    } catch (KeyNotFoundException e) {}
      return text;
  } // String getText(String imageLoc)

} // class AACmapping