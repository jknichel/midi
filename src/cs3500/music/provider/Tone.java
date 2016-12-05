package cs3500.music.model;

/**
 * Created by tpask on 10/21/2016.
 * Class to represent a combination of a Pitch and an int Octave
 * Can also be represented as a numeric tone, which is a scale starting at C0 increasing
 * while also taking octaves into consideration.
 */
public class Tone {
  private Pitches pitch;
  private int octave;
  private int numericTone;

  /**
   * Tone object. Consists of a Pitch and an Octave. Can also be represented
   * as a numeric Tone on the scale from C0=1.
   *
   * @param p   Pitch object
   * @param oct Integer Octave
   */
  public Tone(Pitches p, int oct) {
    if (oct < 0) { //Doesn't allow negative octaves in the scale
      throw new IllegalArgumentException("Invalid Negative octave");
    }
    this.pitch = p;
    this.octave = oct;
    this.numericTone = p.numericPitch() + (12 * octave); //Scale starting at C0
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Tone)) {
      return false;
    } else {
      Tone toneO = (Tone) o;
      return toneO.pitch.equals(this.pitch) &&
              toneO.octave == this.octave &&
              toneO.numericTone == this.numericTone;
    }
  }

  @Override
  public int hashCode() {
    int result = 13;
    result = 31 * result + pitch.hashCode();
    result = 31 * result + octave;
    result = 31 * result + numericTone;
    return result;
  }

  public int getNumericTone() {
    return numericTone;
  }

  public Pitches getPitch() {
    return pitch;
  }

  public int getOctave() {
    return octave;
  }
}
