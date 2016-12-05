package cs3500.music.model;

/**
 * Represents a note from the providers model.
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
    this.duration = note.getTotalDuration();
    this.instrument = note.getInstrument();
  }

  public Tone getTone() {
    return tone;
  }

  public int getBeat() {
    return beat;
  }

  public int getDuration() {
    return duration;
  }

  public int getEndBeat() {
    return beat + duration - 1;
  }

  public int getInstrument() {
    return instrument;
  }
}
