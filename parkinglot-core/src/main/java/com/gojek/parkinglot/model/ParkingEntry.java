package com.gojek.parkinglot.model;

public class ParkingEntry {
  private ParkingSlot parkingSlot;
  private Vehicle vehicle;

  public ParkingSlot getParkingSlot() {
    return parkingSlot;
  }

  public void setParkingSlot(ParkingSlot parkingSlot) {
    this.parkingSlot = parkingSlot;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public ParkingEntry(ParkingSlot parkingSlot, Vehicle vehicle) {
    this.parkingSlot = parkingSlot;
    this.vehicle = vehicle;
  }

  @Override
  public String toString() {
    return "ParkingEntry{" + "parkingSlot=" + parkingSlot + ", vehicle=" + vehicle + '}';
  }
}
