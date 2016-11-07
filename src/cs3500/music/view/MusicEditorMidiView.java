package cs3500.music.view;

import java.awt.*;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicNote;

public class MusicEditorMidiView implements IMusicEditorView {
  private final Synthesizer synth;
  private final Receiver receiver;
  IMusicEditorModel model;

  public MusicEditorMidiView() {
    Synthesizer tempSynth = null;
    Receiver tempReceiver = null;
    try {
      tempSynth = MidiSystem.getSynthesizer();
      tempReceiver = tempSynth.getReceiver();
      tempSynth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.synth = tempSynth;
    this.receiver = tempReceiver;
  }


  @Override
  public void makeVisible() {

  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {

  }
}
