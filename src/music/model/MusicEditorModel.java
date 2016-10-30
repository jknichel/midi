package music.model;

/**
 * An implementation of the MusicEditorModel interface. It is a model to represent a music editor.
 * It will hold the song that is currently open to be edited, and will be able to perform editing
 * functions on the song such as adding, removing, and editing notes.
 */
public class MusicEditorModel implements IMusicEditorModel {

  /**
   * Since writing/reading to/from files is not supported, a MusicEditorModel will be tied to a
   * single song at a time.
   */
  private final Song song;

  /**
   * Default constructor. Opens the editor with a new, empty song.
   */
  public MusicEditorModel() {
    song = new Song();
  }

  /**
   * Constructor that allows you to pass in an exisiting song to open within the editor.
   * @param songToOpen the song to open in the editor.
   */
  public MusicEditorModel(Song songToOpen) {
    song = songToOpen;
  }

  @Override
  public boolean addNote(Pitches pitch, int octave, int start, int duration) {
    MusicNoteWithDuration noteToAdd = new MusicNoteWithDuration(pitch, octave, start, duration);

    try {
      song.addNote(noteToAdd);
    } catch (IllegalArgumentException e) {
      return false;
    }

    return true;
  }

  @Override
  public boolean removeNote(Pitches pitch, int octave, int start, int end) {
    try {
      song.removeNote(pitch, octave, start, end);
    } catch (IllegalArgumentException e) {
      return false;
    }

    return true;
  }

  @Override
  public String getMusicEditorState() {
    String state = "";
    // figure out how many spaces we need to pad too, based on the total number of beats
    int lengthToPadNotes = String.valueOf(song.getLength()).length();

    state += getEditorStateNoteRow(lengthToPadNotes);

    for (int i = 0; i <= song.getLength(); i++) {
      state += String.format("%1$" + String.valueOf(lengthToPadNotes) + "d", i);
      state += song.beatRowNotesToString(i) + "\n";
    }

    return state;
  }

  /**
   * Returns a string representing the beat count line of the editor's current state.
   * @return the string representation of the beat count line.
   */
  private String getEditorStateNoteRow(int lengthToPadNotes) {
    // Create the string of whitespace to block off column for beats before starting note row
    String formatString = "%-" + String.valueOf(lengthToPadNotes) + "s";
    String row = String.format(formatString, " ");

    row += song.noteHeaderRowToString() + "\n";

    return row;
  }
}
