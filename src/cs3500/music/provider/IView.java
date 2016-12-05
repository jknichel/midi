package cs3500.music.provider;

import java.util.function.Consumer;

import cs3500.music.model.IMusicEditorModel;

/**
 * Created by Brennan on 11/4/2016.
 */

public interface IView {
  /**
   * Makes the view visible.
   */
  void initialize(IMusicEditorModel model);

  /**
   * Allows the view to process commands.
   *
   * @param callback object
   */
  void setCommandCallback(Consumer<String> callback);

  /**
   * Shows error messages on invalid inputs.
   */
  void showErrorMessage(String error);

  /**
   * The view draws itself.
   */
  void refresh();
}
