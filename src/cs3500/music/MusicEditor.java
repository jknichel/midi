package cs3500.music;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.Song;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MusicEditorConsoleView;

/**
 * MusicEditor main() runner class. Creates a model, view, and controller and runs the editor.
 */
public class MusicEditor {
  public static void main(String[] args) {
    File songFile = new File("df-ttfaf.txt");
    try {
      Readable fileReader = new FileReader(songFile);
      CompositionBuilder<Song> builder = new Song.Builder();
      Song model = MusicReader.parseFile(fileReader, builder);
      IMusicEditorView view = new MusicEditorConsoleView();
      IMusicEditorController controller = new MusicEditorController(model, view);
      controller.go();
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
  }
}
