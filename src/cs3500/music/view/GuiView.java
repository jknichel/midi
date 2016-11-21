package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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
   * @param listener
   */
  void plugInRemoveNoteActionListener(ActionListener listener);

  /**
   * Adds a mouse listener to the view.
   * @param m the mouse listener to add to the view
   */
  void addMouseListener(MouseListener m);

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

  String getEditText();

  /**
   * The list of notes to be added to a piece of music.
   */
  List<String> addNoteQueue =  new ArrayList<>();

  /**
   * The list of notes to be removed from a piece of music.
   */
  List<String> removeNoteQueue =  new ArrayList<>();
}
