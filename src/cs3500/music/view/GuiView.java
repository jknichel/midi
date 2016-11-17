package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Sub-Interface of IMusicEditorView to hold GUI-specific methods for the GUI views.
 */
public interface GuiView extends IMusicEditorView {

  void addKeyListener(KeyListener k);

  void addMouseListener(MouseListener m);

  void pause();

  void resume();
}
