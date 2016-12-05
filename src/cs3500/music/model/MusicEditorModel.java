package cs3500.music.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the interface to translate between our Song class and their MusicEditorModel
 * class.
 */
public class MusicEditorModel implements IModelTranslator {
  private final IMusicEditorModel song;

  public MusicEditorModel(IMusicEditorModel song) {
    this.song = song;
  }

  @Override
  public List<Note> getNotes() {
    List<Note> notes = new LinkedList<>();

    for (int i = 0; i < this.song.getLength(); i++) {
      if (this.song.noteStartingBeats().containsKey(i)) {
        for (MusicNote note : this.song.noteStartingBeats().get(i)) {
          notes.add(new Note(note, i));
        }
      }
    }

    return notes;
  }

  @Override
  public int getLastBeat() {
    return this.song.getLength();
  }

  @Override
  public Note getLowNote() {
    List<MusicNote> range = this.song.noteRange();
    MusicNote low = range.get(0);

    return new Note(low, 0);
  }

  @Override
  public Note getHighNote() {
    List<MusicNote> range = this.song.noteRange();
    MusicNote high = range.get(range.size() - 1);

    return new Note(high, 0);
  }

  @Override
  public int getTempo() {
    return this.song.getTempo();
  }
}
