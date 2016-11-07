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
  int tempo;

  @Override
  public void initializeView(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
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

      // Translate song length to milliseconds, and let the program execute that long.
      Thread.sleep(songLength * tempo / 1000);
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
      onMsg.setMessage(ShortMessage.NOTE_ON, 10, midiCompatiblePitch, note.getVolume());
      offMsg.setMessage(ShortMessage.NOTE_OFF, 10, midiCompatiblePitch, note.getVolume());

      receiver.send(onMsg, beat * tempo);
      receiver.send(offMsg, (beat + note.getTotalDuration()) * tempo);
    } catch (InvalidMidiDataException e) {}
  }
}
