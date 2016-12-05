package cs3500.music.model;

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
    this.numericTone = p.getScaleIndex() + (12 * octave);
  }

  public int getNumericTone() {
    return numericTone;
  }

  public Pitches getPitch() {
    return pitches;
  }

  public int getOctave() {
    return octave;
  }
}
