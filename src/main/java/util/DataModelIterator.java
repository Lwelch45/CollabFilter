package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by laurencewelch on 3/17/15.
 */
public class DataModelIterator implements Iterator<Tuple<Long, HashMap<Long, Double>>> {
  Iterator<Map.Entry<Long, HashMap<Long, Double>>> dataset;

  public DataModelIterator(Map<Long, HashMap<Long, Double>> dataset) {

    this.dataset = dataset.entrySet().iterator();
  }

  @Override
  public boolean hasNext() {
    return dataset.hasNext();
  }

  @Override
  public Tuple<Long, HashMap<Long, Double>> next() {
    Map.Entry<Long, HashMap<Long, Double>> mn = dataset.next();
    return new Tuple<Long, HashMap<Long, Double>>(mn.getKey(), mn.getValue());
  }
}
