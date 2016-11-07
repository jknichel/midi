package cs3500.music.view;

import java.awt.*;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.MusicNote;

/**
 * Visual view class for a music editor.
 */
public class MusicEditorGuiView extends JPanel implements IMusicEditorView {
  private final JFrame frame = new JFrame("Music Editor - Justin Knichel | Ben Brody");
  private final JPanel panel = new JPanel(new BorderLayout());
  private final JScrollPane scroller = new JScrollPane(panel);

  /**
   * Draw the pitches on the left side of the frame.
   * @return a box that contains the pitches of the song being played.
   */
  private Box drawPitches(List<MusicNote> noteRange) {
    Box pitches = Box.createVerticalBox();
    for (MusicNote n : noteRange) {
      JLabel pitch = new JLabel(n.toString());
      pitches.add(Box.createVerticalStrut(4));
      pitches.add(pitch);
    }
    return pitches;
  }

  /**
   * Draw the number of the beat every 16 beats.
   * @return a box with the beats along the top of the composition
   */
  private Box drawBeats(int songLength) {
    Box beats = Box.createHorizontalBox();
    for (int i = 0; i <= songLength; i += 16) {
      if (i == 0) {
        beats.add(Box.createHorizontalStrut(27));
        JLabel beat = new JLabel(Integer.toString(i));
        beats.add(beat);
      } else if (i > 99) {
        beats.add(Box.createHorizontalStrut(300));
        JLabel beat = new JLabel(Integer.toString(i));
        beats.add(beat);
      } else if (i > 999) {
          beats.add(Box.createHorizontalStrut(280));
          JLabel beat = new JLabel(Integer.toString(i));
          beats.add(beat);
      } else {
        beats.add(Box.createHorizontalStrut(309));
        JLabel beat = new JLabel(Integer.toString(i));
        beats.add(beat);
      }
    }
    return beats;
  }

  @Override
  public void initializeView(int tempo) {
    this.frame.setVisible(true);
  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
    Box pitches = drawPitches(noteRange);
    Box beats = drawBeats(songLength);
    GuiGridAndNotes grid = new GuiGridAndNotes(noteRange, noteStartingBeats, noteContinuationBeats,
            songLength);

    this.panel.add(pitches, BorderLayout.WEST);
    this.panel.add(beats, BorderLayout.NORTH);
    this.panel.add(grid, BorderLayout.CENTER);

    this.frame.add(this.scroller);
    this.frame.setLocation(50, 50);
    this.frame.setSize(1250, 750);
    this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.frame.setBackground(Color.WHITE);
    this.frame.add(scroller);
    this.initializeView(0);
  }
}
