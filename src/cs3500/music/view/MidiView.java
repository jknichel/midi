package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

import cs3500.music.model.MusicNote;

/**
 * Implements the IMusicEditorView interface to play a song through MIDI.
 */
public class MidiView implements IMusicEditorView {
  /**
   * Stores the tempo of the song in
   */
  private int tempo;

  /**
   * Holds a special mock receiver, to be used for testing.
   */
  private Receiver mockReceiver;

  @Override
  public void initializeView(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void initializeView(int tempo, Receiver receiver) {
    this.tempo = tempo;
    this.mockReceiver = receiver;
  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
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
      if (this.mockReceiver == null) {
        synthRcvr = synth.getReceiver();
      } else {
        synthRcvr = this.mockReceiver;
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
    } catch (InvalidMidiDataException e) {}
  }
}
