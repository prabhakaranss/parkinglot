package com.gojek.parkinglot.strategy;

import com.gojek.parkinglot.model.ParkingSlot;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ParkingStrategy {

  /**
   * setup a parking lot on initialization
   * @param slots collection slots
   */
  void createLot(Collection<ParkingSlot> slots);

  /**
   * add a slot into available collection
   * @param slotId parking location identifier
   */
  void freeUpSlot(Integer slotId);

  /**
   * get next available slot and removes the same from available slot
   * @return slot
   */
  Optional<Integer> getSlot();

  /**
   * @return returns available slots count
   */
  Integer availableSlots();

  /**
   *
   * @return allocated parkingSlots identifiers
   */
  List<ParkingSlot> getAllocatedSlots();

  /**
   * return all allocated slot identifiers
   * @return
   */
  List<Integer> getAllAllocatedSlotId();

}
