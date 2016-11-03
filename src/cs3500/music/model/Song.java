package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An object to represent a song. A song consists of NoteLists which hold all of the notes of the
 * song. It implements the IMusicEditorModel interface.
 */
public class Song implements IMusicEditorModel {
  /**
   * A list of the NoteLists containing the component notes of the song.
   */
  private final ArrayList<NoteList> noteLists = new ArrayList<>();

  /**
   * The length of the song in beats. This is not final because it can change as the song is
   * edited.
   */
  private int length;

  /**
   * Default constructor. Creates a song with 0 length, and an empty list of NoteLists.
   */
  public Song() {
    this.length = 0;
  }

  /**
   * Method to add a note based on passed parameters.
   * @param pitch pitch of note to add to song.
   * @param octave octave of note to add to song.
   * @param start start beat of note to add to song.
   * @param duration duration in beats of note to add to song.
   */
  public boolean addNote(Pitches pitch, int octave, int start, int duration) {
    MusicNoteWithDuration noteToAdd = new MusicNoteWithDuration(pitch, octave, start, duration);
    return addNote(noteToAdd);
  }

  /**
   * Add a note to the song. This takes in a generic MusicNote and the start beat and beat duration
   * as parameters.
   * @param note MusicNote to add to the song.
   * @param start the starting beat of the note in the song.
   * @param duration the duration in beats of the note in the song.
   */
  public boolean addNote(MusicNote note, int start, int duration) {
    MusicNoteWithDuration noteToAdd = new MusicNoteWithDuration(note.getPitch(), note.getOctave(),
            start, duration);
    return addNote(noteToAdd);
  }

  /**
   * Add a note to the song. Will add the note to the matching list, if it exists. Otherwise it
   * will create a matching list and add the note to it and then sort noteLists by pitch and octave
   * to preserve the correct order.
   * @param note the MusicNoteWithDuration to add to the song.
   */
  public boolean addNote(MusicNoteWithDuration note) {
    Pitches pitch = note.getPitch();
    int octave = note.getOctave();
    int noteListIndex;

    try {
      noteListIndex = findApplicableNoteListIndex(pitch, octave);
    } catch (IllegalArgumentException e) {
      noteLists.add(new NoteList(note));
      sortNoteListsByPitchAndOctave();
      updateLength();
      return true;
    }
    try {
      noteLists.get(noteListIndex).addNote(note);
    } catch (IllegalArgumentException e) {
      return false;
    }
    updateLength();
    return true;
  }

  /**
   * Removes a note matching the passed pitch, octave, start beat, and end beat from the song. If
   * removes the only remaining note from a list, it removes that list.
   * @param pitch pitch of the note to be removed.
   * @param octave octave of the note to be removed.
   * @param start start beat of the note to be removed.
   * @param end end beat of the note to be removed.
   */
  public boolean removeNote(Pitches pitch, int octave, int start, int end) {
    int noteListIndex;

    try {
      noteListIndex = findApplicableNoteListIndex(pitch, octave);
    } catch (IllegalArgumentException e) {
      return false;
    }
    noteLists.get(noteListIndex).removeNote(start, end);
    if (noteLists.get(noteListIndex).getLength() == 0) {
      noteLists.remove(noteListIndex);
    }
    updateLength();
    return true;
  }

  /**
   * Getter for the length of the song.
   * @return length of the song in beats.
   */
  public int getLength() {
    return length;
  }

  /**
   * Combines two songs to play both of their notes consecutively (one after another).
   * This is new from HW05.
   * @param s the song to be played after the given song
   */
  public void combineConsec(Song s){
    int firstSongLength = this.getLength();
    for (NoteList nl : s.noteLists) {
      for (MusicNoteWithDuration n : nl.getNotes()) {
        n.delay(firstSongLength);
        this.addNote(n);
      }
    }
  }

