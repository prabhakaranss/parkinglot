package com.gojek.parkinglot.model;

public class Vehicle {
  private String regNumber;
  private String color;

  public Vehicle(String regNumber, String color) {
    if (regNumber == null || color == null) {
      throw new IllegalArgumentException("Vehicle reg-number and color is mandatory");
    }
    this.regNumber = regNumber;
    this.color = color;
  }

  public String getRegNumber() {
    return regNumber;
  }

  public void setRegNumber(String regNumber) {
    this.regNumber = regNumber;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return "Vehicle{" + "regNumber='" + regNumber + '\'' + ", color='" + color + '\'' + '}';
  }
}
