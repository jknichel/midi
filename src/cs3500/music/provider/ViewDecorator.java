package cs3500.music.provider;

import java.util.function.Consumer;

import cs3500.music.model.IMusicEditorModel;

public abstract class ViewDecorator implements IView {
  protected IView decoratedView;

  public ViewDecorator(IView decoratedView) {
    this.decoratedView = decoratedView;
  }

  public void initialize(IMusicEditorModel model) {
    decoratedView.initialize(model);
  }

  public void setCommandCallback(Consumer<String> callback) {
    decoratedView.setCommandCallback(callback);
  }

  public void showErrorMessage(String error) {
    decoratedView.showErrorMessage(error);
  }

  public void refresh() {
    decoratedView.refresh();
  }
}