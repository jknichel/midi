package cs3500.music.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.music.util.*;

/**
 * An object to represent a song. A song consists of NoteLists which hold all of the notes of the
 * song. It implements the IMusicEditorModel interface.
 */
public class Song implements IMusicEditorModel {
  private final Map<Integer, List<MusicNote>> beatsToNoteStarts = new HashMap<>();

  private final Map<Integer, List<MusicNote>> beatsToNoteContinuations = new HashMap<>();

  /**
   * The tempo of the song.
   */
  private int tempo;

  /**
   * The length of the song in beats. This is not final because it can change as the song is
   * edited.
   */
  private int length;

  public static final class Builder implements CompositionBuilder<Song> {

    @Override
    public Song build() {
      return null;
    }

    @Override
    public CompositionBuilder<Song> setTempo(int tempo) {
      return null;
    }

    @Override
    public CompositionBuilder<Song> addNote(int start, int end, int instrument, int pitch, int volume) {
      return null;
    }
  }

  /**
   * Default constructor. Creates a song with 0 length with no notes in it.
   */
  public Song() {
    this.length = 0;
  }

  @Override
  public void addNote(int start, int end, int instrument, Pitches pitch, int octave,
                         int volume) {
    List<MusicNote> tempList;
    MusicNote noteToAdd = new MusicNote(pitch, octave, instrument, volume);
    if (beatsToNoteStarts.containsKey(start)) {
      tempList = beatsToNoteStarts.get(start);
      tempList.add(noteToAdd);
      beatsToNoteStarts.put(start, tempList);
    } else {
      tempList = new ArrayList<>();
      tempList.add(noteToAdd);
      beatsToNoteStarts.put(start, tempList);
    }

    for (int i = start; i <= end; i++) {
      if (beatsToNoteContinuations.containsKey(i)) {
        tempList = beatsToNoteContinuations.get(i);
        tempList.add(noteToAdd);
        beatsToNoteContinuations.put(i, tempList);
      } else {
        tempList = new ArrayList<>();
        tempList.add(noteToAdd);
        beatsToNoteContinuations.put(i, tempList);
      }
    }

    updateLength();
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
   *
  public void combineConsec(Song s){
    int firstSongLength = this.getLength();
    for (NoteList nl : s.noteLists) {
      for (MusicNoteInSong n : nl.getNotes()) {
        n.delay(firstSongLength);
        this.addNote(n);
      }
    }
  }*/

  /**
   * Combines this Song with Song s
   * This is new from HW05.
   * @param s the song to have its notes added to the given song
   *
  public void combineSimul(Song s) {
    for (NoteList nl : s.noteLists) {
      nl.getNotes().forEach(this::addNote);
    }
  } */

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
    return beatsToNoteStarts;
  }

  @Override
  public Map<Integer, List<MusicNote>> noteContinuationBeats() {
    return beatsToNoteContinuations;
  }

  /**
   * Returns the MusicNote representing the lowest type (pitch and octave) for a NoteList.
   * @return the MusicNote representing the lowest type of NoteList.
   */
  private MusicNote lowestNote() {
    // Start with a random note, compare all others to it to find the lowest in song.
    MusicNote lowestNote = beatsToNoteStarts.get(0).get(0);

    for (List<MusicNote> starts : beatsToNoteStarts.values()) {
      for (MusicNote note : starts) {
        if ((note.getPitch().getScaleIndex() + note.getOctave() * 12) <
                (lowestNote.getPitch().getScaleIndex() + lowestNote.getOctave() * 12)) {
          lowestNote = note;
        }
      }
    }
    return lowestNote;
  }

  /**
   * Returns the MusicNote representing the highest type (pitch and octave) for a NoteList.
   * @return the MusicNote representing the highest type of NoteList.
   */
  private MusicNote highestNote() {
    // Start with a random note, compare all others to it to find the highest in song.
    MusicNote highestNote = beatsToNoteStarts.get(0).get(0);

    for (List<MusicNote> starts : beatsToNoteStarts.values()) {
      for (MusicNote note : starts) {
        if ((note.getPitch().getScaleIndex() + note.getOctave() * 12) >
                (highestNote.getPitch().getScaleIndex() + highestNote.getOctave() * 12)) {
          highestNote = note;
        }
      }
    }
    return highestNote;
  }

  /**
   * Updates the length of the song by finding the latest end beat of all the notes in the song.
   */
  private void updateLength() {
    Integer length = 0;
    Set<Integer> beats = beatsToNoteContinuations.keySet();
    for (Integer beat : beats) {
      if (beat > length) {
        length = beat;
      }
    }
    this.length = length;
  }
}
