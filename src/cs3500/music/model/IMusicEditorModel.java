package cs3500.music.model;

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
   * Get the tempo of the song.
   * @return the tempo.
   */
  int getTempo();

  /**
   * Method to add a note to the song.
   * @param start the start beat of the note in the song.
   * @param end the end beat of the note in the song.
   * @param instrument the instrument of the note in the song.
   * @param pitch the pitch of the note in the song.
   * @param octave the octave of the note in the song.
   * @param volume the volume of the note in the song.
   */
  void addNote(int start, int end, int instrument, Pitches pitch, int octave, int volume);

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
   * Return a list of all notes in a song that continue through the given beat.
   * This is new from HW05.
   * @return list of notes continuing through the given beat.
   */
  Map<Integer, List<MusicNote>> noteContinuationBeats();
}
