package structures;

import java.lang.reflect.Array;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Reed Colloton
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V>[] pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({"unchecked"})
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) Array.newInstance((new KVPair<K, V>()).getClass(),
            DEFAULT_CAPACITY);
    this.size = 0;
  } // structures.AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this structures.AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> arr = new AssociativeArray<>();
    for (int i = 0; i < this.size; i++) {
      try {
        arr.set(this.pairs[i].key, this.pairs[i].value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      } // try
    } // for
    return arr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    if (this.size == 0) {
      return "{}";
    } // if
    String arr = "{";
    for (int i = 0; i < this.size; i++) {
      arr += " " + this.pairs[i].key + ": " + this.pairs[i].value;
      if (i != (this.size - 1)) {
        arr += ",";
      } // if
    } // for
    return arr + " }";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws Exception {
    if (key == null) {
      throw new Exception("Key cannot be null.");
    } // if
    if (this.size == this.pairs.length) {
      this.expand();
    } // if
    if (this.hasKey(key)) {
      this.pairs[this.find(key)].value = value;
    } else {
      this.pairs[this.size++] = new KVPair<>(key, value);
    } // if
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException when the key does not appear in the associative
   *                              array.
   */
  public V get(K key) throws Exception {
    if (key == null) {
      throw new Exception("Key cannot be null.");
    } // if
    return this.pairs[this.find(key)].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) throws Exception {
    if (key == null) {
      throw new Exception("Key cannot be null.");
    } // if
    try {
      this.find(key);
      return true;
    } catch (KeyNotFoundException e) {
      return false;
    } // try
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) throws Exception {
    if (key == null) {
      throw new Exception("Key cannot be null.");
    } // if
    int i = this.find(key);
    if (i == (this.size - 1)) {
      // If pair is last, set equal to null
      this.pairs[i] = new KVPair<>(null, null);
    } else {
      // Else, move last pair to removed pair
      this.pairs[i] = this.pairs[this.size - 1];
      this.pairs[this.size - 1] = new KVPair<>(null, null);
    } // if
    this.size --;
  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws Exception {
    if (key == null) {
      throw new Exception("Key cannot be null.");
    } // if
    for (int i = 0; i < this.size; i++) {
      if (key.equals(this.pairs[i].key)) {
        return i;
      } // if
    } // for
    throw new KeyNotFoundException("Key not found.");
  } // find(K)

  /**
   * Return all pairs.
   */
  public KVPair<K, V>[] all() {
    return this.pairs;
  } // KVPair<K, V>[] all()

} // class structures.AssociativeArray
