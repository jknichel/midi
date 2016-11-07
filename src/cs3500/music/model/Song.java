package cs3500.music.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.music.util.*;

/**
 * An object to represent a song. A song consists of NoteLists which hold all of the notes of the
 * song. It implements the IMusicEditorModel interface.
 */
public class Song implements IMusicEditorModel {
  /**
   * A map from an integer beat to a list of all notes starting on that beat.
   */
  private final Map<Integer, List<MusicNote>> beatsToNoteStarts = new HashMap<>();

  /**
   * A map from an integer beat to a list of all notes continuing through that beat.
   */
  private final Map<Integer, List<MusicNote>> beatsToNoteContinuations = new HashMap<>();

  /**
   * The tempo of the song.
   */
  private int tempo;

  /**
   * The length of the song in beats. This is not final because it can change as the song is
   * edited.
   */
  private int length;

  public static final class Builder implements CompositionBuilder<Song> {
    /**
     * The tempo of the song being built.
     */
    private int tempo;

    /**
     * The notes of the song to be built.
     */
    private List<Map<String, Integer>> notes = new ArrayList<>();

    @Override
    public Song build() {
      Song song = new Song(this.tempo);
      for (Map<String, Integer> noteInfo : notes) {
        Pitches pitch = Pitches.C;
        int pitchIndex = (noteInfo.get("pitch") % 12) + 1;
        pitch = pitch.getPitchFromScaleIndex(pitchIndex);
        int octave = (noteInfo.get("pitch") - pitchIndex - 1) / 12;
        song.addNote(noteInfo.get("start"), noteInfo.get("end"), noteInfo.get("instrument"),
                pitch, octave, noteInfo.get("volume"));
      }
      return song;
    }

    @Override
    public CompositionBuilder<Song> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<Song> addNote(int start, int end, int instrument, int pitch,
                                            int volume) {
      Map<String, Integer> noteInfo = new HashMap<>();

      noteInfo.put("start", start);
      noteInfo.put("end", end);
      noteInfo.put("instrument", instrument);
      noteInfo.put("pitch", pitch);
      noteInfo.put("volume", volume);
      notes.add(noteInfo);

      return this;
    }
  }

  /**
   * Default constructor. Creates a song with 0 length with no notes in it.
   */
  public Song() {
    this.length = 0;
    this.tempo = 0;
  }

  /**
   * Constructor with tempo, sets the tempo of the song to the passed value.
   * @param tempo the tempo of the song to be created.
   */
  public Song(int tempo) {
    this.length = 0;
    this.tempo = tempo;
  }

  @Override
  public void addNote(int start, int end, int instrument, Pitches pitch, int octave,
                         int volume) {
    List<MusicNote> tempList;
    int duration = end - start + 1;
    MusicNote noteToAdd = new MusicNote(pitch, octave, instrument, volume, duration);
    if (beatsToNoteStarts.containsKey(start)) {
      tempList = beatsToNoteStarts.get(start);
      tempList.add(noteToAdd);
      beatsToNoteStarts.put(start, tempList);
    } else {
      tempList = new ArrayList<>();
      tempList.add(noteToAdd);
      beatsToNoteStarts.put(start, tempList);
    }

    for (int i = start; i <= end; i++) {
      if (beatsToNoteContinuations.containsKey(i)) {
        tempList = beatsToNoteContinuations.get(i);
        tempList.add(noteToAdd);
        beatsToNoteContinuations.put(i, tempList);
      } else {
        tempList = new ArrayList<>();
        tempList.add(noteToAdd);
        beatsToNoteContinuations.put(i, tempList);
      }
    }
    updateLength();
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public int getTempo() {
    return tempo;
  }

  @Override
  public List<MusicNote> noteRange() {
    List<MusicNote> noteRange = new ArrayList<>();
    MusicNote lowest = lowestNote();
    MusicNote highest = highestNote();
    MusicNote current = lowest;

    while ((current.getPitch().getScaleIndex() + (current.getOctave() * 12))
            <= (highest.getPitch().getScaleIndex() + (highest.getOctave() * 12))) {
      noteRange.add(current);
      current = current.nextNote();
    }
    return noteRange;
  }

  @Override
  public Map<Integer, List<MusicNote>> noteStartingBeats() {
    return beatsToNoteStarts;
  }

  @Override
  public Map<Integer, List<MusicNote>> noteContinuationBeats() {
    return beatsToNoteContinuations;
  }

  /**
   * Returns the MusicNote representing the lowest type (pitch and octave) for a NoteList.
   * @return the MusicNote representing the lowest type of NoteList.
   */
  private MusicNote lowestNote() {
    // Start with a random note, compare all others to it to find the lowest in song.
    MusicNote lowestNote = beatsToNoteStarts.get(beatsToNoteStarts.keySet().toArray()[0]).get(0);

    for (List<MusicNote> starts : beatsToNoteStarts.values()) {
      for (MusicNote note : starts) {
        if ((note.getPitch().getScaleIndex() + note.getOctave() * 12) <
                (lowestNote.getPitch().getScaleIndex() + lowestNote.getOctave() * 12)) {
          lowestNote = note;
        }
      }
    }
    return lowestNote;
  }

  /**
   * Returns the MusicNote representing the highest type (pitch and octave) for a NoteList.
   * @return the MusicNote representing the highest type of NoteList.
   */
  private MusicNote highestNote() {
    // Start with a random note, compare all others to it to find the highest in song.
    MusicNote highestNote = beatsToNoteStarts.get(beatsToNoteStarts.keySet().toArray()[0]).get(0);

    for (List<MusicNote> starts : beatsToNoteStarts.values()) {
      for (MusicNote note : starts) {
        if ((note.getPitch().getScaleIndex() + note.getOctave() * 12) >
                (highestNote.getPitch().getScaleIndex() + highestNote.getOctave() * 12)) {
          highestNote = note;
        }
      }
    }
    return highestNote;
  }

  /**
   * Updates the length of the song by finding the latest end beat of all the notes in the song.
   */
  private void updateLength() {
    Integer length = 0;
    Set<Integer> beats = beatsToNoteContinuations.keySet();
    for (Integer beat : beats) {
      if (beat > length) {
        length = beat;
      }
    }
    this.length = length;
  }
}
