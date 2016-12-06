package cs3500.music.model;

/**
 * Represents a Tone in the provider's model which consists of a pitch and an octave.
 */
public class Tone {
  private Pitches pitches;
  private int octave;
  private int numericTone;

  /**
   * Tone object. Consists of a Pitch and an Octave. Can also be represented
   * as a numeric Tone on the scale from C0=1.
   *
   * @param p   Pitch object
   * @param octave Integer Octave
   */
  public Tone(Pitches p, int octave) {
    if (octave < 0) {
      throw new IllegalArgumentException("Octave cannot be negative");
    }
    this.pitches = p;
    this.octave = octave;
    this.numericTone = p.getScaleIndex() + (12 * octave) + 11;
  }

  /**
   * Gets the number index corresponding with the tone.
   * @return the number index corresponding with the tone
   */
  public int getNumericTone() {
    return numericTone;
  }

  /**
   * Gets the pitch of the tone.
   * @return the pitch of the tone
   */
  public Pitches getPitch() {
    return pitches;
  }

  /**
   * Gets the octave of the tone.
   * @return the octave of the tone
   */
  public int getOctave() {
    return octave;
  }
}
