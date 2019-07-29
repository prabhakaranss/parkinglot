package com.gojek.parkinglot.service;

import com.gojek.parkinglot.model.ParkingSlot;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.service.impl.SimpleParkingService;
import com.gojek.parkinglot.strategy.NearestAllocationStrategy;
import com.gojek.parkinglot.strategy.ParkingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleParkingServiceTest {
  private ParkingStrategy strategy;
  private SimpleParkingService parkingService;

  @Before
  public void setUp() {
    this.strategy = new NearestAllocationStrategy();
    this.parkingService = new SimpleParkingService(this.strategy);
  }

  @Test
  public void parkVehicleTest() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(1));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1234", "WHITE");
    assertTrue(this.parkingService.park(myCar1));
    assertTrue(this.parkingService.unPark(1));
  }

  @Test
  public void parkVehicleNoSlotsTest() {
    Vehicle myCar1 = new Vehicle("KA-01-HH-1234", "WHITE");
    assertFalse(this.parkingService.park(myCar1));
  }

  @Test
  public void parkWhenNoSlotsLeft() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(1));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1234", "WHITE");
    assertTrue(this.parkingService.park(myCar1));
    Vehicle myCar2 = new Vehicle("KA-01-HR-1234", "WHITE");
    assertFalse(this.parkingService.park(myCar2));
  }

  @Test
  public void unParkUnOccupiedSlot() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(1));
    this.strategy.createLot(slotList);
    assertFalse(this.parkingService.unPark(1));
  }

}
