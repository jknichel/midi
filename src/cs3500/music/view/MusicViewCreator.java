package cs3500.music.view;

import java.util.List;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicNote;

/**
 * Factory class for view creation.
 */
public class MusicViewCreator implements IMusicEditorView {
  private enum ViewType {
    CONSOLE,
    VISUAL,
    MIDI
  }

  /**
   * Method that creates and returns the appropriate view given a designating string.
   * @param view teh string to designate what type of view to create.
   * @return the view that was created
   */
  public static IMusicEditorView create(ViewType view) {
    switch (view) {
      case CONSOLE:
        return new MusicEditorConsoleView();
      case VISUAL:
        return new MusicEditorGuiView();
      case MIDI:
        return new MusicEditorMidiView();
      default:
        break;
    }
    throw new IllegalArgumentException("Invalid View type");
  }

  @Override
  public void makeVisible() {
    return;
  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {

  }
}
