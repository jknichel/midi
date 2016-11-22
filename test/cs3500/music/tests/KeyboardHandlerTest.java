package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.KeyboardHandler;

import static org.junit.Assert.assertEquals;

public class KeyboardHandlerTest {
  private ByteArrayOutputStream out = new ByteArrayOutputStream();
  private PrintStream ps = new PrintStream(out);

  @Before
  public void initializeStream() {
    System.setOut(ps);
  }

  private KeyboardHandler addMockKeyboardListener() {
    Map<Integer, Runnable> keyPresses = new HashMap<>();

    KeyboardHandler k = new KeyboardHandler();

    keyPresses.put(KeyEvent.VK_SPACE, testPauseResume);
    keyPresses.put(KeyEvent.VK_E, testEditScreen);
    keyPresses.put(KeyEvent.VK_UP, testScrollUp);
    keyPresses.put(KeyEvent.VK_DOWN, testScrollDown);
    keyPresses.put(KeyEvent.VK_LEFT, testScrollLeft);
    keyPresses.put(KeyEvent.VK_RIGHT, testSrollRight);
    keyPresses.put(KeyEvent.VK_END, testEndKey);
    keyPresses.put(KeyEvent.VK_HOME, testHomeKey);

    k.setKeyPressedMap(keyPresses);

    return k;
  }

  private Runnable testPauseResume = () -> {
    System.out.print("Music paused/resumed.");
  };

  private Runnable testEditScreen = () -> {
    System.out.print("Edit screen opened.");
  };

  private Runnable testScrollLeft = () -> {
    System.out.print("Screen scrolled left.");
  };

  private Runnable testSrollRight = () -> {
    System.out.print("Screen scrolled right.");
  };

  private Runnable testScrollUp = () -> {
    System.out.print("Screen scrolled up.");
  };

  private Runnable testScrollDown = () -> {
    System.out.print("Screen scrolled down.");
  };

  private Runnable testHomeKey = () -> {
      System.out.print("Screen scrolled to beginning.");
  };

  private Runnable testEndKey = () -> {
    System.out.print("Screen scrolled to end.");
  };

  @Test
  public void testArrowUp() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_UP, ' ', 0));
    assertEquals(out.toString(),
            "Screen scrolled up.");
  }

  @Test
  public void testArrowDown() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_DOWN, ' ', 0));
    assertEquals(out.toString(),
            "Screen scrolled down.");
  }

  @Test
  public void testArrowLeft() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_LEFT, ' ', 0));
    assertEquals(out.toString(),
            "Screen scrolled left.");
  }

  @Test
  public void testArrowRight() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_RIGHT, ' ', 0));
    assertEquals(out.toString(),
            "Screen scrolled right.");
  }

  @Test
  public void testHomeKey() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_HOME, ' ', 0));
    assertEquals(out.toString(),
            "Screen scrolled to beginning.");
  }

  @Test
  public void testArrowEndKey() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_END, ' ', 0));
    assertEquals(out.toString(),
            "Screen scrolled to end.");
  }

  @Test
  public void testShowEditScreen() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_E, ' ', 0));
    assertEquals(out.toString(),
            "Edit screen opened.");
  }

  @Test
  public void testPausePlay() {
    KeyboardHandler kbh = addMockKeyboardListener();
    kbh.keyPressed(new KeyEvent(new Component() {
    }, 0, 10, 0,
            KeyEvent.VK_SPACE, ' ', 0));
    assertEquals(out.toString(),
            "Music paused/resumed.");
  }
}
