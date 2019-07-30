package com.gojek.parkinglot.service;

import com.gojek.parkinglot.model.ParkingEntry;
import com.gojek.parkinglot.model.ParkingSlot;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.service.impl.SimpleParkingService;
import com.gojek.parkinglot.strategy.NearestAllocationStrategy;
import com.gojek.parkinglot.strategy.ParkingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

  @Test
  public void getSlotIdForParkedVehicleTest() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(1));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1234", "WHITE");
    this.parkingService.park(myCar1);
    assertTrue(this.parkingService.getSlotIdForRegNumber("KA-01-HH-1234").isPresent());
  }

  @Test
  public void getSlotIdForNotParkedVehicleTest() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(1));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1235", "WHITE");
    this.parkingService.park(myCar1);
    assertFalse(this.parkingService.getSlotIdForRegNumber("KA-01-HH-1234").isPresent());
  }

  @Test
  public void getParkingStatusTest() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(2));
    slotList.add(new ParkingSlot(1));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1235", "WHITE");
    this.parkingService.park(myCar1);
    Vehicle myCar2 = new Vehicle("KA-01-HH-1236", "Black");
    this.parkingService.park(myCar2);
    List<ParkingEntry> parkingStatus = this.parkingService.getParkingStatus();
    assertEquals(parkingStatus.size(), 2);
    assertEquals(parkingStatus.get(0).getParkingSlot().getId().intValue(), 1);
    assertEquals(parkingStatus.get(0).getVehicle().getRegNumber(), "KA-01-HH-1235");
    assertEquals(parkingStatus.get(0).getVehicle().getColor(), "WHITE");
    assertEquals(parkingStatus.get(1).getParkingSlot().getId().intValue(), 2);
    assertEquals(parkingStatus.get(1).getVehicle().getRegNumber(), "KA-01-HH-1236");
    assertEquals(parkingStatus.get(1).getVehicle().getColor(), "Black");

  }

}
