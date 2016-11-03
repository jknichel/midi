package cs3500.music.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the interface for the MusicEditor model.
 */
public interface IMusicEditorModel {
  /**
   * Get the number of beats in the song.
   * @return the value of the last ending beat in the song.
   */
  int getLength();

  /**
   * Method to add a note to the song in the editor.
   * @param pitch the pitch of the note to be added.
   * @param octave the octave of the note to be added.
   * @param start the start beat of the note to be added.
   * @param duration the duration of the note to be added.
   * @return true if the note was added successfully, false if it was not.
   */
  boolean addNote(Pitches pitch, int octave, int start, int duration);

  /**
   * Method to remove a note in the song in the editor.
   * @param pitch the pitch of the note to be removed.
   * @param octave the octave of the note to be removed.
   * @param start the start beat of the note to be removed.
   * @param end the end beat of the note to be removed.
   * @return true if the note was removed sucessfully, false if it was not.
   */
  boolean removeNote(Pitches pitch, int octave, int start, int end);

  /**
   * Get a string representation of the current state of the Music Editor. This will be in the form
   * of columns of notes with markings indicating the beats they're played on.
   * @return string representation of the state of the song.
   */
  //String getMusicEditorState();

  /**
   * Get a list from the lowest to highest note in a song.
   * This is new from HW05.
   * @return the list containing all notes in the range of the song.
   */
  List<MusicNote> noteRange();

  /**
   * Return a HashMap mapping a beat to all notes that start on that beat.
   * This is new from HW05.
   * @return HashMap mapping a beat to List of notes that start on that beat.
   */
  Map<Integer, List<MusicNote>> noteStartingBeats();

  /**
   * Return a lit of all notes in a song that continue through the given beat.
   * This is new from HW05.
   * @return list of notes continuing through the given beat.
   */
  Map<Integer, List<MusicNote>> noteContinuationBeats();
}
