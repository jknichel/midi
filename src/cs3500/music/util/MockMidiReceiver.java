package cs3500.music.util;

import java.io.IOException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * A mocked MidiReceiver to receive Midi messages and log them for testing, instead of playing
 * them.
 */
public class MockMidiReceiver implements Receiver {
  /**
   * The appendable for logging the Midi Message Info
   */
  Appendable log = new StringBuilder();

  @Override
  public void send(MidiMessage message, long timeStamp) {
    try {
      ShortMessage msg = (ShortMessage) message;
      log.append(String.valueOf(msg.getCommand())).append(" ");
      log.append(String.valueOf(msg.getChannel())).append(" ");
      log.append(String.valueOf(msg.getData1())).append(" ");
      log.append(String.valueOf(msg.getData2()));
      log.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This shouldn't really be called, but we'll have it print the contents of the log to the
   * console if it is.
   */
  @Override
  public void close() {
    System.out.append(log.toString());
  }

  /**
   * Returns the log in string form.
   * @return the log in string form.
   */
  public String getLog() {
    return log.toString();
  }
}
