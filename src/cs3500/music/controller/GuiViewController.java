package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Pitches;
import cs3500.music.view.GuiView;

/**
 * Represents a controller for the GUI view.
 */
public class GuiViewController implements IMusicEditorController {
  private IMusicEditorModel model;
  private GuiView view;

  /**
   * Constructs a controller for the GUI view.
   * @param model the model to be used in the controller
   * @param view the view to be used in the controller
   */
  public GuiViewController(IMusicEditorModel model, GuiView view) {
    this.model = model;
    this.view = view;
    addKeyboardListener();
  }

  /**
   * Adds a keyboard listener to the controller to listen for key inputs.
   */
  private void addKeyboardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    KeyboardHandler k = new KeyboardHandler();
    k.setKeyTypedMap(keyTypes);
    k.setKeyPressedMap(keyPresses);
    k.setKeyReleasedMap(keyReleases);

    keyPresses.put(KeyEvent.VK_SPACE, new PausePlay());
    keyPresses.put(KeyEvent.VK_E, new EditScreen());

    view.addKeyListener(k);
  }

  /**
   * Represents a runnable that pauses and plays a piece of music.
   */
  private final class PausePlay implements Runnable {
    public void run() {
      view.pauseResume();
    }
  }

  /**
   * Represents a runnable that brings up the edit screen to add or remove notes.
   */
  private final class EditScreen implements Runnable {
    public void run() {
      view.showEditScreen();
    }
  }

  @Override
  public void runMusicEditor() {
    this.view.initializeView(model.noteRange(), model.noteStartingBeats(),
            model.noteContinuationBeats(), model.getLength(), this.model.getTempo());

    this.view.plugInAddNoteActionListener(actionEvent -> {
      addNote(this.view.getEditText());
      this.view.redrawForSongChange(model.noteRange(), model.noteStartingBeats(),
              model.noteContinuationBeats(), model.getLength(), model.getTempo());
    });

    this.view.plugInRemoveNoteActionListener(actionEvent -> {
      removeNote(this.view.getEditText());
      this.view.redrawForSongChange(model.noteRange(), model.noteStartingBeats(),
              model.noteContinuationBeats(), model.getLength(), model.getTempo());
    });
  }

  /**
   * Method to be called when the Add Note action gets triggered by the view. Takes in a text input
   * field and splits it based on spaces.
   * @param input the input text to translate into a note to add.
   */
  private void addNote(String input) {
    String[] fields = input.split(" ");
    int start = Integer.valueOf(fields[0]);
    int end = Integer.valueOf(fields[1]);
    int instrument = Integer.valueOf(fields[2]);
    Pitches pitch = Pitches.C.getPitchFromScaleIndex(Integer.valueOf(fields[3]));
    int octave = Integer.valueOf(fields[4]);
    int volume = Integer.valueOf(fields[5]);

    this.model.addNote(start, end, instrument, pitch, octave, volume);
  }

  /**
   * Method to be called when the Remove Note action gets triggered by the view. Takes in a text
   * input field and splits it based on spaces.
   * @param input the input text to translate into a note to remove.
   */
  private void removeNote(String input) {
    String[] fields = input.split(" ");
    int start = Integer.valueOf(fields[0]);
    Pitches pitch = Pitches.C.getPitchFromScaleIndex(Integer.valueOf(fields[1]));
    int octave = Integer.valueOf(fields[2]);

    this.model.removeNote(start, pitch, octave);
  }
}
