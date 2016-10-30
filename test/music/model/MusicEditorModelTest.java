package music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the MusicEditorModel.
 */
public class MusicEditorModelTest {
  @Test
  public void getMusicEditorStateTest() {
    MusicEditorModel model = new MusicEditorModel();
    model.addNote(Pitches.E, 3, 0, 11);
    model.addNote(Pitches.A_SHARP, 3, 0, 11);
    model.addNote(Pitches.C_SHARP, 4, 5, 3);
    assertEquals(model.getMusicEditorState(), "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  "
            + "C#4 \n"
            + " 0  X                             X                   \n"
            + " 1  |                             |                   \n"
            + " 2  |                             |                   \n"
            + " 3  |                             |                   \n"
            + " 4  |                             |                   \n"
            + " 5  |                             |              X    \n"
            + " 6  |                             |              |    \n"
            + " 7  |                             |              |    \n"
            + " 8  |                             |                   \n"
            + " 9  |                             |                   \n"
            + "10  |                             |                   \n");
  }

}