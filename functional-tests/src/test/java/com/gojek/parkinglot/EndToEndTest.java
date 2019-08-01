package com.gojek.parkinglot;

import com.gojek.parkinglot.cli.ParkingServiceClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class EndToEndTest {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream SystemPrintStream = System.out;

  @Before
  public void setUp(){
      System.setOut(new PrintStream(outputStream));
  }

  @After
  public void tearDown() {
    System.setOut(SystemPrintStream);
  }

  @Test
  public void runFileInputTest() {
    String fileInput = "file_input.txt";
    String fileOutput = "file_output.txt";
    try{
      File file = new File(getClass().getClassLoader().getResource(fileInput).getFile());
      String[] arguments = {file.getAbsolutePath()};
      ParkingServiceClient.main(arguments);
      String expectedOutput = this.getFileContentAsString(fileOutput);
      Assert.assertEquals(expectedOutput, outputStream.toString());
    } catch (Exception ex){
      Assert.fail("Error occurred in functional test:"+ex.getMessage());
    }

  }

  private String getFileContentAsString(String filename) throws IOException {
    StringBuilder sb = new StringBuilder();
    File file = new File(getClass().getClassLoader().getResource(filename).getFile());
    String line;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (Exception ex) {
      throw ex;
    }
    return sb.toString();
  }
}
