package cs3500.music;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Pitches;
import cs3500.music.model.Song;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MusicEditorConsoleView;

/**
 * MusicEditor main() runner class. Creates a model, view, and controller and runs the editor.
 */
public class MusicEditor {
  public static void main(String[] args) {
    IMusicEditorModel model = new Song();
    model.addNote(0, 10, 1, Pitches.C, 4, 10);
    model.addNote(0, 4, 1, Pitches.D_SHARP, 4, 10);
    model.addNote(5, 7, 1, Pitches.C, 6, 10);
    IMusicEditorView view = new MusicEditorConsoleView();
    IMusicEditorController controller = new MusicEditorController(model, view);
    controller.go();
  }
}
