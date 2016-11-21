package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.MusicNote;

/**
 * Visual view class for a music editor.
 */
public class MusicEditorGuiView extends JPanel implements GuiView {
  private final JFrame frame = new JFrame("Music Editor - Justin Knichel | Ben Brody");
  private final JPanel panel = new JPanel(new BorderLayout());

  private final JFrame editFrame = new JFrame("Edit");
  private final JPanel editPanel = new JPanel(new BorderLayout());
  private final JTextField editField = new JTextField();
  private final JButton addNote = new JButton("+");
  private final JButton removeNote = new JButton("-");

  private final JPanel buttons = new JPanel();
  private final JScrollPane scroller = new JScrollPane(panel);

  static int currentBeat;

  @Override
  public void addKeyListener(KeyListener k) {
    this.frame.addKeyListener(k);
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.panel.addMouseListener(m);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void pauseResume() {
  }

  @Override
  public void removeMouseListener(MouseListener m) {
    this.panel.removeMouseListener(m);
  }

  /**
   * Draw the pitches on the left side of the frame.
   *
   * @return a box that contains the pitches of the song being played.
   */
  private Box drawPitches(List<MusicNote> noteRange) {
    Box pitches = Box.createVerticalBox();
    for (MusicNote n : noteRange) {
      JLabel pitch = new JLabel(n.toString());
      pitches.add(Box.createVerticalStrut(5));
      pitches.add(pitch);
    }
    return pitches;
  }

  /**
   * Draw the number of the beat every 16 beats.
   *
   * @return a box with the beats along the top of the composition
   */
  private Box drawBeats(int songLength) {
    Box beats = Box.createHorizontalBox();
    for (int i = 0; i <= songLength; i += 16) {
      if (i == 0) {
        beats.add(Box.createHorizontalStrut(30));
        JLabel beat = new JLabel(Integer.toString(i));
        beats.add(beat);
      } else {
        beats.add(Box.createHorizontalStrut(320 - String.valueOf(i).length() * 8));
        JLabel beat = new JLabel(Integer.toString(i));
        beats.add(beat);
      }
    }

    return beats;
  }

  @Override
  public void refresh(int beat) {
    currentBeat = beat;
    if (currentBeat == 0) {
      this.scroller.getHorizontalScrollBar().setValue(0);
    } else if ((currentBeat > 20) && (currentBeat * 20) %
            (this.frame.getBounds().getSize().getWidth() - 40) < 20) {
      this.scroller.getHorizontalScrollBar().setValue(currentBeat * 20 + 7);
    }
  }

  /**
   * Shows the edit screen for adding and removing notes.
   */
  public void showEditScreen() {
    if (this.editFrame.isVisible()) {
      this.editFrame.setVisible(false);
    } else {
      this.editFrame.setVisible(true);
    }
  }

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo) {
    this.frame.setVisible(true);
    this.editFrame.setVisible(false);

    Box pitches = drawPitches(noteRange);
    Box beats = drawBeats(songLength);

    GuiGridAndNotes grid = new GuiGridAndNotes(noteRange, noteStartingBeats,
            songLength);

    this.scroller.getVerticalScrollBar().setUnitIncrement(16);
    InputMap im = this.scroller.getVerticalScrollBar().
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    im.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
    im.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");

    this.scroller.getHorizontalScrollBar().setUnitIncrement(16);
    im = this.scroller.getHorizontalScrollBar().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    im.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
    im.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");
    im.put(KeyStroke.getKeyStroke("HOME"), "minScroll");
    im.put(KeyStroke.getKeyStroke("END"), "maxScroll");

    this.buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.buttons.add(addNote);
    this.buttons.add(removeNote);

    this.panel.add(pitches, BorderLayout.WEST);
    this.panel.add(beats, BorderLayout.NORTH);
    this.panel.add(grid, BorderLayout.CENTER);

    this.editPanel.add(this.buttons, BorderLayout.SOUTH);
    this.editPanel.add(this.editField, BorderLayout.NORTH);

    this.editFrame.add(this.editPanel);
    this.editFrame.setLocation(100,100);
    this.editFrame.setSize(250, 100);

    this.frame.add(this.scroller);
    this.frame.setLocation(50, 50);
    this.frame.setSize(1250, 750);
    this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.frame.setBackground(Color.WHITE);
    this.frame.add(scroller);
    this.refresh(0);
  }

  @Override
  public void redrawForSongChange(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                           Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                           int tempo) {
    this.panel.removeAll();
    initializeView(noteRange, noteStartingBeats, noteContinuationBeats, songLength, tempo);
  }

  @Override
  public void plugInAddNoteActionListener(ActionListener listener) {
    this.addNote.addActionListener(listener);
  }

  @Override
  public void plugInRemoveNoteActionListener(ActionListener listener) {
    this.removeNote.addActionListener(listener);
  }

  @Override
  public String getEditText() {
    return editField.getText();
  }
}
