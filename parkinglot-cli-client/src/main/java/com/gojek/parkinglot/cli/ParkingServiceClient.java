package com.gojek.parkinglot.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ParkingServiceClient {

  public static void main(String[] args) {
    Processor cliProcessor = new Processor();
    try {
      switch (args.length) {
        case 0:
          boolean canContinue = true;
          Command command;
          try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (canContinue) {
              try {
                String[] inputs = bufferedReader.readLine().trim().split(" ");
                command = Command.fromText(inputs[0]);

                switch (command) {
                  case EXIT:
                    canContinue = false;
                    break;

                  default:
                    cliProcessor.processCommand(command, Arrays.copyOfRange(inputs, 1, inputs.length));
                }
              } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please input valid command");
              }
            }
          } catch (IOException e) {
            System.out.println("Unknown error occurred:"+e.getMessage());
          }
          break;

        case 1:
          File testFile = new File(args[0]);
          String input;
          String[] inputs;
          try (BufferedReader bufferedReader = new BufferedReader(new FileReader(testFile))) {
            while ((input = bufferedReader.readLine()) != null) {
              inputs = input.split(" ");
              try {
                command = Command.fromText(inputs[0]);
                cliProcessor.processCommand(command, Arrays.copyOfRange(inputs, 1, inputs.length));
              } catch (IllegalArgumentException e) {
                System.out.println("Invalid input:" + input+", "+e.getMessage());
              }
            }
          } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid file found to process:"+exception.getMessage());
          }
          break;

        default:
          System.out.println("Invalid arguments given to run the application");
      }
    } catch (Exception exception) {
      System.out.println("unknown error occurred:" + exception.getMessage());
      exception.printStackTrace();
    }
  }
}
