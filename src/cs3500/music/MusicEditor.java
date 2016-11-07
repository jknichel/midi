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
import cs3500.music.view.MusicEditorGuiView;
import cs3500.music.view.MusicViewCreator;
import cs3500.music.view.MidiView;
import cs3500.music.view.MusicEditorConsoleView;

/**
 * MusicEditor main() runner class. Creates a model, view, and controller and runs the editor.
 */
public class MusicEditor {
  public static void main(String[] args) {
    String fileName;
    String viewType;

    if (args.length == 1) {
      fileName = args[0];
      viewType = "console";
    } else {
      fileName = args[0];
      viewType = args[1];
    }

    try {
      Readable fileReader = new FileReader(new File(fileName));
      CompositionBuilder<Song> builder = new Song.Builder();
      Song model = MusicReader.parseFile(fileReader, builder);
      IMusicEditorView view = new MusicViewCreator().create(viewType);
      IMusicEditorController controller = new MusicEditorController(model, view);
      controller.go();
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
  }
}
