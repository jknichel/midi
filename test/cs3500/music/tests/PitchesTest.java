package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.Pitches;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the Pitches enum.
 */
public class PitchesTest {
  /**
   * Tests the nextPitch method to make sure it steps through the pitches correctly.
   */
  @Test
  public void nextPitchAndScaleIndexTest() {
    Pitches pitch = Pitches.C;
    assertEquals(pitch.getScaleIndex(), 1);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.C_SHARP);
    assertEquals(pitch.getScaleIndex(), 2);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.D);
    assertEquals(pitch.getScaleIndex(), 3);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.D_SHARP);
    assertEquals(pitch.getScaleIndex(), 4);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.E);
    assertEquals(pitch.getScaleIndex(), 5);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.F);
    assertEquals(pitch.getScaleIndex(), 6);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.F_SHARP);
    assertEquals(pitch.getScaleIndex(), 7);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.G);
    assertEquals(pitch.getScaleIndex(), 8);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.G_SHARP);
    assertEquals(pitch.getScaleIndex(), 9);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.A);
    assertEquals(pitch.getScaleIndex(), 10);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.A_SHARP);
    assertEquals(pitch.getScaleIndex(), 11);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.B);
    assertEquals(pitch.getScaleIndex(), 12);
    pitch = pitch.nextPitch();
    assertEquals(pitch, Pitches.C);
    assertEquals(pitch.getScaleIndex(), 1);
  }

  /**
   * Makes sure the toString method returns correctly for each pitch value.
   */
  @Test
  public void toStringTest() {
    Pitches pitch = Pitches.C;
    assertEquals(pitch.toString(), "C");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "C#");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "D");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "D#");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "E");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "F");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "F#");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "G");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "G#");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "A");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "A#");
    pitch = pitch.nextPitch();
    assertEquals(pitch.toString(), "B");
  }

  /**
   * Tests that the method to translate an int to a Pitches enum works correctly.
   */
  @Test
  public void getPitchFromScaleIndexTest() {
    Pitches pitch = Pitches.C;
    assertEquals(pitch, pitch.getPitchFromScaleIndex(1));
    assertEquals(Pitches.B, pitch.getPitchFromScaleIndex(12));
    assertEquals(Pitches.G_SHARP, pitch.getPitchFromScaleIndex(9));
  }

}