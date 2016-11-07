package cs3500.music.controller;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

/**
 * Implementation of a MusicEditor controller.
 */
public class MusicEditorController implements IMusicEditorController {
  private IMusicEditorModel model;
  private IMusicEditorView view;

  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() {
    this.view.makeVisible();
    this.view.refresh(model.noteRange(), model.noteStartingBeats(), model.noteContinuationBeats(),
            model.getLength());
  }
}
