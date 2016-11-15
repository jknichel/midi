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

  }

  @Override
  public void update() {
    if (!this.paused) {
      this.gui.update();
    }
  }

  @Override
  public void initializeView(int tempo) {
    this.gui.initializeView(tempo);
    this.midi.initializeView(tempo);

  }

  @Override
  public void initializeView(int tempo, Receiver receiver) {
    this.gui.initializeView(tempo, receiver);
    this.midi.initializeView(tempo, receiver);
  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
    this.gui.refresh(noteRange, noteStartingBeats, noteContinuationBeats, songLength);
    this.midi.refresh(noteRange, noteStartingBeats, noteContinuationBeats, songLength);
  }
}
