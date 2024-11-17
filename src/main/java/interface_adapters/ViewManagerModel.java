package interface_adapters;

/**
 * The model for the view manager.
 * Its state is the name of the currently active view.
 * An initial state of "" is used.
 */
public class ViewManagerModel extends ViewModel<String> {
    public ViewManagerModel() {
        super("view manager");
        setState("");
    }
}