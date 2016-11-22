package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class KeyboardHandler implements KeyListener {
  private Map<Character, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap, keyReleasedMap;

  public void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyChar())) {
      keyTypedMap.get(e.getKeyChar()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode()))
      keyPressedMap.get(e.getKeyCode()).run();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode()))
      keyReleasedMap.get(e.getKeyCode()).run();
  }
}