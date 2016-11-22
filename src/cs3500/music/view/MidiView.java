package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
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
public class MidiView implements IMidiView {
  /**
   * Stores the tempo of the song microseconds per beat.
   */
  private int tempo;

  /**
   * The sequencer to be used for track construction and playback.
   */
  private Sequencer sequencer;

  /**
   * The track that holds the notes of the song for playback.
   */
  private Track track;

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo, Sequencer sequencer) {
    this.sequencer = sequencer;
    initializeView(noteRange, noteStartingBeats, noteContinuationBeats, songLength, tempo);
  }

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo) {
    this.tempo = tempo;
    boolean startImmediatly = false;
    try {
      if (this.sequencer == null) {
        this.sequencer = MidiSystem.getSequencer();
        this.sequencer.addMetaEventListener(meta -> {
          if (meta.getType() == 47) {
            sequencer.close();
          }
        });
        startImmediatly = true;
      }
      Transmitter seqTrans = sequencer.getTransmitter();
      Synthesizer synthesizer = MidiSystem.getSynthesizer();
      Receiver receiver = synthesizer.getReceiver();
      seqTrans.setReceiver(receiver);
      synthesizer.open();
      sequencer.open();
      Sequence sequence = new Sequence(Sequence.PPQ, 1);
      this.track = sequence.createTrack();

      for (int i = 0; i <= songLength; i++) {
        track.add(new MidiEvent(new MetaMessage(6, new byte[1], 1), i));
        if (noteStartingBeats.containsKey(i)) {
          for (MusicNote note : noteStartingBeats.get(i)) {
            addNoteToTrack(i, note);
          }
        }
      }

      this.sequencer.setSequence(sequence);
      if (startImmediatly) {
        resume();
      }
    } catch (MidiUnavailableException | InvalidMidiDataException e) {
      e.printStackTrace();
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

  @Override
  public void refresh(int beat) {
    return;
  }

  @Override
  public void pause() {
    this.sequencer.stop();
  }

  @Override
  public void resume() {
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(this.tempo);
  }

  public List<ShortMessage> getNoteMessages() {
    List<MidiEvent> events = new ArrayList<>();
    for (int i = 0; i < this.track.size(); i++) {
      events.add(this.track.get(i));
    }
    List<ShortMessage> noteMessages = events.stream().filter(event ->
            event.getMessage().getStatus() != 255).map(event -> (ShortMessage) event.getMessage()).
            collect(Collectors.toList());
    return noteMessages;
  }
}
