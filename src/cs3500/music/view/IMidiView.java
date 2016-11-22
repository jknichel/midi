package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

import cs3500.music.model.MusicNote;

/**
 * A sub-interface of IMidiView that provides special methods only applicable to a Midi view.
 */
public interface IMidiView extends IMusicEditorView {
  /**
   * This special initializeView method allows for a custom Sequencer to be passed into the view.
   * This enables a controller to pass its own sequencer in to gain insight about the status of
   * playback.
   * @param noteRange the range of notes in the song.
   * @param noteStartingBeats map of beats to notes that start at that beat.
   * @param noteContinuationBeats map of beats to notes that continue through that beat.
   * @param songLength the length of the song in beats.
   * @param tempo the tempo of the song in microseconds per beat.
   * @param sequencer the custom sequencer to use for track construction and playback.
   */
  void initializeView(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                      int tempo, Sequencer sequencer);

  /**
   * Pauses Midi playback.
   */
  void pause();

  /**
   * Resumes Midi playback.
   */
  void resume();

  /**
   * Get a list of all note on/off messages from the Midi track. To be used for testing purposes.
   * @return the list of ShortMessages representing note on/off events.
   */
  List<ShortMessage> getNoteMessages();
}
