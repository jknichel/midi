package cs3500.music.controller;

import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.GuiView;

public class GuiViewController implements IMusicEditorController {
  private IMusicEditorModel model;
  private GuiView view;
  private boolean isPaused = false;

  public GuiViewController(IMusicEditorModel model, GuiView view) {
    this.model = model;
    this.view = view;
    addKeyboardListener();

  }

  public void addKeyboardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    KeyboardHandler k = new KeyboardHandler();
    k.setKeyTypedMap(keyTypes);
    k.setKeyPressedMap(keyPresses);
    k.setKeyReleasedMap(keyReleases);

    view.addKeyListener(k);
  }

  private final Runnable pause = () -> {
    this.view.pause();
    this.isPaused = !this.isPaused;
  };

  private void tick() {
    // not sure yet how to make this call update at a constant rate based on the tempo
    if (!isPaused) {
      this.view.update();
    }
  }

  @Override
  public void runMusicEditor() {
    this.view.initializeView(this.model.getTempo());
    this.tick();
    this.view.refresh(model.noteRange(), model.noteStartingBeats(),
            model.noteContinuationBeats(), model.getLength());
  }
}
