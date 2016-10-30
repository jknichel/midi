package music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the MusicNoteWithDuration class. It will test the constructor input validation
 * and the getter methods.
 */
public class MusicNoteWithDurationTest {
  @Test
  public void constructorGetterAndToStringTests() {
    MusicNoteWithDuration note1 = new MusicNoteWithDuration();
    MusicNoteWithDuration note2 = new MusicNoteWithDuration(Pitches.D_SHARP, 11, 10, 200);

    assertEquals(note1.getPitch(), Pitches.C);
    assertEquals(note1.getOctave(), 4);
    assertEquals(note1.getStartBeat(), 0);
    assertEquals(note1.getDuration(), 4);
    assertEquals(note1.getEndBeat(), 3);
    assertEquals(note1.toString(), "C4");

    assertEquals(note2.getPitch(), Pitches.D_SHARP);
    assertEquals(note2.getOctave(), 11);
    assertEquals(note2.getStartBeat(), 10);
    assertEquals(note2.getDuration(), 200);
    assertEquals(note2.getEndBeat(), 209);
    assertEquals(note2.toString(), "D#11");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeOctaveConstructorInputs() {
    new MusicNoteWithDuration(Pitches.C, -10, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStartBeatConstructorInputs() {
    new MusicNoteWithDuration(Pitches.C, 10, -5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeDurationConstructorInputs() {
    new MusicNoteWithDuration(Pitches.C_SHARP, 8, 5, -8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroDurationConstructorInputs() {
    new MusicNoteWithDuration(Pitches.C_SHARP, 8, 5, 0);
  }
}