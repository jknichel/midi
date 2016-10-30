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
   * Default constructor for MusicNote class. Simply creates a note representing "middle C".
   */
  public MusicNote() {
    this.pitch = Pitches.C;
    this.octave = 4;
  }

  /**
   * Constructor for the MusicNote object. Takes in the pitch and octave and assigns the fields.
   * @param pitch the pitch of the note to create.
   * @param octave the octave of the note to create.
   * @throws IllegalArgumentException if invalid octave input.
   */
  public MusicNote(Pitches pitch, int octave) {
    if (octave < 1) {
      throw new IllegalArgumentException("Octave must be >= 1");
    }
    this.pitch = pitch;
    this.octave = octave;
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