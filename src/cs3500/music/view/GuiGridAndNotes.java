package cs3500.music.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import cs3500.music.model.MusicNote;

/**
 * Represents the gridlines and notes drawn in a GUI view.
 */
public class GuiGridAndNotes extends JPanel {
  private static final int BOX_WIDTH = 20;
  private static final int BOX_HEIGHT = 18;
  private List<MusicNote> noteRange;
  private Map<Integer, List<MusicNote>> noteStartingBeats;
  private int songLength;

  /**
   * Constructs the gridlines and notes for a GUI view.
   *
   * @param noteRange             the range of notes in a song
   * @param noteStartingBeats     the map of starting beats to notes in a song
   * @param songLength            the length of the song in beats
   */
  public GuiGridAndNotes(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                         int songLength) {
    this.noteRange = noteRange;
    this.noteStartingBeats = noteStartingBeats;
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
      g2.draw(new Line2D.Float(7, vert, songLength * 20 + 7, vert));
      vert += 20;
      if (i == noteRange.size()) {
        g.drawLine(songLength * 20 + 7, 4, songLength * 20 + 7, vert - 20);
      }
    }
    g.drawLine(songLength * 20 + 7, 4, songLength * 20 + 7, vert - 20);
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
    while (counter <= songLength + 1) {
      g.drawLine(horiz, 6, horiz, noteRange.size() * 20 + 3);
      horiz += tempo;
      counter += 4;
    }
  }

  public void drawCurrentBeatLine(int currentBeat, Graphics g) {
    g.setColor(Color.red);
    g.drawLine(currentBeat * 20 + 7, 6, currentBeat * 20 + 7, noteRange.size() * 20 + 3);
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
        int d = songNote.getTotalDuration() - 1;
        for (MusicNote rangeNote : noteRange) {
          if (rangeNote.getPitch() == songNote.getPitch()
                  && rangeNote.getOctave() == songNote.getOctave()) {
            if (noteRange.indexOf(rangeNote) == 0) {
              if (c.equals(Color.green)) {

                g.fillRect(x, 5, d * BOX_WIDTH, BOX_HEIGHT);
              } else {
                g.fillRect(x, 5, BOX_WIDTH, BOX_HEIGHT);
              }
            } else {
              if (c.equals(Color.green)) {
                g.fillRect(x, noteRange.indexOf(rangeNote) * 20 + 5, d * BOX_WIDTH, BOX_HEIGHT);
              } else {
                g.fillRect(x, noteRange.indexOf(rangeNote) * 20 + 5, BOX_WIDTH, BOX_HEIGHT);
              }
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
    drawNotesHelper(noteStartingBeats, Color.green, g);
    drawNotesHelper(noteStartingBeats, Color.black, g);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.drawHorizontalLines(g);
    this.drawNotes(g);
    this.drawVerticalLines(g);
    this.drawCurrentBeatLine(MusicEditorGuiView.currentBeat, g);
    this.repaint();
  }
}