  /**
   * Combines this Song with Song s
   * This is new from HW05.
   * @param s the song to have its notes added to the given song
   */
  public void combineSimul(Song s) {
    for (NoteList nl : s.noteLists) {
      nl.getNotes().forEach(this::addNote);
    }
  }

  @Override
  public List<MusicNote> noteRange() {
    List<MusicNote> noteRange = new ArrayList<>();
    MusicNote lowest = lowestNote();
    MusicNote highest = highestNote();
    MusicNote current = lowest;

    while ((current.getPitch().getScaleIndex() + (current.getOctave() * 12))
            <= (highest.getPitch().getScaleIndex() + (highest.getOctave() * 12))) {
      noteRange.add(current);
      current = current.nextNote();
    }
    return noteRange;
  }

  @Override
  public Map<Integer, List<MusicNote>> noteStartingBeats() {
    Map<Integer, List<MusicNote>> noteStartingBeats = new HashMap<>();

    for (int i = 0; i <= getLength(); i++) {
      List<MusicNote> startsAtBeat = new ArrayList<>();
      for (NoteList list : noteLists) {
        if (list.hasNoteStartOnBeat(i)) {
          startsAtBeat.add(list.noteListType);
        }
      }
      noteStartingBeats.put(i, startsAtBeat);
    }

    return noteStartingBeats;
  }

  @Override
  public Map<Integer, List<MusicNote>> noteContinuationBeats() {
    HashMap<Integer, List<MusicNote>> noteContinuationBeats = new HashMap<>();

    for (int i = 0; i <= getLength(); i++) {
      List<MusicNote> continuesAtBeat = new ArrayList<>();
      for (NoteList list : noteLists) {
        if (list.hasNoteContinuationOnBeat(i)) {
          continuesAtBeat.add(list.noteListType);
        }
      }
      noteContinuationBeats.put(i, continuesAtBeat);
    }

    return noteContinuationBeats;
  }

  /**
   * Returns the MusicNote representing the lowest type (pitch and octave) for a NoteList.
   * @return the MusicNote representing the lowest type of NoteList.
   */
  private MusicNote lowestNote() {
    return noteLists.get(0).noteListType;
  }

  /**
   * Returns the MusicNote representing the highest type (pitch and octave) for a NoteList.
   * @return the MusicNote representing the highest type of NoteList.
   */
  private MusicNote highestNote() {
    return noteLists.get(noteLists.size() - 1).noteListType;
  }

  /**
   * Finds the NoteList for the passed pitch and octave.
   * @param pitch target NoteList pitch.
   * @param octave target NoteList octave.
   * @return the integer index of the requested NoteList.
   * @throws IllegalArgumentException if the requested list doesn't exist.
   */
  private int findApplicableNoteListIndex(Pitches pitch, int octave) {
    Pitches listPitch;
    int listOctave;
    for (int i = 0; i < noteLists.size(); i++) {
      listPitch = noteLists.get(i).getPitch();
      listOctave = noteLists.get(i).getOctave();
      if (listPitch == pitch && listOctave == octave) {
        return i;
      }
    }
    throw new IllegalArgumentException("Such list doesn't exist!");
  }

  /**
   * Sorts the NoteLists belonging to this song so that they're in order from the lowest note to
   * the highest note.
   */
  private void sortNoteListsByPitchAndOctave() {
    Collections.sort(noteLists, new Comparator<NoteList>() {
      @Override
      public int compare(NoteList o1, NoteList o2) {
        return (o1.getPitch().getScaleIndex() + (o1.getOctave() * 12))
                - (o2.getPitch().getScaleIndex() + (o2.getOctave() * 12));
      }
    });
  }

  /**
   * Updates the length of the song by finding the latest end beat of all the notes in the song.
   */
  private void updateLength() {
    int currentLength = 0;
    for (NoteList list : noteLists) {
      currentLength = Math.max(currentLength, list.getLength());
    }
    this.length = currentLength;
  }
}
