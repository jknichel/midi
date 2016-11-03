package cs3500.music.view;

import java.awt.*;
import java.util.List;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicNote;

public class MusicEditorMidiView implements IMusicEditorView {
  IMusicEditorModel model;

  @Override
  public void makeVisible() {

  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {

  }
}
