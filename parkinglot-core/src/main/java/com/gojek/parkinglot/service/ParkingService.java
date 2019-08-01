package com.gojek.parkinglot.service;

import com.gojek.parkinglot.model.ParkingEntry;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.strategy.ParkingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class ParkingService {

  protected ParkingStrategy parkingStrategy;
  protected Map<Integer, Vehicle> parkedSlots;

  public ParkingService(ParkingStrategy strategy) {
    this.parkingStrategy = strategy;
    this.parkedSlots = new HashMap<>();
  }

  public abstract Optional<Integer> park(Vehicle vehicle);

  public abstract boolean unPark(Integer slotId);

  public abstract Optional<Integer> getSlotIdForRegNumber(String regNumber);

  public abstract List<ParkingEntry> getParkingStatus();

  public abstract List<Integer> getAllSlot(String color);

  public abstract List<String> getAllRegNumber(String color);
}
