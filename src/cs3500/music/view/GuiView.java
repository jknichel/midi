package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import cs3500.music.model.MusicNote;

/**
 * Sub-Interface of IMusicEditorView to hold GUI-specific methods for the GUI views.
 */
public interface GuiView extends IMusicEditorView {

  /**
   * Adds a key listener to the the view.
   * @param k the key listener to add to the view
   */
  void addKeyListener(KeyListener k);

  /**
   * Allow a controller to plug-in an ActionListener to the view's button/whatever that adds a
   * note.
   * @param listener the listener to plug into the "Add Note" button/etc.
   */
  void plugInAddNoteActionListener(ActionListener listener);

  /**
   * Allow a controller to plug-in an ActionListener to the view's button/whatever that removes a
   * note.
   * @param listener the listener to plug into the "Remove Note" button/etc.
   */
  void plugInRemoveNoteActionListener(ActionListener listener);

  /**
   * Adds a mouse listener to the view.
   * @param m the mouse listener to add to the view
   */
  void addMouseListener(MouseListener m);

  /**
   * A method to redraw the entire view in the event that the composition has been edited (i.e. a
   * note has been added or removed, etc).
   * @param noteRange the range of notes in the song.
   * @param noteStartingBeats map of beats to the notes that start on that beat.
   * @param noteContinuationBeats map of beats to notes that continue through that beat.
   * @param songLength the length of the song in beats.
   * @param tempo the tempo of the song in microseconds per beat.
   */
  void redrawForSongChange(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                           Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                           int tempo);

  /**
   * Pauses the piece of music.
   */
  void pause();

  /**
   * Resumes the piece of music after being paused.
   */
  void resume();

  /**
   * A combination of pause and resume for the sake of calling the correct method based on whether
   * or not the music is playing or is paused.
   */
  void pauseResume();

  /**
   * Shows the edit screen for the view.
   */
  void showEditScreen();

  /**
   * How the view can return the arguments passed to it from the user for the controller to use.
   * @return string representing the user's input.
   */
  String getEditText();

  /**
   * Shows a warning screen if the user inputs invalid text for a note.
   * @param text the error message to display with the warning screen
   */
  void showWarningScreen(String text);
}
