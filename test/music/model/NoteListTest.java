package music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the NoteList class. Will test all constructors and public methods.
 */
public class NoteListTest {
  @Test
  public void constructorAndGetterTests() {
    NoteList list1 = new NoteList();
    NoteList list2 = new NoteList(Pitches.F, 2);

    MusicNoteWithDuration note = new MusicNoteWithDuration(Pitches.G_SHARP, 5, 5, 5);
    NoteList list3 = new NoteList(note);

    assertEquals(list1.getPitch(), Pitches.C);
    assertEquals(list1.getOctave(), 4);
    assertEquals(list1.getLength(), 0);

    assertEquals(list2.getPitch(), Pitches.F);
    assertEquals(list2.getOctave(), 2);
    assertEquals(list2.getLength(), 0);

    assertEquals(list3.getPitch(), Pitches.G_SHARP);
    assertEquals(list3.getOctave(), 5);
    assertEquals(list3.getLength(), 9);
  }

  @Test
  public void addNoteTest() {
    NoteList list = new NoteList(Pitches.D_SHARP, 5);

    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 0, 8));
    assertEquals(list.getLength(), 7);

    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 9, 10));
    assertEquals(list.getLength(), 18);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryAddNonMatchingPitch() {
    NoteList list = new NoteList(Pitches.D_SHARP, 5);

    list.addNote(new MusicNoteWithDuration(Pitches.G_SHARP, 5, 0, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryAddNonMatchingOctave() {
    NoteList list = new NoteList(Pitches.D_SHARP, 5);

    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 8, 0, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryAddOverlappingNote() {
    NoteList list = new NoteList(Pitches.D_SHARP, 5);

    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 0, 10));
    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 5, 10));
  }

  @Test
  public void removeNoteTest() {
    NoteList list = new NoteList(Pitches.D_SHARP, 5);

    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 0, 10));
    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 10, 10));
    list.removeNote(10, 19);

    assertEquals(list.getLength(), 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentNoteTest() {
    NoteList list = new NoteList(Pitches.D_SHARP, 5);

    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 0, 10));
    list.addNote(new MusicNoteWithDuration(Pitches.D_SHARP, 5, 10, 10));
    list.removeNote(2, 19);
  }

  @Test
  public void noteStartAndContinuationChecksTest() {
    NoteList list = new NoteList(Pitches.C, 4);
    list.addNote(new MusicNoteWithDuration());

    assertEquals(list.hasNoteStartOnBeat(0), true);
    assertEquals(list.hasNoteStartOnBeat(1), false);
    assertEquals(list.hasNoteStartOnBeat(4), false);
    assertEquals(list.hasNoteContinuationOnBeat(0), false);
    assertEquals(list.hasNoteContinuationOnBeat(1), true);
    assertEquals(list.hasNoteContinuationOnBeat(2), true);
    assertEquals(list.hasNoteContinuationOnBeat(3), true);
    assertEquals(list.hasNoteStartOnBeat(5), false);
    assertEquals(list.hasNoteContinuationOnBeat(5), false);
  }
}