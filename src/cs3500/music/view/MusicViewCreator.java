package cs3500.music.view;

/**
 * Factory class for view creation.
 */
public class MusicViewCreator {

  /**
   * Method that creates and returns the appropriate view given a designating string.
   * @param view teh string to designate what type of view to create.
   * @return the view that was created
   */
  public static IMusicEditorView create(String view) {
    switch (view) {
      case "composite":
        return new MusicEditorCompositeView();
      case "console":
        return new MusicEditorConsoleView();
      case "visual":
        return new MusicEditorGuiView();
      case "midi":
        return new MidiView();
      default:
        throw new IllegalArgumentException("Invalid view type!");
    }
  }
}
