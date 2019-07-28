package com.gojek.parkinglot.strategy;

import java.util.Optional;

public interface ParkingStrategy<T> {

  /**
   * add a slot into available collection
   * @param slot parking location
   */
  void addSlot(T slot);

  /**
   * get next available slot and removes the same from available slot
   * @return slot
   */
  Optional<T> getSlot();

}
