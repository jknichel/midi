package cs3500.music.tests;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.Song;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MockMidiReceiver;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MusicViewCreator;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for MidiView.
 */
public class MidiViewTest {
  /**
   * Tests the Midi view with a mock receiver. Calls some alternate methods to have the MIDI
   * messages sent to it and logged for testing.
   */
  @Test
  public void testMidiWithMock() {
    MockMidiReceiver mock = new MockMidiReceiver();
    try {
      Readable fileReader = new FileReader(new File("mary-little-lamb.txt"));
      CompositionBuilder<Song> builder = new Song.Builder();
      Song model = MusicReader.parseFile(fileReader, builder);
      IMusicEditorView view = new MusicViewCreator().create("midi");
      IMusicEditorController controller = new MusicEditorController(model, view, mock);
      controller.go();
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
    assertEquals(mock.getLog(), "144 10 64 72\n" +
            "128 10 64 72\n" +
            "144 10 55 70\n" +
            "128 10 55 70\n" +
            "144 10 62 72\n" +
            "128 10 62 72\n" +
            "144 10 60 71\n" +
            "128 10 60 71\n" +
            "144 10 62 79\n" +
            "128 10 62 79\n" +
            "144 10 55 79\n" +
            "128 10 55 79\n" +
            "144 10 64 85\n" +
            "128 10 64 85\n" +
            "144 10 64 78\n" +
            "128 10 64 78\n" +
            "144 10 64 74\n" +
            "128 10 64 74\n" +
            "144 10 55 77\n" +
            "128 10 55 77\n" +
            "144 10 62 75\n" +
            "128 10 62 75\n" +
            "144 10 62 77\n" +
            "128 10 62 77\n" +
            "144 10 62 75\n" +
            "128 10 62 75\n" +
            "144 10 55 79\n" +
            "128 10 55 79\n" +
            "144 10 64 82\n" +
            "128 10 64 82\n" +
            "144 10 67 84\n" +
            "128 10 67 84\n" +
            "144 10 67 75\n" +
            "128 10 67 75\n" +
            "144 10 55 78\n" +
            "128 10 55 78\n" +
            "144 10 64 73\n" +
            "128 10 64 73\n" +
            "144 10 62 69\n" +
            "128 10 62 69\n" +
            "144 10 60 71\n" +
            "128 10 60 71\n" +
            "144 10 62 80\n" +
            "128 10 62 80\n" +
            "144 10 55 79\n" +
            "128 10 55 79\n" +
            "144 10 64 84\n" +
            "128 10 64 84\n" +
            "144 10 64 76\n" +
            "128 10 64 76\n" +
            "144 10 64 74\n" +
            "128 10 64 74\n" +
            "144 10 64 77\n" +
            "128 10 64 77\n" +
            "144 10 55 78\n" +
            "128 10 55 78\n" +
            "144 10 62 75\n" +
            "128 10 62 75\n" +
            "144 10 62 74\n" +
            "128 10 62 74\n" +
            "144 10 64 81\n" +
            "128 10 64 81\n" +
            "144 10 62 70\n" +
            "128 10 62 70\n" +
            "144 10 52 72\n" +
            "128 10 52 72\n" +
            "144 10 60 73\n" +
            "128 10 60 73\n");
  }
}