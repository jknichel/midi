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
   * Add the notes from the add-note queue to the model.
   */
  private void addNotes() {
    for (String s : GuiView.addNoteQueue) {
      System.out.println(s);
      String[] a = s.split(" ");

      Pitches pitch = null;
      for (Pitches p : Pitches.values()) {
        if (a[0].equals(p.toString())) {
          pitch = p;
        }
      }
      int octave = Integer.valueOf(a[1]);
      int start = Integer.valueOf(a[2]);
      int end = Integer.valueOf(a[3]);
      int instrument = Integer.valueOf(a[4]);
      int volume = Integer.valueOf(a[5]);

      this.model.addNote(start, end, instrument, pitch, octave, volume);
    }
  }

  /**
   * Remove the notes from model that are present in the remove-note queue.
   */
  private void removeNotes(){
    for (String s : GuiView.removeNoteQueue){
      String[] a = s.split(" ");

      Pitches pitch = null;
      for (Pitches p : Pitches.values()) {
        if (a[0].equals(p.toString())) {
          pitch = p;
        }
      }
      int start = Integer.valueOf(a[1]);
      int octave = Integer.valueOf(a[2]);

      this.model.removeNote(start, pitch, octave);
    }
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
  }
}
