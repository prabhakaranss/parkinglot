package com.gojek.parkinglot.strategy;

import com.gojek.parkinglot.model.ParkingSlot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

public class NearestAllocationStrategy implements ParkingStrategy {

  private Map<Integer, ParkingSlot> slots = new HashMap<>();
  private TreeSet<Integer> freeSlots = new TreeSet<>();
  private TreeSet<Integer> allocatedSlots = new TreeSet<>();

  @Override
  public void createLot(Collection<ParkingSlot> slots) {
    slots.forEach(slot -> {
      this.slots.put(slot.getId(), slot);
      this.freeSlots.add(slot.getId());
    });
  }

  @Override
  public void freeUpSlot(Integer slotId) {
    this.allocatedSlots.remove(slotId);
    this.freeSlots.add(slotId);
  }

  @Override
  public Optional<Integer> getSlot() {
    Integer nextFreeSlot = this.freeSlots.pollFirst();
    if (nextFreeSlot == null) {
      return Optional.empty();
    }
    this.allocatedSlots.add(nextFreeSlot);
    return Optional.of(nextFreeSlot);
  }

  @Override
  public Integer availableSlots() {
    return this.freeSlots.size();
  }
}
