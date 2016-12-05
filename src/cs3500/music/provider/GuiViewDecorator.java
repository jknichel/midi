package cs3500.music.provider;

import java.util.function.Consumer;

import cs3500.music.model.IMusicEditorModel;

public class GuiViewDecorator extends ViewDecorator {

  public GuiViewDecorator(IView decoratedView) {
    super(decoratedView);
  }

  @Override
  public void initialize(IMusicEditorModel model) {
    decoratedView.initialize(model);
    //...
  }
  //...

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    decoratedView.setCommandCallback(callback);
    //...
  }
  //...

  @Override
  public void showErrorMessage(String error) {
    decoratedView.showErrorMessage(error);
    //...
  }

  //...

  @Override
  public void refresh() {
    decoratedView.refresh();
    //...
  }

  //...
}