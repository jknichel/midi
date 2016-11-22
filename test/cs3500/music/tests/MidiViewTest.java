package cs3500.music.tests;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.sound.midi.ShortMessage;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.Song;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMidiView;
import cs3500.music.view.MidiView;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for MidiView.
 */
public class MidiViewTest {
  /**
   * Tests the Midi view by calling all builders to make the song, and then calling a test method
   * to compare the contents of the Track with what's expected.
   */
  @Test
  public void testMidiWithMock() {
    try {
      Readable fileReader = new FileReader(new File("mary-little-lamb.txt"));
      CompositionBuilder<Song> builder = new Song.Builder();
      Song model = MusicReader.parseFile(fileReader, builder);
      IMidiView view = new MidiView();
      IMusicEditorController controller = new MusicEditorController(model, view);
      controller.runMusicEditor();
      List<ShortMessage> noteMessages = view.getNoteMessages();
      Appendable log = new StringBuilder();
      for (ShortMessage msg : noteMessages) {
        try {
          log.append(String.valueOf(msg.getCommand())).append(" ");
          log.append(String.valueOf(msg.getChannel())).append(" ");
          log.append(String.valueOf(msg.getData1())).append(" ");
          log.append(String.valueOf(msg.getData2()));
          log.append("\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      assertEquals(log.toString(), "144 1 64 72\n" +
              "144 1 55 70\n" +
              "144 1 62 72\n" +
              "128 1 64 72\n" +
              "144 1 60 71\n" +
              "128 1 62 72\n" +
              "144 1 62 79\n" +
              "128 1 60 71\n" +
              "128 1 55 70\n" +
              "144 1 55 79\n" +
              "144 1 64 85\n" +
              "128 1 62 79\n" +
              "144 1 64 78\n" +
              "128 1 64 85\n" +
              "144 1 64 74\n" +
              "128 1 64 78\n" +
              "128 1 55 79\n" +
              "128 1 64 74\n" +
              "144 1 55 77\n" +
              "144 1 62 75\n" +
              "144 1 62 77\n" +
              "128 1 62 75\n" +
              "144 1 62 75\n" +
              "128 1 62 77\n" +
              "144 1 55 79\n" +
              "144 1 64 82\n" +
              "128 1 55 77\n" +
              "128 1 62 75\n" +
              "144 1 67 84\n" +
              "128 1 55 79\n" +
              "128 1 64 82\n" +
              "144 1 67 75\n" +
              "128 1 67 84\n" +
              "144 1 55 78\n" +
              "144 1 64 73\n" +
              "128 1 67 75\n" +
              "144 1 62 69\n" +
              "128 1 64 73\n" +
              "144 1 60 71\n" +
              "128 1 62 69\n" +
              "144 1 62 80\n" +
              "128 1 60 71\n" +
              "144 1 55 79\n" +
              "144 1 64 84\n" +
              "128 1 55 78\n" +
              "128 1 62 80\n" +
              "144 1 64 76\n" +
              "128 1 64 84\n" +
              "144 1 64 74\n" +
              "128 1 64 76\n" +
              "144 1 64 77\n" +
              "128 1 64 74\n" +
              "144 1 55 78\n" +
              "144 1 62 75\n" +
              "128 1 55 79\n" +
              "128 1 64 77\n" +
              "144 1 62 74\n" +
              "128 1 62 75\n" +
              "144 1 64 81\n" +
              "128 1 62 74\n" +
              "144 1 62 70\n" +
              "128 1 64 81\n" +
              "144 1 52 72\n" +
              "144 1 60 73\n" +
              "128 1 55 78\n" +
              "128 1 62 70\n" +
              "128 1 52 72\n" +
              "128 1 60 73\n");
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
  }
}