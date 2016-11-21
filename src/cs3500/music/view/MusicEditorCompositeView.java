package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import cs3500.music.model.MusicNote;

public class MusicEditorCompositeView implements GuiView {
  private GuiView gui = new MusicEditorGuiView();
  private MidiView midi = new MidiView();
  private Sequencer sequencer;

  @Override
  public void addKeyListener(KeyListener k) {
    this.gui.addKeyListener(k);
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.gui.addMouseListener(m);
  }

  @Override
  public void plugInAddNoteActionListener(ActionListener listener) {
    this.gui.plugInAddNoteActionListener(listener);
  }

  @Override
  public void plugInRemoveNoteActionListener(ActionListener listener) {
    this.gui.plugInRemoveNoteActionListener(listener);
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
  public void pauseResume() {
    if (this.sequencer.isRunning()) {
      pause();
    } else {
      resume();
    }
  }

  @Override
  public void showEditScreen() {
    this.gui.showEditScreen();
  }

  @Override
  public String getEditText() {
    return this.gui.getEditText();
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
                             int tempo) {
    try {
      this.sequencer = MidiSystem.getSequencer();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.gui.initializeView(noteRange, noteStartingBeats, noteContinuationBeats, songLength,
            tempo);
    this.midi.initializeView(noteRange, noteStartingBeats, noteContinuationBeats, songLength,
            tempo, this.sequencer);
    this.sequencer.addMetaEventListener(meta -> {
      if (meta.getType() == 47) {
        sequencer.close();
      } else if (meta.getType() == 6) {
        refresh((int) sequencer.getTickPosition());
      }
    });
  }
}
