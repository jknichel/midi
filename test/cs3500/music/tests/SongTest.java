package cs3500.music.tests;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicNoteWithDuration;
import cs3500.music.model.Pitches;
import cs3500.music.model.Song;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the Song class. Will test all constructors and public methods.
 */
public class SongTest {
  @Test
  public void testCombineConsec() {
    Song a = new Song();

    a.addNote(new MusicNoteWithDuration(Pitches.C_SHARP, 4, 0, 5));
    a.addNote(new MusicNoteWithDuration(Pitches.D, 5, 2, 6));

    Song b = new Song();

    b.addNote(new MusicNoteWithDuration(Pitches.E, 3, 1, 4));
    b.addNote(new MusicNoteWithDuration(Pitches.F_SHARP, 7, 0, 7));

    a.combineConsec(b);

    // the last note to finish from the first song ends at its beat 7
    // the last note to finish from song 2 starts at its beat 0 and ends at its beat 6
    // so last beat first song + last end of second song = 7 + 6 == 13
    assertEquals(a.getLength(), 13);
  }

  @Test
  public void testCombineSimul() {
    Song a = new Song();

    a.addNote(new MusicNoteWithDuration(Pitches.C_SHARP, 4, 0, 5));
    a.addNote(new MusicNoteWithDuration(Pitches.D, 5, 2, 6));

    Song b = new Song();

    b.addNote(new MusicNoteWithDuration(Pitches.E, 3, 1, 4));
    b.addNote(new MusicNoteWithDuration(Pitches.F_SHARP, 7, 0, 7));

    a.combineSimul(b);

    // the last note to finish from either song starts at 2 and lasts for 6 beats
    // so the length of the song should be 2 + 6 - 1 == 7
    assertEquals(a.getLength(), 7);
  }

  @Test
  public void addAndRemoveNotesAndGetLengthTest() {
    Song song = new Song();

    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 0, 5));
    assertEquals(song.getLength(), 4);

    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 5, 5));
    assertEquals(song.getLength(), 9);

    song.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 10, 0, 100));
    assertEquals(song.getLength(), 99);

    song.removeNote(Pitches.D_SHARP, 10, 0, 99);
    assertEquals(song.getLength(), 9);
  }

  @Test
  public void testAddOverlappingNote() {
    Song song = new Song();

    assertEquals(song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 0, 5)), true);
    assertEquals(song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 2, 5)), false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNonExistentNote() {
    Song song = new Song();

    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 0, 5));
    song.removeNote(Pitches.C, 4, 0, 20);
  }

  @Test
  public void testNoteBeatsMaps() {
    Song song = new Song();
    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 0, 5));
    song.addNote(new MusicNoteWithDuration(Pitches.G, 5, 2, 5));

    Map<Integer, List<MusicNote>> starts = song.noteStartingBeats();
    Map<Integer, List<MusicNote>> continues = song.noteContinuationBeats();

    MusicNote startsAt0 = starts.get(0).get(0);
    MusicNote continuesAt1 = continues.get(1).get(0);
    MusicNote startsAt2 = starts.get(2).get(0);
    List<MusicNote> continuesAt4 = continues.get(4);

    assertEquals(startsAt0.getPitch() == Pitches.C, true);
    assertEquals(continuesAt1.getPitch() == Pitches.C, true);
    assertEquals(startsAt2.getPitch() == Pitches.G, true);
    assertEquals(continuesAt4.get(0).getPitch() == Pitches.C, true);
    assertEquals(continuesAt4.get(1).getPitch() == Pitches.G, true);
  }
}