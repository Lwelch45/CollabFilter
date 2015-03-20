package models;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Map;

/**
 * Created by laurence on 3/17/15.
 */
public interface IDataModel {

  /**
   * @Returns returns all user ids in the model, in order
   */
  public Long[] userIds();

  /**
   * @Returns returns all item ids in the model, in order
   */
  public Long[] itemIds();

  /**
   * @Param user id of the user to obtain preference values for
   * @Returns an array of the users preference values
   */
  public INDArray preferenceValuesFromUser(Long user);

  /**
   * @Return return a map of the preference items and their values
   */
  public Map<Long, Double> preferencesFromUser(Long user, boolean orderById);

  /**
   * @Return return a map of the preference items and their values
   */
  public Map<Long, Double> preferencesFromUser(Long user );

  /**
   * @Param user id of the user to obtain items for
   * @Param orderById if false order by the magnitude of the returned values
   * @Returns an array of the items the user expressed preference for
   */
  public Long[] itemsFromUser(Long user, boolean orderById);

  /**
   * @Param user id of the user to obtain items for
   * @Return an array of the items the user expressed preference for
   */
  public Long[] itemsFromUser(Long user);

  public Map<Long, Double> preferencesForItem(Long item, boolean orderById);

  public Map<Long, Double> preferencesForItem(Long item);

  public Double preferenceValue(Long user, Long item);

  public Long preferenceTime(Long user, Long item);

  /**
   * @Return returns the count of unique the users in the dataset.
   */
  public Integer usersCount();

  /**
   * @Return returns the count of unique the items in the dataset.
   */
  public Integer itemsCount();

  public void setPreference(Long user, Long item, double value);

  public void removePreference(Long user, Long item);

  /**
   * does the inherited class contain preference values or is it boolean data
   */
  public boolean hasPreferenceValues();

  /**
   * @Return returns the maximum value in the preference table
   */
  public double maxPreferenceValue();

  /**
   * @Return returns the smallest value in the preference table
   */
  public double minPreferenceValue();

}
