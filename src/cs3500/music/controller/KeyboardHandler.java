package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class KeyboardHandler implements KeyListener {
  /**
   * Maps keyboard keys to the proper runnable to be executed up keypress.
   */
  private Map<Character, Runnable> keyTypedMap;
  /**
   * Maps keyboard key numbers to the proper runnable to be executed up keypress.
   */
  private Map<Integer, Runnable> keyPressedMap, keyReleasedMap;

  /**
   * Assigns the Key Typed map, linking a runnable to a key typed event.
   * @param map the map to assign to keyTypedMap.
   */
  public void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  /**
   * Assigns the Key Pressed map, linking a runnable to a key pressed event.
   * @param map the map to assign to keyPressedMap.
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Assigns the Key Released map, linking a runnable to a key released event.
   * @param map the map to assign to keyReleasedMap.
   */
  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
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