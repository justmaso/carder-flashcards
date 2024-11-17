package interface_adapters.edit;

import interface_adapters.ViewModel;

/**
 * The view model for the edit view.
 */
public class EditViewModel extends ViewModel<String> {
    public EditViewModel() {
        super("edit");
        setState("");
    }
}
