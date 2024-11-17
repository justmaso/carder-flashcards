package interface_adapters.create;

import interface_adapters.ViewModel;

/**
 * The view model for the create view.
 */
public class CreateViewModel extends ViewModel<String> {
    public CreateViewModel() {
        super("create");
        setState("");
    }
}
