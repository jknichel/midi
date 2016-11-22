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
  /**
   * Holds the GuiView for this composite view.
   */
  private GuiView gui = new MusicEditorGuiView();

  /**
   * Holds the MidiView for this composite view.
   */
  private MidiView midi = new MidiView();

  /**
   * Holds the sequencer for this view to plug into the MidiView to keep sync between the two
   * component views.
   */
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
  public void upScroll() {
    this.gui.upScroll();
  }

  @Override
  public void downScroll() {
    this.gui.downScroll();
  }

  @Override
  public void leftScroll() {
    this.gui.leftScroll();
  }

  @Override
  public void rightScroll() {
    this.gui.rightScroll();
  }

  @Override
  public void homeKey() {
    this.gui.homeKey();
  }

  @Override
  public void endKey(int songLength) {
    this.gui.endKey(songLength);
  }

  @Override
  public void showWarningScreen(String text) {
    this.gui.showWarningScreen(text);
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
    addRedLineTrackerListener();
  }

  @Override
  public void redrawForSongChange(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                           Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                           int tempo) {
    long currentTick = this.sequencer.getTickPosition();
    try {
      this.sequencer = MidiSystem.getSequencer();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.gui.redrawForSongChange(noteRange, noteStartingBeats, noteContinuationBeats, songLength,
            tempo);
    this.midi.initializeView(noteRange, noteStartingBeats, noteContinuationBeats, songLength,
            tempo, this.sequencer);
    if (currentTick > this.sequencer.getTickLength()) {
      currentTick = this.sequencer.getTickLength();
    }
    this.sequencer.setTickPosition(currentTick);
    this.refresh((int) this.sequencer.getTickPosition());
    addRedLineTrackerListener();
  }

  /**
   * A method to add the MetaEventListener to the sequencer so that the GUI view can tell when the
   * Midi playback moves to another beat.
   */
  private void addRedLineTrackerListener() {
    this.sequencer.addMetaEventListener(meta -> {
      if (meta.getType() == 47) {
        this.sequencer.setTickPosition(0);
        pause();
        refresh((int) this.sequencer.getTickPosition());
      } else if (meta.getType() == 6) {
        refresh((int) sequencer.getTickPosition());
      }
    });
  }
}
