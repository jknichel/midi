package cs3500.music.model;

/**
 * Concrete implementation of MusicNote for the Music Editor. Object that represents a music note
 * with a specific start beat and beat duration. The ending beat is determined as well.
 */
public class MusicNoteWithDuration extends MusicNote {

  /**
   * Integer indicating the number of beats the note lasts in a song.
   */
  private int duration;

  /**
   * Integer indicating the starting beat number of the note in a song.
   */
  private int startBeat;

  /**
   * Integer indicating the ending beat of the note in a song. Calculated in the constructor, and
   * mostly used for convenience.
   */
  private int endBeat;

  /**
   * Default constructor. Calls the MusicNote constructor to create a "middle C" note starting at
   * beat 0 and lasting 4 beats.
   */
  public MusicNoteWithDuration() {
    super();
    this.startBeat = 0;
    this.duration = 4;
    this.endBeat = this.startBeat + this.duration - 1;
  }

  /**
   * Constructor that takes in the pitch, octave, duration, and starting beat for a note in a song
   * and creates a MusicNoteWithDuration object representing that.
   * @param pitch the pitch of the note.
   * @param octave the octave of the note.
   */
  public MusicNoteWithDuration(Pitches pitch, int octave, int start, int duration) {
    super(pitch, octave);

    if (start < 0) {
      throw new IllegalArgumentException("Cannot have a negative start beat!");
    } else if (duration < 1) {
      throw new IllegalArgumentException("Cannot have a zero or negative duration!");
    }

    this.startBeat = start;
    this.duration = duration;
    this.endBeat = this.startBeat + this.duration - 1;
  }

  /**
   * Lengthens the duration of a MusicNoteWithDuration.
   * @param t the amount of time to lengthen the note by.
   */
  public void lengthen(int t) {
    this.duration += t;
    this.endBeat = this.startBeat + this.duration - 1;
  }

  /**
   * Shortens the duration of a MusicNoteWithDuration.
   * @param t the amount of time to shorten the note by
   * @throws IllegalArgumentException if the user attempts to shorten the note by an amount
   * greater than the duration of the note
   */
  public void shorten(int t) throws IllegalArgumentException {
    if (this.duration - t < 1) {
      throw new IllegalArgumentException("Duration of note must be greater than 0");
    }
    this.duration -= t;
    this.endBeat = this.startBeat + this.duration - 1;
  }

  /**
   * Expedites the arrival of the note in a song.
   * @param t the amount of time to expedite the note by
   */
  public void expedite(int t) throws IllegalArgumentException{
    if (this.startBeat - t < 0) {
      throw new IllegalArgumentException("Cannot start note before the initial beat");
    }
    this.startBeat -= t;
    this.endBeat = this.startBeat + this.duration - 1;
  }

  /**
   * Delays the start time of a MusicNoteWithDuration.
   * @param t the amount of time to delay the note by
   */
  public void delay(int t) {
    this.startBeat += t;
    this.endBeat = this.startBeat + this.duration - 1;
  }

  /**
   * Getter method for the duration value.
   * @return int representing the duration of the note in a song.
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Getter method for the startBeat value.
   * @return int representing the starting beat of the note in a song.
   */
  public int getStartBeat() {
    return startBeat;
  }

  /**
   * Getter method for the endBeat value.
   * @return int representing the ending beat of the note in a song.
   */
  public int getEndBeat() {
    return endBeat;
  }
}
