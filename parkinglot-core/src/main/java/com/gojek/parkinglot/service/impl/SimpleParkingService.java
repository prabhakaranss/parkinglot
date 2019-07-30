package com.gojek.parkinglot.service.impl;

import com.gojek.parkinglot.model.ParkingEntry;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.service.ParkingService;
import com.gojek.parkinglot.strategy.ParkingStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleParkingService extends ParkingService {

  public SimpleParkingService(ParkingStrategy strategy) {
    super(strategy);
  }

  @Override
  public boolean park(Vehicle vehicle) {
    Optional<Integer> nextSlot = this.parkingStrategy.getSlot();
    if (!nextSlot.isPresent()) {
      return false;
    }

    this.parkedSlots.put(nextSlot.get(), vehicle);
    return true;
  }

  @Override
  public boolean unPark(Integer slotId) {
    if (this.parkedSlots.containsKey(slotId)) {
      this.parkedSlots.remove(slotId);
      this.parkingStrategy.freeUpSlot(slotId);
      return true;
    }

    return false;
  }

  @Override
  public Optional<Integer> getSlotIdForRegNumber(String regNumber) {
    return this.parkedSlots.entrySet().stream()
        .filter(parkingEntry -> parkingEntry.getValue().getRegNumber().equals(regNumber))
        .findFirst().map(Map.Entry::getKey);
  }

  @Override
  public List<ParkingEntry> getParkingStatus() {
    return this.parkingStrategy
        .getAllocatedSlots()
        .stream()
        .map(slot -> new ParkingEntry(slot, this.parkedSlots.get(slot.getId())))
        .collect(Collectors.toList());
  }
}
