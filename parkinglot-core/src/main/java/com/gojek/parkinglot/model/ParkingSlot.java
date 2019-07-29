package com.gojek.parkinglot.model;

public class ParkingSlot implements Comparable<ParkingSlot>{
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ParkingSlot(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ParkingSlot{" + "id=" + id + '}';
  }

  @Override
  public int compareTo(ParkingSlot o) {
    return this.getId().compareTo(o.getId());
  }
}
