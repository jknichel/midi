package cs3500.music.model;

/**
 * Enumeration for each possible pitch.
 */
public enum Pitches {
  C(1),
  C_SHARP(2),
  D(3),
  D_SHARP(4),
  E(5),
  F(6),
  F_SHARP(7),
  G(8),
  G_SHARP(9),
  A(10),
  A_SHARP(11),
  B(12);

  /**
   * Variable to hold the value of where a given pitch sits on the pitch scale to determine the
   * order of the pitches. Can be used to compare if a pitch is higher than another pitch.
   */
  private final int scaleIndex;

  /**
   * Constructor to assign values to the order field.
   * @param value indicating the order of the Pitch enum.
   */
  Pitches(int value) {
    scaleIndex = value;
  }

  /**
   * Getter for the scaleIndex
   * @return the scaleIndex of this pitch.
   */
  public int getScaleIndex() {
    return scaleIndex;
  }

  /**
   * Looks up the pitch associated with a scale index.
   * @param index the index to get a pitch for.
   * @return the pitch associated with the scale index.
   */
  public Pitches getPitchFromScaleIndex(int index) {
    switch (index) {
      case 1:
        return C;
      case 2:
        return C_SHARP;
      case 3:
        return D;
      case 4:
        return D_SHARP;
      case 5:
        return E;
      case 6:
        return F;
      case 7:
        return F_SHARP;
      case 8:
        return G;
      case 9:
        return G_SHARP;
      case 10:
        return A;
      case 11:
        return A_SHARP;
      case 12:
        return B;
      default:
        throw new IllegalArgumentException("Invalid Index");
    }
  }

  /**
   * Returns the next pitch in order.
   * @return the next pitch following the current pitch.
   * @throws IllegalStateException if this is an invalid pitch.
   */
  public Pitches nextPitch() {
    switch (this) {
      case C:
        return Pitches.C_SHARP;
      case C_SHARP:
        return Pitches.D;
      case D:
        return Pitches.D_SHARP;
      case D_SHARP:
        return Pitches.E;
      case E:
        return Pitches.F;
      case F:
        return Pitches.F_SHARP;
      case F_SHARP:
        return Pitches.G;
      case G:
        return Pitches.G_SHARP;
      case G_SHARP:
        return Pitches.A;
      case A:
        return Pitches.A_SHARP;
      case A_SHARP:
        return Pitches.B;
      case B:
        return Pitches.C;
      default:
        throw new IllegalStateException("This pitch is invalid!");
    }
  }

  /**
   * Return a string representation of the pitch. Enums can't hold "#" so this makes it easier to
   * translate here rather than farther down the line.
   * @return string representation of pitch.
   */
  @Override
  public String toString() {
    switch (this) {
      case C:
        return "C";
      case C_SHARP:
        return "C#";
      case D:
        return "D";
      case D_SHARP:
        return "D#";
      case E:
        return "E";
      case F:
        return "F";
      case F_SHARP:
        return "F#";
      case G:
        return "G";
      case G_SHARP:
        return "G#";
      case A:
        return "A";
      case A_SHARP:
        return "A#";
      case B:
        return "B";
      default:
        return "";
    }
  }
}
