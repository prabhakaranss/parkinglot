package com.gojek.parkinglot.strategy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class NearestAllocationStrategyTest {

  private ParkingStrategy<Integer> strategy;

  @Before
  public void setUp() {
    this.strategy = new NearestAllocationStrategy<>();
  }

  @Test
  public void returnNullOnNoMoreSlot() throws Exception {
    assertFalse(strategy.getSlot().isPresent());
  }

  @Test
  public void returnNearestSlot() {
    this.strategy.addSlot(5);
    this.strategy.addSlot(4);
    this.strategy.addSlot(2);
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(2));
  }

  @Test
  public void returnNearestSlotAfterSwap() {
    this.strategy.addSlot(5);
    this.strategy.addSlot(4);
    this.strategy.addSlot(2);
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(2));
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(4));
    assertEquals(this.strategy.getSlot().get(), Integer.valueOf(5));
    assertFalse(this.strategy.getSlot().isPresent());
  }
}
