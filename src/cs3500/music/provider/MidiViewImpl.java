package cs3500.music.provider;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicNote;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl implements IView {
  private Synthesizer synth;
  private Receiver receiver;
  private List<MusicNote> notes;
  private int lastBeat;
  private int tempo;
  private int currentBeat = 0;

  /**
   * Implements the Midi View.
   */
  public MidiViewImpl() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();

      final MidiChannel[] MC = synth.getChannels();

      Instrument[] instr = synth.getDefaultSoundbank().getInstruments();

    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   * <li>{@link MidiSystem#getSynthesizer()}</li>
   * <li>{@link Synthesizer}
   * <ul>
   * <li>{@link Synthesizer#open()}</li>
   * <li>{@link Synthesizer#getReceiver()}</li>
   * <li>{@link Synthesizer#getChannels()}</li>
   * </ul>
   * </li>
   * <li>{@link Receiver}
   * <ul>
   * <li>{@link Receiver#send(MidiMessage, long)}</li>
   * <li>{@link Receiver#close()}</li>
   * </ul>
   * </li>
   * <li>{@link MidiMessage}</li>
   * <li>{@link ShortMessage}</li>
   * <li>{@link MidiChannel}
   * <ul>
   * <li>{@link MidiChannel#getProgram()}</li>
   * <li>{@link MidiChannel#programChange(int)}</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */
  /*
  public void playNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }*/

  /**
   * Plays a note of the given parameters via the midi.
   *
   * @param duration   Duration of the note.
   * @param pitch      Pitch of the note.
   * @param instrument Instrument to be played.
   * @throws InvalidMidiDataException Throws an InvalidMidiDataException
   */
  public void playNote(int duration, int pitch, int instrument) throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument, pitch, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, pitch, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + (duration * tempo));
  }


  /**
   * Plays the entire composition.
   *
   * @throws InvalidMidiDataException Throws an InvalidMidiDataException
   */
  public void playComposition() throws InvalidMidiDataException {
    Timer timer = new Timer();
    currentBeat = 0;
    TimerTask playNotes = new TimerTask() {

      @Override
      public void run() {
        //if(currentBeat == )
        List<MusicNote> playNotes = notes;
        Collections.sort(playNotes, new Comparator<MusicNote>() {
          @Override
          public int compare(MusicNote o1, MusicNote o2) {
            return o1.getBeat() - o2.getBeat();
          }
        });
        for (int note = 0; note < notes.size(); note++) {
          if (notes.get(note).getBeat() > currentBeat) {
            break;
          }
          if (notes.get(note).getBeat() == currentBeat) {
            //play note
            try {
              playNote(notes.get(note).getDuration(), notes.get(note).getTone().getNumericTone(),
                      notes.get(note).getInstrument());
            } catch (InvalidMidiDataException e) {
              e.printStackTrace();
            }
          }
        }
        currentBeat += 1;
        if (currentBeat == lastBeat) {
          timer.cancel();
        }
        //check for notes at beat and play them
      }
    };
    timer.schedule(playNotes, 0, tempo);


  }

  @Override
  public void initialize(IMusicEditorModel model) {
    notes = model.getNotes();
    lastBeat = model.getLastBeat();
    tempo = model.getTempo() / 1000;
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    //Unused method
  }

  @Override
  public void showErrorMessage(String error) {
    //Unused method
  }

  @Override
  public void refresh() {
    //Unused method
  }

  public int getCurrentBeat() {
    return currentBeat;
  }
}
