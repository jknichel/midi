package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Pitches;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the MusicNote class.
 */
public class MusicNoteTest {
  @Test
  public void gettersAndToStringTest() {
    MusicNote note1 = new MusicNote();
    MusicNote note2 = new MusicNote(Pitches.A_SHARP, 12, 2, 3, 4);

    assertEquals(note1.getPitch(), Pitches.C);
    assertEquals(note1.getOctave(), 4);
    assertEquals(note1.toString(), "C4");
    assertEquals(note2.getPitch(), Pitches.A_SHARP);
    assertEquals(note2.getOctave(), 12);
    assertEquals(note2.getInstrument(), 2);
    assertEquals(note2.getVolume(), 3);
    assertEquals(note2.getTotalDuration(), 4);
    assertEquals(note2.toString(), "A#12");
  }

  @Test
  public void nextNoteTest() {
    MusicNote note = new MusicNote(Pitches.A_SHARP, 9);

    assertEquals(note.nextNote().getPitch(), Pitches.B);
    assertEquals(note.nextNote().getOctave(), 9);
    assertEquals(note.nextNote().nextNote().getPitch(), Pitches.C);
    assertEquals(note.nextNote().nextNote().getOctave(), 10);
  }

}