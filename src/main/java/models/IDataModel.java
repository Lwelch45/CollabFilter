package models;

import java.util.Iterator;
import java.util.List;

/**
 * Created by laurence on 3/17/15.
 */
public interface IDataModel {

  /**
   * @Returns returns all user ids in the model, in order
   */
  public Iterator<Long> userIds();

  /**
   * @Param user id of the user to obtain preference values for
   * @Param orderById if false order by magnitude of returned values
   * @Returns an array of the users preference values
   */
  public double[] preferenceValuesFromUsers(Long user, boolean orderById);

  /**
   * @Param user id of the user to obtain preference values for
   * @Returns an array of the users preference values
   */
  public double[] preferenceValuesFromUsers(Long user);

  /**
   * @Param user id of the user to obtain items for
   * @Param orderById if false order by the magnitude of the returned values
   * @Returns an array of the items the user expressed preference for
   */
  public long[] itemsFromUser(Long user, boolean orderById);

  /**
   * @Param user id of the user to obtain items for
   * @Return an array of the items the user expressed preference for
   **/
  public long[] itemsFromUser(Long user);

  public double[] preferencesForItem(Long item, boolean orderById);

  public double preferenceValue(Long user, Long item);

  public long preferenceTime(Long user, Long item);

  public long usersCount();

  public long itemsCount();

  public void setPreference(Long user, Long item, double value);

  public void removePreference(Long user, Long item);

  public boolean hasPreferenceValues();

  public double maxPreferenceValue();

  public double minPreferenceValue();


}
