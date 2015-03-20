package models;

import models.exception.ItemNotFoundException;
import models.exception.UserNotFoundException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.jblas.NDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DataModelIterator;
import util.Tuple;

import java.util.*;

/**
 * Created by laurencewelch on 3/17/15.
 */
public class MatrixPreferenceDataModel implements IDataModel, Iterable<Tuple<Long, HashMap<Long, Double>>> {

  private static Logger log = LoggerFactory.getLogger(MatrixPreferenceDataModel.class);
  Map<Long, HashMap<Long, Double>> dataset = new HashMap<Long, HashMap<Long, Double>>();

  INDArray index;

  private Long[] userIds;
  private Long[] itemIds;

  private double maxPref = Double.MIN_VALUE;
  private double minPref = Double.MAX_VALUE;

  public MatrixPreferenceDataModel(HashMap<Long, HashMap<Long, Double>> dataset) {
    this.dataset = dataset;
    buildModel();
  }

  private void buildModel() {
    if (dataset == null)
      return;
    userIds = util.Util.getLongKeyFromMap(dataset);
    itemIds = util.Util.getLongIdFromMap(dataset);
    log.info("Creating index for " + userIds + " Users and " + itemIds + " Items");
    index = Nd4j.create(userIds.length, itemIds.length);
    int i = 0; //user number
    for (Long user_id : userIds) {
      if (i % 2 == 0) {
        log.info("PROGRESS: at user id: " + i + "/" + userIds.length);
      }
      int j = 0; // item number
      for (Long item_id : itemIds) {
        double r = dataset.get(user_id).getOrDefault(item_id, Double.NaN);
        index.put(i, j, r);
        j++;
      }
      i++;
    }

    if (index != null) {
      maxPref = Nd4j.max(index).getDouble();
      minPref = Nd4j.min(index).getDouble();
    }

  }

  public HashMap<Long, Double> get(Long user) {
    return dataset.get(user);
  }

  public int[] shape(){
    return index.shape();
  }

  @Override
  public Long[] userIds() {
    return userIds;
  }

  @Override
  public Long[] itemIds() {
    return itemIds;
  }

  @Override
  public INDArray preferenceValuesFromUser(Long user) {
    Integer rowId = util.Util.indexOfArray(userIds, user);

    if (rowId == -1) {
      throw new UserNotFoundException(rowId);
    }
    return index.getRow(rowId);
  }

  @Override
  public Map<Long, Double> preferencesFromUser(Long user, boolean orderById) {
    INDArray preferences = preferenceValuesFromUser(user);
    HashMap<Long, Double> res = new HashMap<Long, Double>();

    for(int i = 0; i < itemIds.length; i++){
      if (preferences.getDouble(i) != Double.NaN)
        res.putIfAbsent(itemIds[i], preferences.getDouble(i));
    }
    if (orderById){
      //sorted by item id
      return util.Util.sortByKeys(res);
    }
    return res;
  }

  @Override
  public Map<Long, Double> preferencesFromUser(Long user) {
    return null;
  }

  @Override
  public Long[] itemsFromUser(Long user, boolean orderById) {
    Map<Long, Double> preferences = preferencesFromUser(user, orderById);
    Long[] res = new Long[preferences.size()];
    return preferences.keySet().toArray(res);
  }

  @Override
  public Long[] itemsFromUser(Long user) {
    return itemsFromUser(user, false);
  }

  @Override
  public Map<Long, Double> preferencesForItem(Long item, boolean orderById) {
    Integer colId = util.Util.indexOfArray(itemIds, item);
    if (colId == -1) {
      throw new ItemNotFoundException(colId);
    }
    INDArray preferences =  index.getColumn(colId);

    HashMap<Long, Double> res = new HashMap<Long, Double>();

    for(int i = 0; i < userIds.length; i++){
      if (preferences.getDouble(i) != Double.NaN)
        res.putIfAbsent(userIds[i], preferences.getDouble(i));
    }
    if (orderById){
      //sorted by item id
      return util.Util.sortByKeys(res);
    }
    return res;
  }

  @Override
  public Map<Long, Double> preferencesForItem(Long item) {
    return preferencesForItem(item, true);
  }

  @Override
  public Double preferenceValue(Long user, Long item) {
    Integer rowId = util.Util.indexOfArray(userIds, user);
    if (rowId == -1) {
      throw new UserNotFoundException(rowId);
    }

    Integer colId = util.Util.indexOfArray(itemIds, item);
    if (colId == -1) {
      throw new ItemNotFoundException(colId);
    }

    return index.getDouble(rowId,colId);
  }

  @Override
  public Long preferenceTime(Long user, Long item) {
    return 0L;
  }

  @Override
  public Integer usersCount() {
    return userIds.length;
  }

  @Override
  public Integer itemsCount() {
    return itemIds.length;
  }

  @Override
  public void setPreference(Long user, Long item, double value) {
    int rowId = util.Util.indexOfArray(userIds, user);
    if (rowId == -1) {
      throw new UserNotFoundException(rowId);
    }
    dataset.get(Long.valueOf(rowId)).put(Long.valueOf(item), value);
    buildModel();
  }

  @Override
  public void removePreference(Long user, Long item) {
    Integer rowId = util.Util.indexOfArray(userIds, user);
    if (rowId == -1) {
      throw new UserNotFoundException(rowId);
    }

    Integer colId = util.Util.indexOfArray(itemIds, item);
    if (colId == -1) {
      throw new ItemNotFoundException(colId);
    }
    dataset.get(Long.valueOf(rowId)).remove(Long.valueOf(item));
  }

  @Override
  public boolean hasPreferenceValues() {
    return true;
  }

  @Override
  public double maxPreferenceValue() {
    return maxPref;
  }

  @Override
  public double minPreferenceValue() {
    return minPref;
  }

  @Override
  public Iterator<Tuple<Long, HashMap<Long, Double>>> iterator() {
    return new DataModelIterator(dataset);
  }
}
