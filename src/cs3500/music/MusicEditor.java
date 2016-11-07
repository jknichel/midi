package cs3500.music;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Pitches;
import cs3500.music.model.Song;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MusicEditorConsoleView;
import cs3500.music.view.MusicEditorGuiView;

/**
 * MusicEditor main() runner class. Creates a model, view, and controller and runs the editor.
 */
public class MusicEditor {
  public static void main(String[] args) {
    File songFile = new File("mary-little-lamb.txt");
    try {
      Readable fileReader = new FileReader(songFile);
      CompositionBuilder<Song> builder = new Song.Builder();
      Song model = MusicReader.parseFile(fileReader, builder);
      IMusicEditorView view = new MusicEditorGuiView();
      IMusicEditorController controller = new MusicEditorController(model, view);
      controller.go();
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }

    /*
    IMusicEditorModel model = new Song();
    model.addNote(4, 32, 1, Pitches.C, 4, 3); // start end instrument pitch octave volume
    model.addNote(20, 24, 1, Pitches.D, 5, 2);
    model.addNote(7, 28, 1, Pitches.A_SHARP, 5, 2);
    model.addNote(0, 12, 1, Pitches.D_SHARP, 4, 2);
    model.addNote(2, 10, 1, Pitches.B, 4, 2);
    model.addNote(11, 32, 1, Pitches.F_SHARP, 5, 2);
    model.addNote(3, 8, 1, Pitches.E, 6, 2);

    IMusicEditorView view = new MusicEditorGuiView();
    IMusicEditorController controller = new MusicEditorController(model, view);
    controller.go();
    */
  }
}