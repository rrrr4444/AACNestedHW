import structures.AssociativeArray;
import structures.KVPair;

public class AACCategory {
  String category;
  AssociativeArray<String, String> mapping = new AssociativeArray<>();

  public AACCategory(String name) {
    this.category = name;
  } // AACCategory(String name)

  public void addItem​(String imageLoc, String text) throws Exception{
    this.mapping.set(imageLoc, text);
  } // void addItem​(String imageLoc, String text)

  public String getCategory(){
    return this.category;
  } // String getCategory()

  public String getText​(String imageLoc) throws Exception{
    return this.mapping.get(imageLoc);
  } // String getText​(String imageLoc)

  public boolean hasImage​(String imageLoc) throws Exception{
    return this.mapping.hasKey(imageLoc);
  } // boolean hasImage​(String imageLoc)

  public String[] getImages(){
    KVPair<String, String>[] pairs = this.mapping.all();
    String[] imageLocs = new String[pairs.length];
    for (int i = 0; i < imageLocs.length; i++) {
        imageLocs[i] = pairs[i].key;
    } // for
    return imageLocs;
  } // String[] getImages()

} // class AACCategory
