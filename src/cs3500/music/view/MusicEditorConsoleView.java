package cs3500.music.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Receiver;

import cs3500.music.model.MusicNote;

/**
 * A console view for a IMusicEditorModel.
 */
public class MusicEditorConsoleView implements IMusicEditorView {
  final Appendable out = System.out;

  @Override
  public void refresh(int beat) {
    // For a console view, there's no refresh needed. Just start writing to System.out.
  }

  @Override
  public void initializeView(List<MusicNote> noteRange,
                             Map<Integer, List<MusicNote>> noteStartingBeats,
                             Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength,
                             int tempo) {
    // figure out how many spaces we need to pad to, based on the total number of beats in song
    int lengthToPadNotes = String.valueOf(songLength).length();
    try {
      out.append(noteHeaderRow(noteRange, lengthToPadNotes)).append("\n");
      for (int i = 0; i <= songLength; i++) {
        out.append(String.format("%1$" + String.valueOf(lengthToPadNotes) + "d", i));
        out.append("  ").append(beatRowToString(noteRange, noteStartingBeats.get(i),
                noteContinuationBeats.get(i))).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Make the header note row for the console view.
   * @param noteRange the range of notes in the song, from lowest to highest.
   * @param lengthToPadNotes the length to pad the notes, based on the number of beats in the song.
   * @return a string of the header row.
   */
  private String noteHeaderRow(List<MusicNote> noteRange, int lengthToPadNotes) {
    // Create the string of whitespace to block off column for beats before starting note
    // headerRowString
    String formatString = "%-" + String.valueOf(lengthToPadNotes) + "s";
    String headerRowString = String.format(formatString, " ");

    for (MusicNote note : noteRange) {
      headerRowString += centerNoteText(note.toString());
    }
    return headerRowString;
  }

  /**
   * Render a string to represent a row of a specific beat in the console.
   * @param noteRange the range of notes in the song.
   * @param noteStarts list of notes that start on given beat.
   * @param noteContinues list of notes that continue through given beat.
   * @return string representation of that row.
   */
  private String beatRowToString(List<MusicNote> noteRange, List<MusicNote> noteStarts,
                                 List<MusicNote> noteContinues) {
    if (noteStarts == null) {
      noteStarts = new ArrayList<>();
    }
    if (noteContinues == null) {
      noteContinues = new ArrayList<>();
    }

    String beatRow = "";

    for (MusicNote note : noteRange) {
      for (MusicNote start : noteStarts) {
        if (note.getPitch() == start.getPitch() && note.getOctave() == start.getOctave()) {
          beatRow += "X";
          break;
        }
      }
      if (beatRow.length() > 0 && beatRow.charAt(beatRow.length() - 1) == 'X') {
        beatRow += "    ";
        continue;
      }
      for (MusicNote continues : noteContinues) {
        if (note.getPitch() == continues.getPitch() && note.getOctave() == continues.getOctave()) {
          beatRow += "|";
          break;
        }
      }
      if (beatRow.length() > 0 && beatRow.charAt(beatRow.length() - 1) == '|') {
        beatRow += "    ";
      } else {
        beatRow += "     ";
      }
    }
    return beatRow;
  }

  /**
   * Simple helper to center the name of a note in a column for the note header row.
   * @param noteString the string representing the note.
   * @return noteString properly whitespaced to be centered.
   */
  private String centerNoteText(String noteString) {
    if (noteString.length() == 2) {
      return "  " + noteString + " ";
    } else if (noteString.length() == 3) {
      return " " + noteString + " ";
    } else if (noteString.length() == 4) {
      return " " + noteString;
    } else {
      return noteString;
    }
  }
}
