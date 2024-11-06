package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for TeamColors.
 */
public class TeamColorTest {
  // Test abbreviation for RED
  @Test
  public void testToStringAbbreviationRed() {
    Assert.assertEquals("R", TeamColor.RED.toStringAbbreviation());
  }

  // Test abbreviation for BLUE
  @Test
  public void testToStringAbbreviationBlue() {
    Assert.assertEquals("B", TeamColor.BLUE.toStringAbbreviation());
  }
}