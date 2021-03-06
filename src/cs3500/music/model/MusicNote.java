package cs3500.music.model;

/**
 * Abstract class MusicNote to represent a single note with a pitch and an octave. Concrete
 * implementations of this class will be able to expand on its functionality.
 */
public class MusicNote {

  /**
   * Holds the enum representing the pitch of the note.
   */
  private final Pitches pitch;

  /**
   * Holds an int to identify the octave of the note.
   */
  private final int octave;

  /**
   * Holds an int to identify the instrument, to be interpreted by MIDI. Optional.
   */
  private final int instrument;

  /**
   * Holds and int to identify the volume of the note. Optional
   */
  private final int volume;

  /**
   * Holds the totalDuration the note continues on for.
   */
  private final int totalDuration;

  /**
   * Default constructor for MusicNote class. Simply creates a note representing "middle C".
   */
  public MusicNote() {
    this.pitch = Pitches.C;
    this.octave = 4;
    this.instrument = 0;
    this.volume = 90;
    this.totalDuration = 4;
  }

  /**
   * Constructor for the MusicNote object. Takes in the pitch and octave and assigns the fields.
   * @param pitch the pitch of the note to create.
   * @param octave the octave of the note to create.
   * @throws IllegalArgumentException if invalid octave input.
   */
  public MusicNote(Pitches pitch, int octave) {
    if (octave < 0) {
      throw new IllegalArgumentException("Octave must be >= 0");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.instrument = 0;
    this.volume = 90;
    this.totalDuration = 4;
  }

  /**
   * Constructor for the MusicNote object. Takes in the pitch and octave and assigns the fields.
   * This one also creates the notes with the option instrument and volume fields.
   * This is new from HW05.
   * @param pitch the pitch of the note to create.
   * @param octave the octave of the note to create.
   * @param instrument the instrument of the note to create.
   * @param volume the volume of the note to create.
   */
  public MusicNote(Pitches pitch, int octave, int instrument, int volume, int duration) {
    if (octave < 0) {
      throw new IllegalArgumentException("Octave must be >= 0");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.instrument = instrument;
    this.volume = volume;
    this.totalDuration = duration;
  }

  /**
   * Simple getter for the pitch.
   * @return the pitch.
   */
  public Pitches getPitch() {
    return pitch;
  }

  /**
   * Simple getter for the octave.
   * @return the ocatve.
   */
  public int getOctave() {
    return octave;
  }

  /**
   * Simple getter for the totalDuration.
   * @return number of beats the note is played held for from this point.
   */
  public int getTotalDuration() {
    return totalDuration;
  }

  /**
   * Get the volume of the note.
   * @return the volume of the note.
   */
  public int getVolume() {
    return volume;
  }

  /**
   * Getter for the instrument.
   * @return the instrument value.
   */
  public int getInstrument() {
    return instrument;
  }

  /**
   * Returns a string representation of the note (pitch and octave).
   * @return the string representation of the note.
   */
  public String toString() {
    return pitch.toString() + String.valueOf(octave);
  }

  /**
   * Returns the next highest note compared to this one.
   * @return the next highest note.
   */
  public MusicNote nextNote() {
    if (pitch.nextPitch() == Pitches.C) {
      return new MusicNote(pitch.nextPitch(), octave + 1);
    } else {
      return new MusicNote(pitch.nextPitch(), octave);
    }
  }
}