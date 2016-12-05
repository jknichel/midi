package cs3500.music.provider;

import java.awt.Graphics;
import java.awt.Color;

import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;

/**
 * Created by tpask on 11/6/2016.
 */
public class NotesPanel extends JPanel {
  private Note highNote;
  private Note lowNote;
  private List<Note> notes;
  private int lastBeat;

  /**
   * Constructor for a Panel drawing the notes.
   *
   * @param notes    List of notes to display
   * @param highNote Highest note in list
   * @param lowNote  Lowest note in list
   * @param lastBeat Beat of the last played note
   */
  public NotesPanel(List<Note> notes, Note highNote, Note lowNote, int lastBeat) {
    this.highNote = highNote;
    this.lowNote = lowNote;
    this.notes = notes;
    this.lastBeat = lastBeat;
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
    //this.setPreferredSize(new Dimension(500,500));

    g.setColor(Color.black);

    //draw key labels
    int start = lowNote.getTone().getNumericTone();
    int end = highNote.getTone().getNumericTone();
    int count = 1;
    for (int x = end; x >= start; x--) {
      g.drawString(toneNumToString(x), 0, (25 * count) + 25);
      count += 1;
    }

    int beat;
    int duration;
    int tone;
    //draw notes
    int lastNote = lowNote.getTone().getNumericTone() * 25;

    for (int note = notes.size() - 1; note >= 0; note--) {
      beat = notes.get(note).getBeat();
      duration = notes.get(note).getDuration();
      tone = notes.get(note).getTone().getNumericTone();

      g.setColor(Color.magenta);
      g.fillRect((beat * 25) + 25, ((highNote.getTone().getNumericTone() - tone) * 25) + 25,
              duration * 25, 25);
      g.setColor(Color.BLACK);
      g.fillRect((beat * 25) + 25, ((highNote.getTone().getNumericTone() - tone) * 25) + 25,
              25, 25);
    }

    g.setColor(Color.BLACK);
    //draw dimensions
    for (int currentBeat = 1; currentBeat <= Math.ceil(this.lastBeat / 4.0); currentBeat++) {
      for (int row = 1; row <= (highNote.getTone().getNumericTone() -
              lowNote.getTone().getNumericTone() + 1); row++) {
        g.drawRect((currentBeat * 100) - 75, (row * 25), 100, 25);
      }
    }

  }

  public void setRange(Note highNote, Note lowNote) {
    this.highNote = highNote;
    this.lowNote = lowNote;
  }

  private String toneNumToString(int t) {
    String result = " ";
    Tone tone = new Tone(Pitch.values()[((t - 1) % 12)], ((t - 1) / 12));
    result += tone.getPitch().toString();
    result += Integer.toString(tone.getOctave());
    while (result.length() != 5) {
      result += " ";
    }
    return result;
  }

}
