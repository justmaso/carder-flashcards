package interface_adapters.home;

import interface_adapters.ViewModel;

/**
 * The view model for the home view.
 */
public class HomeViewModel extends ViewModel<String> {
    public HomeViewModel() {
        super("home");
        setState("");
    }
}
