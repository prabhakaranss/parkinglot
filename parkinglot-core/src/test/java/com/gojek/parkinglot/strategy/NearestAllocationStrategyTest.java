package com.gojek.parkinglot.strategy;

import com.gojek.parkinglot.model.ParkingSlot;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class NearestAllocationStrategyTest {

  private ParkingStrategy strategy;

  @Before
  public void setUp() {
    List<ParkingSlot> slotList = new ArrayList<>();
    slotList.add(new ParkingSlot(5));
    slotList.add(new ParkingSlot(4));
    slotList.add(new ParkingSlot(2));

    this.strategy = new NearestAllocationStrategy();
    this.strategy.createLot(slotList);
  }

  @Test
  public void returnNullOnNoMoreSlot() throws Exception {
    this.strategy.getSlot();
    this.strategy.getSlot();
    this.strategy.getSlot();
    assertFalse(strategy.getSlot().isPresent());
  }

  @Test
  public void returnNearestSlot() {
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(2));
  }

  @Test
  public void returnNearestSlotAfterSwap() {
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(2));
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(4));
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(5));
    assertFalse(this.strategy.getSlot().isPresent());
  }
}
