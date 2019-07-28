package com.gojek.parkinglot.strategy;

import java.util.Optional;
import java.util.TreeSet;

public class NearestAllocationStrategy<T> implements ParkingStrategy<T> {

  private TreeSet<T> slots = new TreeSet<>();

  @Override
  public void addSlot(T slot) {
    this.slots.add(slot);
  }

  @Override
  public Optional<T> getSlot() {
    return Optional.ofNullable(this.slots.pollFirst());
  }
}
