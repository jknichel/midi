package cs3500.music.tests;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Pitches;
import cs3500.music.model.Song;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the Song class. Will test all constructors and public methods.
 */
public class SongTest {
  /**
   * This large test does the following:
   * Creates a song, and adds two notes to it.
   * Gets the noteStartingBeats and noteContinuationBeats maps and makes sure the notes are there.
   * Checks the noteRange() returns the range of notes and the right number of notes.
   * Checks that the song length is as long as it should be.
   */
  @Test
  public void testMostSongMethods() {
    Song song = new Song();
    song.addNote(0, 4, 1, Pitches.C, 4, 10);
    song.addNote(2, 6, 1, Pitches.G, 5, 10);

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

    assertEquals(song.noteRange().size(), 20);
    assertEquals(song.noteRange().get(0).getPitch(), Pitches.C);
    assertEquals(song.noteRange().get(0).getOctave(), 4);
    assertEquals(song.noteRange().get(song.noteRange().size() - 1).getPitch(), Pitches.G);
    assertEquals(song.noteRange().get(song.noteRange().size() - 1).getOctave(), 5);

    assertEquals(song.getLength(), 6);

    song.removeNote(2, Pitches.G, 5);
    assertEquals(song.getLength(), 4);

    song.addNote(2, 6, 1, Pitches.G, 5, 10);
    song.removeNote(0, Pitches.C, 4);
    assertEquals(song.getLength(), 6);
  }
}