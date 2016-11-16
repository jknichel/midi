package cs3500.music.view;

import java.lang.invoke.SwitchPoint;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
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
   * Stores the tempo of the song microseconds per beat.
   */
  private int tempo;

  /**
   * Holds a special receiver, only used for testing for now.
   */
  private Receiver receiver;

  private Sequencer sequencer;

  private Sequence sequence;

  private Track track;

  @Override
  public void refresh(int beat) {
    return;
  }

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo, Receiver receiver) {
    this.tempo = tempo;
    this.receiver = receiver;

    this.tempo = tempo;
    Sequencer seq;
    Transmitter seqTrans;
    Synthesizer synth;
    Receiver synthRcvr;
    /**
     * Calculate the time to sleep the thread by multiplying the number of beats by the time of
     * each beat in milliseconds which is calculated by dividing the tempo (in microseconds/beat)
     * by 1000.
     */
    int timeToSleepThread = songLength * tempo / 1000;

    try {
      seq = MidiSystem.getSequencer();
      seqTrans = seq.getTransmitter();
      synth = MidiSystem.getSynthesizer();
      if (this.receiver == null) {
        synthRcvr = synth.getReceiver();
      } else {
        synthRcvr = this.receiver;
        timeToSleepThread = 0;
      }
      seqTrans.setReceiver(synthRcvr);
      synth.open();

      for (int i = 0; i <= songLength; i++) {
        if (noteStartingBeats.containsKey(i)) {
          for (MusicNote note : noteStartingBeats.get(i)) {
            playNote(i, note, synthRcvr);
          }
        }
      }

      Thread.sleep(timeToSleepThread);
    } catch (MidiUnavailableException e) {
      return;
    } catch (InterruptedException e) {
      return;
    }
  }

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo) {
    this.tempo = tempo;
    try {
      this.sequencer = MidiSystem.getSequencer();
      Transmitter seqTrans = sequencer.getTransmitter();
      Synthesizer synth = MidiSystem.getSynthesizer();
      Receiver receiver = synth.getReceiver();
      seqTrans.setReceiver(receiver);
      synth.open();
      sequencer.open();
      this.sequence = new Sequence(Sequence.PPQ, 1);
      this.track = sequence.createTrack();

      for (int i = 0; i <= songLength; i++) {
        if (i % 4 == 0) {
          MetaMessage indicator = new MetaMessage();
          indicator.setMessage(6, new byte[1], 1);
          track.add(new MidiEvent(indicator, i));
        }
        if (noteStartingBeats.containsKey(i)) {
          for (MusicNote note : noteStartingBeats.get(i)) {
            addNoteToTrack(i, note);
          }
        }
      }

      this.sequencer.setSequence(this.sequence);
      this.sequencer.setTempoInMPQ(tempo);
      this.sequencer.addMetaEventListener(meta -> {
        if (meta.getType() == 47) {
          sequencer.close();
        } else if (meta.getType() == 6) {
          System.out.println("Tick is: " + String.valueOf(sequencer.getTickPosition()));
        }
      });
      this.sequencer.start();

      //for (int i = 0; i < this.track.size(); i++) {
        //MidiEvent event = this.track.get(i);
        //receiver.send(event.getMessage(), event.getTick() * tempo);
      //}
    } catch (MidiUnavailableException | InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  public void pause() {
    this.sequencer.stop();
  }

  public void resume() {
    this.sequencer.start();
  }

  /**
   * Send a note to the given receiver to play at the given timestamp.
   * @param beat the beat the note starts on.
   * @param note the note to play.
   * @param receiver the receiver to send the message to.
   */
  private void playNote(int beat, MusicNote note, Receiver receiver) {
    ShortMessage onMsg = new ShortMessage();
    ShortMessage offMsg = new ShortMessage();

    int midiCompatiblePitch = (note.getPitch().getScaleIndex() - 1) + (note.getOctave() * 12) + 12;

    try {
      onMsg.setMessage(ShortMessage.NOTE_ON, note.getInstrument() % 15, midiCompatiblePitch,
              note.getVolume());
      offMsg.setMessage(ShortMessage.NOTE_OFF, note.getInstrument() % 15, midiCompatiblePitch,
              note.getVolume());

      receiver.send(onMsg, beat * tempo);
      receiver.send(offMsg, (beat + note.getTotalDuration()) * tempo);
    } catch (InvalidMidiDataException e) {
      // Handling could be added here, but for now we can just move onto the next note.
    }
  }

  private void addNoteToTrack(int beat, MusicNote note) {
    ShortMessage onMsg = new ShortMessage();
    ShortMessage offMsg = new ShortMessage();

    int midiCompatiblePitch = (note.getPitch().getScaleIndex() - 1) + (note.getOctave() * 12) + 12;

    try {
      onMsg.setMessage(ShortMessage.NOTE_ON, note.getInstrument() % 15, midiCompatiblePitch,
              note.getVolume());
      offMsg.setMessage(ShortMessage.NOTE_OFF, note.getInstrument() % 15, midiCompatiblePitch,
              note.getVolume());

      track.add(new MidiEvent(onMsg, beat));
      track.add(new MidiEvent(offMsg, beat + note.getTotalDuration()));
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }
}
