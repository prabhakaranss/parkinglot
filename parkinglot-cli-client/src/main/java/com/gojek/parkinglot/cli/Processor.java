package com.gojek.parkinglot.cli;

import com.gojek.parkinglot.model.ParkingSlot;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.service.ParkingService;
import com.gojek.parkinglot.service.impl.SimpleParkingService;
import com.gojek.parkinglot.strategy.NearestAllocationStrategy;
import com.gojek.parkinglot.strategy.ParkingStrategy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Processor {

  private ParkingStrategy parkingStrategy;
  private ParkingService parkingService;

  public Processor() {
    this.parkingStrategy = new NearestAllocationStrategy();
    this.parkingService = new SimpleParkingService(this.parkingStrategy);
  }

  public void processCommand(Command command, String[] args) {
    switch (command) {
      case CREATE_LOT:
        if (args.length != 1) {
          throw new IllegalArgumentException("invalid command");
        }
        this.createParkingLot(Integer.parseInt(args[0]));
        break;
      case PARK:
        if (args.length != 2) {
          throw new IllegalArgumentException("invalid command");
        }
        this.park(args[0], args[1]);
        break;
      case lEAVE:
        if (args.length != 1) {
          throw new IllegalArgumentException("invalid command");
        }
        this.leave(Integer.parseInt(args[0]));
        break;
      case STATUS:
        if (args.length != 0) {
          throw new IllegalArgumentException("invalid command");
        }
        this.getStatus();
        break;
      case REG_NUMBERS_FOR_COLOR:
        if (args.length != 1) {
          throw new IllegalArgumentException("invalid command");
        }
        this.printRegNumbersForColor(args[0]);
        break;
      case SLOT_NUMBERS_FOR_COLOR:
        if (args.length != 1) {
          throw new IllegalArgumentException("invalid command");
        }
        this.printSlotIdForColor(args[0]);
        break;
      case SLOT_NUMBER_FOR_REG_NUM:
        if (args.length != 1) {
          throw new IllegalArgumentException("invalid command");
        }
        this.printSlotIdForRegNumber(args[0]);
        break;

      default:
        throw new IllegalArgumentException("Unknown command found to process");
    }
  }

  private void printSlotIdForRegNumber(String regNumber) {
    Optional<Integer> foundSlotId = this.parkingService.getSlotIdForRegNumber(regNumber);
    if (foundSlotId.isPresent()) {
      System.out.println(foundSlotId.get());
      return;
    }
    System.out.println("Not found");
  }

  private void printSlotIdForColor(String color) {
    System.out.println(String.join(", ", this.parkingService.getAllSlot(color).stream().map(slotId -> Integer.toString(slotId)).collect(Collectors.toList())));
  }

  private void printRegNumbersForColor(String color) {
    System.out.println(String.join(", ", this.parkingService.getAllRegNumber(color)));
  }

  private void createParkingLot(Integer capacity) {
    List<ParkingSlot> parkingSlotList = IntStream.range(1, capacity+1).mapToObj(ParkingSlot::new).collect(Collectors.toList());
    this.parkingStrategy.createLot(parkingSlotList);
    System.out.printf("Created a parking lot with %d slots\n", capacity);
  }

  private void park(String regNumber, String color) {
    Vehicle vehicle = new Vehicle(regNumber, color);
    Optional<Integer> optionalSlot = this.parkingService.park(vehicle);
    if (optionalSlot.isPresent()) {
      System.out.printf("Allocated slot number: %d\n", optionalSlot.get());
      return;
    }
    System.out.println("Sorry, parking lot is full");
  }

  private void leave(Integer slotId) {
    if (this.parkingService.unPark(slotId)) {
      System.out.printf("Slot number %d is free\n", slotId);
      return;
    }
    System.out.printf("Could not find a vehicle in Slot %d\n", slotId);
  }

  private void getStatus() {
    System.out.println("Slot No. Registration No Colour");
    this.parkingService.getParkingStatus().forEach(parkingEntry -> System.out.println(parkingEntry.getParkingSlot().getId() + " " + parkingEntry.getVehicle().getRegNumber() + " " + parkingEntry.getVehicle().getColor()));
  }
}
