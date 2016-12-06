package cs3500.music.model;

import java.util.List;

/**
 * Class to translate between our Song class and their MusicEditorModel class that is expected by
 * their view.
 */
public interface IModelTranslator {
  List<Note> getNotes();

  /**
   * Gets the last beat that is played in a song.
   * @return the beat as a number that is the last one played in the song
   */
  int getLastBeat();

  /**
   * Gets the lowest note in a song based on pitch and octave.
   * @return the lowest Note
   */
  Note getLowNote();

  /**
   * Gets the highest note in a song based on pitch and octave.
   * @return the highest note
   */
  Note getHighNote();

  /**
   * Gets the tempo of a song.
   * @return the tempos of a song
   */
  int getTempo();
}
