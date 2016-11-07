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
import cs3500.music.view.MusicViewCreator;

/**
 * MusicEditor main() runner class. Creates a model and controller, then creates a view based on
 * passed args (either console, visual, or midi).
 */
public class MusicEditor {
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Invalid arguments. Exactly 2 args required.");
    }
    try {
      Readable fileReader = new FileReader(new File(args[0]));
      CompositionBuilder<Song> builder = new Song.Builder();
      Song model = MusicReader.parseFile(fileReader, builder);
      IMusicEditorView view = new MusicViewCreator().create(args[1]);
      IMusicEditorController controller = new MusicEditorController(model, view);
      controller.go();
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
    return;
  }
}
