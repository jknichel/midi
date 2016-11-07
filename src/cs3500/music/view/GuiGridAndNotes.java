package cs3500.music.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.MusicNote;

/**
 * Represents the gridlines and notes drawn in a GUI view.
 */
public class GuiGridAndNotes extends JPanel {
  private static final int BOX_WIDTH = 20;
  private static final int BOX_HEIGHT = 18;
  private List<MusicNote> noteRange;
  private Map<Integer, List<MusicNote>> noteStartingBeats;
  private Map<Integer, List<MusicNote>> noteContinuationBeats;
  private int songLength;

  /**
   * Constructs the gridlines and notes for a GUI view.
   *
   * @param noteRange             the range of notes in a song
   * @param noteStartingBeats     the map of starting beats to notes in a song
   * @param noteContinuationBeats the map of continuing beats to notes in a song
   * @param songLength            the length of the song in beats
   */
  public GuiGridAndNotes(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                         Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
    this.noteRange = noteRange;
    this.noteStartingBeats = noteStartingBeats;
    this.noteContinuationBeats = noteContinuationBeats;
    this.songLength = songLength;
  }

  /**
   * Draws the horizontal lines that separate each pitch in a composition.
   *
   * @param g the graphics used to create these lines
   */
  private void drawHorizontalLines(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g.setColor(Color.black);
    g2.setStroke(new BasicStroke(2));
    int vert = 4;
    for (int i = 0; i <= noteRange.size(); i++) {
      g2.draw(new Line2D.Float(7, vert, songLength * 22, vert));
      vert += 20;
    }
    g2.draw(new Line2D.Float(songLength * 22, 4, songLength * 22, noteRange.size() * 20 + 3));
  }

  /**
   * Draws the vertical lines for a composition.
   *
   * @param g the graphics used to draw the lines
   */
  private void drawVerticalLines(Graphics g) {
    g.setColor(Color.black);
    int tempo = 80;
    int horiz = 7;
    int counter = 0;
    while (counter <= songLength + 4) {
      g.drawLine(horiz, 6, horiz, noteRange.size() * 20 + 3);
      horiz += tempo;
      counter += 4;
    }
  }

  /**
   * Helps draw the notes in the GUI view to avoid duplicate code.
   *
   * @param list the list of notes to be drawn
   * @param c    the color use when drawing the notes
   * @param g    the graphics to be used to draw the notes
   */
  private void drawNotesHelper(Map<Integer, List<MusicNote>> list, Color c, Graphics g) {
    g.setColor(c);
    for (Map.Entry<Integer, List<MusicNote>> e : list.entrySet()) {
      int k = e.getKey();
      int x = k * BOX_WIDTH + 8;
      for (MusicNote songNote : e.getValue()) {
        for (MusicNote rangeNote : noteRange) {
          if (rangeNote.getPitch() == songNote.getPitch()
                  && rangeNote.getOctave() == songNote.getOctave()) {
            if (noteRange.indexOf(rangeNote) == 0) {
              g.fillRect(x, 5, BOX_WIDTH, BOX_HEIGHT);
            } else {
              g.fillRect(x, noteRange.indexOf(rangeNote) * 20 + 5, BOX_WIDTH, BOX_HEIGHT);
            }
          }
        }
      }
    }
  }

  /**
   * Draws the notes on the GUI view.
   *
   * @param g the graphics used to draw the notes
   */
  private void drawNotes(Graphics g) {
    // draw note continuations
    drawNotesHelper(noteContinuationBeats, Color.green, g);

    // draw note starts
    drawNotesHelper(noteStartingBeats, Color.black, g);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.drawHorizontalLines(g);
    this.drawNotes(g);
    this.drawVerticalLines(g);
    this.repaint();
  }
}
