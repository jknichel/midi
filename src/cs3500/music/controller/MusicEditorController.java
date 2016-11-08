package cs3500.music.controller;

import javax.sound.midi.Receiver;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

/**
 * Implementation of a MusicEditor controller.
 */
public class MusicEditorController implements IMusicEditorController {
  private IMusicEditorModel model;
  private IMusicEditorView view;
  private Receiver mockReceiver;

  /**
   * Public constructor based on a view and a model.
   * @param model the model to be used with this controller.
   * @param view the view to be used with this controller.
   */
  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Constructor with a mock midi receiver for testing.
   * @param model the model to be used with this controller.
   * @param view the view to be used with this controller.
   * @param mockReceiver the mock Midi receiver.
   */
  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view,
                               Receiver mockReceiver) {
    this.model = model;
    this.view = view;
    this.mockReceiver = mockReceiver;
  }

  @Override
  public void go() {
    if (this.mockReceiver == null) {
      this.view.initializeView(model.getTempo());
    } else {
      this.view.initializeView(model.getTempo(), this.mockReceiver);
    }
    this.view.refresh(model.noteRange(), model.noteStartingBeats(),
            model.noteContinuationBeats(), model.getLength());
  }
}
