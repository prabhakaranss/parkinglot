package com.gojek.parkinglot.service;

import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.strategy.ParkingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ParkingService {

  protected ParkingStrategy parkingStrategy;
  protected Map<Integer, Vehicle> parkedSlots;

  public ParkingService(ParkingStrategy strategy) {
    this.parkingStrategy = strategy;
    this.parkedSlots = new HashMap<>();
  }

  public abstract boolean park(Vehicle vehicle);

  public abstract boolean unPark(Integer slotId);

  public abstract Optional<Integer> getSlotIdForRegNumber(String regNumber);
}
