package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Receiver;

import cs3500.music.model.MusicNote;

public class MusicEditorCompositeView implements GuiView {
  private GuiView gui = new MusicEditorGuiView();
  private MidiView midi = new MidiView();
  private boolean paused = false;


  @Override
  public void addKeyListener(KeyListener k) {
    this.gui.addKeyListener(k);
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.gui.addMouseListener(m);
  }

  @Override
  public void pause() {
    this.midi.pause();
  }

  @Override
  public void resume() {
    this.midi.resume();
  }

  @Override
  public void update() {
    if (!this.paused) {
      this.gui.update();
    }
  }

  @Override
  public void refresh(int beat) {
    this.gui.refresh(beat);
    this.midi.refresh(beat);
  }

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo, Receiver receiver) {
    throw new IllegalArgumentException("This constructor can't be used for this view.");
  }

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo) {
    this.gui.initializeView(noteRange, noteStartingBeats, noteContinuationBeats, songLength,
            tempo);
    this.midi.initializeView(noteRange, noteStartingBeats, noteContinuationBeats, songLength,
            tempo);
  }
}
