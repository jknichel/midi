package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cs3500.music.model.MusicNote;

/**
 * Implements the IMusicEditorView interface to play a song through MIDI.
 */
public class MidiView implements IMusicEditorView {
  @Override
  public void makeVisible() {

  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
    try {
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
      sequencer.setTempoInMPQ(15000);
      sequencer.setLoopCount(0);

      sequencer.start();
      try {
        Thread.sleep(100000);
      } catch (InterruptedException e) {
      }
      sequencer.stop();
    } catch (MidiUnavailableException e) {
      return;
    } catch (InvalidMidiDataException e) {
      return;
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
}
