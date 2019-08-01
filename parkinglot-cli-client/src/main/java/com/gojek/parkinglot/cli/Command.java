package com.gojek.parkinglot.cli;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
  CREATE_LOT("create_parking_lot"),
  PARK("park"),
  lEAVE("leave"),
  STATUS("status"),
  REG_NUMBERS_FOR_COLOR("registration_numbers_for_cars_with_colour"),
  SLOT_NUMBERS_FOR_COLOR("slot_numbers_for_cars_with_colour"),
  SLOT_NUMBER_FOR_REG_NUM("slot_number_for_registration_number"),
  EXIT("exit");

  private String command;

  Command(String command) {
    this.command = command;
  }

  public static Command fromText(String text) {
    Optional<Command> optionalCommand = Arrays.stream(values()).filter(cmd -> cmd.command.equals(text)).findFirst();
    if (optionalCommand.isPresent()) {
      return optionalCommand.get();
    }
    throw new IllegalArgumentException("invalid command text");
  }
}
