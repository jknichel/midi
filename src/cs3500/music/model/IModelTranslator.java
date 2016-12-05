package cs3500.music.model;

import java.util.List;

/**
 * Class to translate between our Song class and their MusicEditorModel class that is expected by
 * their view.
 */
public interface IModelTranslator {
  public List<Note> getNotes();

  public int getLastBeat();

  public Note getLowNote();

  public Note getHighNote();

  public int getTempo();
}
