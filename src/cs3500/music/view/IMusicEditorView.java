package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.Receiver;

import cs3500.music.model.MusicNote;

/**
 * The view interface for the music editor. Should implement all methods the controller needs to
 * invoke on the view.
 */
public interface IMusicEditorView {
  /**
   * Draw up an initial version of the view based on a bunch of info from the model.
   * @param noteRange the range of notes in the song.
   * @param noteStartingBeats map of beats to notes that start at that beat.
   * @param noteContinuationBeats map of beats to notes that continue through that beat.
   * @param songLength the length of the song in beats.
   * @param tempo the tempo of the song in microseconds per beat.
   */
  void initializeView(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                      int tempo);

  /**
   * Refreshes the view based on the beat the song is currently focused on.
   * @param beat the beat to refresh the view around.
   */
  void refresh(int beat);
}
