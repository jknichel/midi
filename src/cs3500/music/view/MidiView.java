package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import cs3500.music.model.MusicNote;

/**
 * Implements the IMusicEditorView interface to play a song through MIDI.
 */
public class MidiView implements IMusicEditorView {
  /**
   * Stores the tempo of the song in
   */
  int tempo;

  public StringBuilder log = new StringBuilder("");

  @Override
  public void initializeView(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
    /*try {
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();

      Sequence sequence = new Sequence(Sequence.PPQ, 1);

      Track track = sequence.createTrack();

      for (int i = 0; i <= songLength; i++) {
        if (noteStartingBeats.containsKey(i)) {
          for (MusicNote note : noteStartingBeats.get(i)) {
            addMusicNoteToTrack(i, note, track);
          }
        }
      }

      sequencer.setSequence(sequence);
      sequencer.setTempoInMPQ(this.tempo);
      sequencer.setLoopCount(0);

      sequencer.start();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        sequencer.stop();
        return;
      }
      sequencer.stop();
      return;
    } catch (MidiUnavailableException e) {
      return;
    } catch (InvalidMidiDataException e) {
      return;
    }*/

    Sequencer seq;
    Transmitter seqTrans;
    Synthesizer synth;
    Receiver synthRcvr;

    try {
      seq = MidiSystem.getSequencer();
      seqTrans = seq.getTransmitter();
      synth = MidiSystem.getSynthesizer();
      synthRcvr = synth.getReceiver();
      seqTrans.setReceiver(synthRcvr);
      synth.open();

      for (int i = 0; i <= songLength; i++) {
        if (noteStartingBeats.containsKey(i)) {
          for (MusicNote note : noteStartingBeats.get(i)) {
            playNote(i, note, synthRcvr);
          }
        }
      }

      Thread.sleep(100000);
    } catch (MidiUnavailableException e) {
      // handle or throw exception
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void addMusicNoteToTrack(int beat, MusicNote note, Track track) {
    ShortMessage onMsg = new ShortMessage();
    ShortMessage offMsg = new ShortMessage();

    int midiCompatiblePitch = (note.getPitch().getScaleIndex() - 1) + (note.getOctave() * 12) + 12;

    try {
      onMsg.setMessage(ShortMessage.NOTE_ON, 0, midiCompatiblePitch, note.getVolume());
      offMsg.setMessage(ShortMessage.NOTE_OFF, 0, midiCompatiblePitch, note.getVolume());

      track.add(new MidiEvent(onMsg, beat));
      track.add(new MidiEvent(offMsg, beat + note.getTotalDuration()));
    } catch (InvalidMidiDataException e) {}
  }

  public void playNote(int beat, MusicNote note, Receiver receiver) {
    ShortMessage onMsg = new ShortMessage();
    ShortMessage offMsg = new ShortMessage();

    int midiCompatiblePitch = (note.getPitch().getScaleIndex() - 1) + (note.getOctave() * 12) + 12;

    try {
      onMsg.setMessage(ShortMessage.NOTE_ON, 10, midiCompatiblePitch, note.getVolume());
      offMsg.setMessage(ShortMessage.NOTE_OFF, 10, midiCompatiblePitch, note.getVolume());

      receiver.send(onMsg, beat * tempo);
      receiver.send(offMsg, (beat + note.getTotalDuration()) * tempo);
    } catch (InvalidMidiDataException e) {}
  }
}
