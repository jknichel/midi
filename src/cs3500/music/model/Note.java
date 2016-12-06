package cs3500.music.model;

/**
 * Represents a note from the providers model that is created using a note from our model.
 */
public class Note {
  private Tone tone;
  private int beat;
  private int duration;
  private int instrument;

  /**
   * Constructs a note in the form of the provider's code when given a note from the original
   * code and a start time for the note.
   * @param note the note to be translated into a new type of note
   * @param start the start time of the note
   */
  public Note(MusicNote note, int start) {
    this.tone = new Tone(note.getPitch(), note.getOctave());
    this.beat = start;
    this.duration = note.getTotalDuration() - 1;
    this.instrument = note.getInstrument();
  }

  /**
   * Gets the tone of the note.
   * @return the tone of the note
   */
  public Tone getTone() {
    return tone;
  }

  /**
   * Gets the starting beat of the note.
   * @return teh starting beat for the note
   */
  public int getBeat() {
    return beat;
  }

  /**
   * Gets the duration of the note.
   * @return the duration of the note
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Gets the instrument that the note is played in.
   * @return the number corresponding to the instrument that the note is being played in
   */
  public int getInstrument() {
    return instrument;
  }
}
