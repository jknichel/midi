package cs3500.music.controller;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

/**
 * Implementation of a MusicEditor controller.
 */
public class MusicEditorController implements IMusicEditorController {
  /**
   * Holds the model to use with by this controller.
   */
  private IMusicEditorModel model;

  /**
   * Holds the view to be used by this controller.
   */
  private IMusicEditorView view;

  /**
   * Public constructor based on a view and a model.
   * @param model the model to be used with this controller.
   * @param view the view to be used with this controller.
   */
  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void runMusicEditor() {
    this.view.initializeView(model.noteRange(), model.noteStartingBeats(),
            model.noteContinuationBeats(), model.getLength(), model.getTempo());
  }
}
