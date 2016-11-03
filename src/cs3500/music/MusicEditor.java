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
    model.addNote(Pitches.C, 4, 0, 11);
    model.addNote(Pitches.D_SHARP, 4, 0, 5);
    model.addNote(Pitches.C, 6, 5, 3);
    IMusicEditorView view = new MusicEditorConsoleView();
    IMusicEditorController controller = new MusicEditorController(model, view);
    controller.go();
  }
}
