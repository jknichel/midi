package cs3500.music.tests;

import org.junit.Test;

import javax.sound.midi.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Pitches;
import cs3500.music.view.MidiView;

import static org.junit.Assert.assertEquals;

public class MidiReceiver implements Receiver{
  MidiView mv = new MidiView();

  @Override
  public void send(MidiMessage m, long timeStamp) {
    ShortMessage sm = (ShortMessage) m;
    int d1 = sm.getData1();
    int d2 = sm.getData2();

    mv.log.append("Note: ").append(timeStamp).append(" ").append(d1).append(" ").append(d2).append("\n");
  }

  @Override
  public void close() {
    mv.log.append("Receiver closed.\n");
  }

  @Test
  public void testMidi1() throws MidiUnavailableException {
    Synthesizer md = new MidiDevice();
    Receiver r = md.getReceiver();

    mv.playNote(2, new MusicNote(Pitches.C, 4, 1, 4, 2), r);

    assertEquals(mv.log.toString(),
            "Note: 2 something something");
  }

  @Test
  public void testMidi2() throws MidiUnavailableException {
    Synthesizer md = new MidiDevice();
    MidiReceiver mr = new MidiReceiver();
    Receiver r = md.getReceiver();

    mv.playNote(2, new MusicNote(Pitches.C, 4, 1, 4, 2), r);
    mr.close();

    assertEquals(mv.log.toString(),
            "Note: 2 something something\n" +
                    "Receiver closed.\n");
  }


}
