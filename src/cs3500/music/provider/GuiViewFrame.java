package cs3500.music.provider;

import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;


import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicNote;

/**
 * A skeleton Frame (i.e., a window) in Swing.
 */
public class GuiViewFrame extends javax.swing.JFrame implements IView {

  private final NotesPanel NotesPanel; // You may want to refine this to a subtype of JPanel
  private JButton commandButton;
  Consumer<String> commandCallback;
  private List<MusicNote> notes;
  private int lastBeat;
  private MusicNote highNote;
  private MusicNote lowNote;
  private JScrollPane scrollPanel;

  /**
   * Creates new GuiView.
   */
  public GuiViewFrame(IMusicEditorModel model) {
    super();
    initialize(model);
    this.NotesPanel = new NotesPanel(model.getNotes(), this.highNote, this.lowNote,
            model.getLastBeat());
    this.NotesPanel.setRange(this.highNote, this.lowNote);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    this.scrollPanel = new JScrollPane(NotesPanel);
    this.NotesPanel.setPreferredSize(new Dimension((lastBeat * 25) + 25,
            ((highNote.getTone().getNumericTone() - lowNote.getTone().getNumericTone()) * 25)
                    + 25));
    this.getContentPane().add(scrollPanel);


    //button, don't need these yet

    /*commandButton = new JButton("Add");
    commandButton.addActionListener((ActionEvent e) ->
    {
      if (commandCallback!=null) { //if there is a command callback

      }
    });
    displayPanel.add(commandButton);*/


    this.pack();
  }


  @Override
  public void initialize(IMusicEditorModel model) {
    this.setVisible(true);
    this.notes = model.getNotes();
    this.lastBeat = model.getLastBeat();
    this.lowNote = model.getLowNote();
    this.highNote = model.getHighNote();
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    //Unused method setCommandCallback
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 500);
  }

  public JScrollPane getPanel() {
    return scrollPanel;
  }

}
