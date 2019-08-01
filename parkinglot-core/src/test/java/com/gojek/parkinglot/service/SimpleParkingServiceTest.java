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
    assertTrue(this.parkingService.park(myCar1).isPresent());
    assertTrue(this.parkingService.unPark(1));
  }

  @Test
  public void parkVehicleNoSlotsTest() {
    Vehicle myCar1 = new Vehicle("KA-01-HH-1234", "WHITE");
    assertFalse(this.parkingService.park(myCar1).isPresent());
  }

  @Test
  public void parkWhenNoSlotsLeft() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(1));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1234", "WHITE");
    assertTrue(this.parkingService.park(myCar1).isPresent());
    Vehicle myCar2 = new Vehicle("KA-01-HR-1234", "WHITE");
    assertFalse(this.parkingService.park(myCar2).isPresent());
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

  @Test
  public void getRegNumberForColorTest() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(2));
    slotList.add(new ParkingSlot(1));
    slotList.add(new ParkingSlot(5));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1235", "WHITE");
    this.parkingService.park(myCar1);
    Vehicle myCar2 = new Vehicle("KA-01-HH-1236", "Black");
    this.parkingService.park(myCar2);
    Vehicle myCar3 = new Vehicle("KA-01-HH-1237", "WHITE");
    this.parkingService.park(myCar3);
    List<String> regNumberList = this.parkingService.getAllRegNumber("WHITE");
    assertEquals(regNumberList.size(), 2);
    assertEquals(regNumberList.get(0), "KA-01-HH-1235");
    assertEquals(regNumberList.get(1), "KA-01-HH-1237");
    regNumberList = this.parkingService.getAllRegNumber("Blue");
    assertEquals(regNumberList.size(), 0);
  }

  @Test
  public void getParkingSlotForColorTest() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(10));
    slotList.add(new ParkingSlot(3));
    slotList.add(new ParkingSlot(5));
    this.strategy.createLot(slotList);
    Vehicle myCar1 = new Vehicle("KA-01-HH-1235", "WHITE");
    this.parkingService.park(myCar1);
    Vehicle myCar2 = new Vehicle("KA-01-HH-1236", "Black");
    this.parkingService.park(myCar2);
    Vehicle myCar3 = new Vehicle("KA-01-HH-1237", "WHITE");
    this.parkingService.park(myCar3);
    List<Integer> matchedSlotList = this.parkingService.getAllSlot("WHITE");
    assertEquals(matchedSlotList.size(), 2);
    assertEquals(matchedSlotList.get(0).intValue(), 3);
    assertEquals(matchedSlotList.get(1).intValue(), 10);
    matchedSlotList = this.parkingService.getAllSlot("Black");
    assertEquals(matchedSlotList.size(), 1);
    assertEquals(matchedSlotList.get(0).intValue(), 5);
    matchedSlotList = this.parkingService.getAllSlot("Blue");
    assertEquals(matchedSlotList.size(), 0);
  }
}
