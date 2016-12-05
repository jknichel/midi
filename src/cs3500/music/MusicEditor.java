package cs3500.music;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.controller.GuiViewController;
import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Song;
import cs3500.music.provider.CompositeView;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiView;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MusicViewCreator;

/**
 * MusicEditor main() runner class. Creates a model and controller, then creates a view based on
 * passed args (either console, visual, or midi).
 */
public class MusicEditor {
  /**
   * The main() method. Starts up a controller, view, and model and starts running the
   * Music Editor.
   * @param args the console command args from when the program is executed.
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Invalid arguments. Exactly 2 args required.");
    }
    try {
      Readable fileReader = new FileReader(new File(args[0]));
      CompositionBuilder<Song> builder = new Song.Builder();
      IMusicEditorModel model = MusicReader.parseFile(fileReader, builder);
      if (args[1].equalsIgnoreCase("console") || args[1].equalsIgnoreCase("midi")) {
        IMusicEditorView view = MusicViewCreator.create(args[1]);
        IMusicEditorController controller = new MusicEditorController(model, view);
        controller.runMusicEditor();
      } else if (args[1].equalsIgnoreCase("provider")) {
        MusicEditorModel providerModel = new MusicEditorModel(model);
        CompositeView view = new CompositeView(providerModel);
        //IMusicEditorController controller = new MusicEditorController(providerModel, view);
        //controller.runProviderEditor();
      } else {
        GuiView view = (GuiView) MusicViewCreator.create(args[1]);
        IMusicEditorController controller = new GuiViewController(model, view);
        controller.runMusicEditor();
      }
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
  }
}
