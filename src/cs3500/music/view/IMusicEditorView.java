package cs3500.music.view;

import java.util.List;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicNote;

/**
 * The view interface for the music editor. Should implement all methods the controller needs to
 * invoke on the view.
 */
public interface IMusicEditorView {
  /**
   * Initialize the view. Should be called in a controller's initialization method after the view
   * has already been constructed.
   */
  void initializeView(int tempo);

  /**
   * Command for the view to redraw itself. Takes in a bunch of info about the state of the model
   * in order to perform its task.
   * @param noteRange a list of notes from lowest to highest note.
   * @param noteStartingBeats a map mapping each beat to a note that starts on it.
   * @param noteContinuationBeats a map mapping each beat to a note that continues through it.
   */
  void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
               Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength);
}
