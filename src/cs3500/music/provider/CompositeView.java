package cs3500.music.provider;


import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditorModel;


/**
 * Created by tpask on 11/21/2016.
 */
public class CompositeView extends GuiViewFrame {
  private IMusicEditorModel model;
  private int currentBeat;

  /**
   * Constructor for composite view.
   *
   * @param model Model class
   */
  public CompositeView(IMusicEditorModel model) {
    super(model);
    this.model = model;
    //GuiViewFrame guiView = new GuiViewFrame(model);
    //guiView.setVisible(false);

    //Container panel = guiView.getContentPane();

    //this.getContentPane().add(panel);
    //this.setVisible(true);


    MidiViewImpl midiView = new MidiViewImpl();
    midiView.initialize(model);
    try {
      midiView.playComposition();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    /*while(true){
      currentBeat = midiView.getCurrentBeat();
      getGraphics().drawLine((currentBeat-1)*25,model.getHighNote().getTone().getNumericTone()*25,
              (currentBeat-1)*25,model.getLowNote().getTone().getNumericTone()*25);
    }*/
    Timer timer = new Timer();
    TimerTask drawRedLine = new TimerTask() {

      @Override
      public void run() {
        getGraphics().setColor(Color.RED);
        currentBeat = midiView.getCurrentBeat();
        getGraphics().drawLine((currentBeat - 1) * 25, 25,
                (currentBeat - 1) * 25, model.getLowNote().getTone().getNumericTone() * 25);
      }
    };
    TimerTask refreshTask = new TimerTask() {

      @Override
      public void run() {
        CompositeView.super.refresh();
      }
    };
    timer.schedule(drawRedLine, 0, model.getTempo() / 1000);
    timer.schedule(refreshTask, 500, model.getTempo() / 1000);

  }

}


