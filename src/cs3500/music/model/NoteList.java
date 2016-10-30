package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * An object to hold all instances of a specific note. The NoteList is assigned a MusicNote with a
 * pitch and octave when it is constructed. It holds all of the MusicNoteWithDurations matching
 * those attributes along with all of the information their start beat, beat duration, and end
 * beat.
 */
public class NoteList {

  /**
   * The actual list of notes in the song.
   */
  private final ArrayList<MusicNoteWithDuration> notes = new ArrayList<>();

  /**
   * The MusicNote that identifies the type (read pitch and octave) of notes this NoteList can
   * contain.
   */
  protected final MusicNote noteListType;

  /**
   * Default constructor. Creates a note list representing "middle C".
   */
  public NoteList() {
    noteListType = new MusicNote(Pitches.C, 4);
  }

  /**
   * Creates a new list with passed pitch and octave values. Leaves the note list empty for now.
   * @param pitch pitch of the NoteList.
   * @param octave octave of the NoteList.
   */
  public NoteList(Pitches pitch, int octave) {
    noteListType = new MusicNote(pitch, octave);
  }

  /**
   * Creates a new NoteList based on a passed note. This note's pitch and octave are assigned to
   * this list, and then the note is added as the first note to the list.
   * @param note the note to base the new NoteList on and then add it.
   */
  public NoteList(MusicNoteWithDuration note) {
    noteListType = new MusicNote(note.getPitch(), note.getOctave());
    notes.add(note);
  }

  /**
   * Add a note to the note list. Will throw an IllegalArgumentException if the note isn't
   * applicable to this list, or if the note overlaps with a note already in the list. If the add
   * is successful, the notes will be sorted in order of start time.
   * @param note the MusicNoteWithDuration to be added to the song.
   * @throws IllegalArgumentException if the note trying to be added doesn't match this list,
   *         or overlaps an existing note in the list.
   */
  public void addNote(MusicNoteWithDuration note) {
    if (note.getPitch() != noteListType.getPitch() ||
            note.getOctave() != noteListType.getOctave()) {
      throw new IllegalArgumentException("Note does not match this list!");
    } else if (noteOverlapsWithNoteInList(note)) {
      throw new IllegalArgumentException("Note overlaps with existing note in this list!");
    } else {
      notes.add(note);
      sortNotesByStartTime();
    }
  }

  /**
   * Removes the note from this NoteList at the given start and end times.
   * @param startBeat starting beat of note to remove.
   * @param endBeat ending beat of note to remove.
   * @throws IllegalArgumentException if no note with the passed start and end times exists.
   */
  public void removeNote(int startBeat, int endBeat) {
    if (startBeat > endBeat) {
      throw new IllegalArgumentException("Starting beat cannot be less than ending beat!");
    }

    int indexToRemove = -1;
    for (int i = 0; i < notes.size(); i++) {
      if (notes.get(i).getStartBeat() == startBeat && notes.get(i).getEndBeat() == endBeat) {
        indexToRemove = i;
      }
    }
    if (indexToRemove >= 0) {
      notes.remove(indexToRemove);
    } else {
      throw new IllegalArgumentException("Note not found, cannot remove!");
    }
  }

  /**
   * Getter for the pitch of this NoteList.
   * @return the pitch of the list.
   */
  public Pitches getPitch() {
    return noteListType.getPitch();
  }

  /**
   * Getter for the octave of this NoteList.
   * @return the octave of this list.
   */
  public int getOctave() {
    return noteListType.getOctave();
  }

  /**
   * Gets the length of the NoteList in beats. Does this by returning the latest end time of all
   * the MusicNoteWithDurations in the list.
   * @return the length of the song, in number of beats.
   */
  public int getLength() {
    int latestEndBeat = 0;
    for (MusicNoteWithDuration note : notes) {
      latestEndBeat = Math.max(latestEndBeat, note.getEndBeat());
    }
    return latestEndBeat;
  }

  /**
   * Checks if there is note that starts on a given beat in this list.
   * @param beat the beat number to check.
   * @return true if there is a beat that starts, false if not.
   */
  public boolean hasNoteStartOnBeat(int beat) {
    for (MusicNoteWithDuration note : notes) {
      if (beat == note.getStartBeat()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if there is a note in this list present at the passed beat that wasn't started on the
   * passed beat.
   * @param beat the beat to check.
   * @return true if there is a continuing note on the given beat, false otherwise.
   */
  public boolean hasNoteContinuationOnBeat(int beat) {
    for (MusicNoteWithDuration note : notes) {
      if (beat > note.getStartBeat() && beat <= note.getEndBeat()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sorts the note in the NoteList to make sure they're in the order they appear in the song.
   */
  private void sortNotesByStartTime() {
    Collections.sort(notes, new Comparator<MusicNoteWithDuration>() {
      @Override
      public int compare(MusicNoteWithDuration o1, MusicNoteWithDuration o2) {
        return o1.getStartBeat() - o2.getStartBeat();
      }
    });
  }

  /**
   * Checks to see if the note to add's start and end time overlap with a note already existing in
   * this song.
   * @param noteToAdd the new note to see check if it overlaps with an existing note.
   * @return true if it does, false if it doesn't overlap.
   */
  private boolean noteOverlapsWithNoteInList(MusicNoteWithDuration noteToAdd) {
    for (MusicNoteWithDuration existingNote : notes) {
      if (existingNote.getStartBeat() <= noteToAdd.getEndBeat()
              && noteToAdd.getStartBeat() <= existingNote.getEndBeat()) {
        return true;
      }
    }
    return false;
  }
}
