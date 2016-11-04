package cs3500.music.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicNote;

/**
 * A console view for a IMusicEditorModel.
 */
public class MusicEditorConsoleView implements IMusicEditorView {
  IMusicEditorModel model;
  final Appendable out = System.out;

  @Override
  public void makeVisible() {
    return;
  }

  @Override
  public void refresh(List<MusicNote> noteRange, Map<Integer, List<MusicNote>> noteStartingBeats,
                      Map<Integer, List<MusicNote>> noteContinuationBeats, int songLength) {
    // figure out how many spaces we need to pad to, based on the total number of beats in song
    int lengthToPadNotes = String.valueOf(songLength).length();
    try {
      out.append(noteHeaderRow(noteRange, lengthToPadNotes) + "\n");
      for (int i = 0; i <= songLength; i++) {
        out.append(String.format("%1$" + String.valueOf(lengthToPadNotes) + "d", i));
        out.append("  " + beatRowToString(noteRange, noteStartingBeats.get(i),
                noteContinuationBeats.get(i)) + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String noteHeaderRow(List<MusicNote> noteRange, int lengthToPadNotes) {
    // Create the string of whitespace to block off column for beats before starting note headerRowString
    String formatString = "%-" + String.valueOf(lengthToPadNotes) + "s";
    String headerRowString = String.format(formatString, " ");

    for (MusicNote note : noteRange) {
      headerRowString += centerNoteText(note.toString());
    }
    return headerRowString;
  }

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
