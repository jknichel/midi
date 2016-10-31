package cs3500.music.tests;

import org.junit.Test;

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

    assertEquals(a.getNoteLists().get(0).getNotes().get(0).getStartBeat(),
            8);
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

    assertEquals(a.getNoteLists().get(0).getNotes().get(0).toString(),
            "E3");
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

  @Test(expected = IllegalArgumentException.class)
  public void testAddOverlappingNote() {
    Song song = new Song();

    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 0, 5));
    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 2, 5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNonExistentNote() {
    Song song = new Song();

    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 0, 5));
    song.removeNote(Pitches.C, 4, 0, 20);
  }

  @Test
  public void testRowToStringMethods() {
    Song song = new Song();

    song.addNote(new MusicNoteWithDuration(Pitches.C, 4, 0, 5));
    song.addNote(new MusicNoteWithDuration(Pitches.C, 5, 2, 5));

    assertEquals(song.noteHeaderRowToString(), "  C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   "
            + "A4  A#4   B4   C5 ");
    assertEquals(song.beatRowNotesToString(0), "  X                                             "
            + "                   ");
    assertEquals(song.beatRowNotesToString(2), "  |                                             "
            + "              X    ");
  }
}