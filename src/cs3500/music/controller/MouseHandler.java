package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

// mouse motion listener for dragging (if we wanted to create notes by dragging the cursor from
// start beat to end beat

public class MouseHandler implements MouseListener {
  private Map<Integer, Runnable> clickedMap = new HashMap<>();
  private Map<Integer, Runnable> pressedMap = new HashMap<>();
  private Map<Integer, Runnable> releasedMap = new HashMap<>();
  private Map<Integer, Runnable> enteredMap = new HashMap<>();
  private Map<Integer, Runnable> exitedMap = new HashMap<>();

  private MouseEvent currentMouseEvent;

    MouseHandler(Map<Integer, Runnable> clickedMap, Map<Integer, Runnable> pressedMap,
    Map<Integer, Runnable> releasedMap, Map<Integer, Runnable> enteredMap,
               Map<Integer, Runnable> exitedMap) {
    this.clickedMap = clickedMap;
    this.pressedMap = pressedMap;
    this.releasedMap = releasedMap;
    this.enteredMap = enteredMap;
    this.exitedMap = exitedMap;
  }

  public MouseEvent getCurrentMouseEvent() {
    return this.currentMouseEvent;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.currentMouseEvent = e;
    if (this.clickedMap.containsKey(e.getButton())) {
      this.clickedMap.get(e.getButton()).run();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.currentMouseEvent = e;
    if (this.pressedMap.containsKey(e.getButton())) {
      this.pressedMap.get(e.getButton()).run();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.currentMouseEvent = e;
    if (this.releasedMap.containsKey(e.getButton())) {
      this.releasedMap.get(e.getButton()).run();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.currentMouseEvent = e;
    if (this.enteredMap.containsKey(e.getButton())) {
      this.enteredMap.get(e.getButton()).run();
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.currentMouseEvent = e;
    if (this.exitedMap.containsKey(e.getButton())) {
      this.exitedMap.get(e.getButton()).run();
    }
  }
}
