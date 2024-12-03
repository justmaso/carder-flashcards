package interface_adapters.edit;

import interface_adapters.ViewManagerModel;
import interface_adapters.home.HomeViewModel;
import use_cases.edit.EditOutputBoundary;

public class EditPresenter implements EditOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final EditViewModel editViewModel;
    private final HomeViewModel homeViewModel;

    public EditPresenter(ViewManagerModel vmm, EditViewModel evm,
                         HomeViewModel hvm) {
        viewManagerModel = vmm;
        editViewModel = evm;
        homeViewModel = hvm;
    }
    // no errors; input of edit is valid
    @Override
    public void editSuccessful() {
        editViewModel.getState().setEditError(null);
        editViewModel.firePropertyChanged("edit");
        homeViewModel.firePropertyChanged("edited");
        switchToHomeView();
    }
    // set an error message telling user what issue is
    @Override
    public void editFailed(String editError) {
        editViewModel.getState().setEditError(editError);
        editViewModel.firePropertyChanged("edit");
    }
    // switch back to main home page of flashcards
    @Override
    public void switchToHomeView() {
        editViewModel.getState().clear();
        editViewModel.firePropertyChanged("clear");
        // switch state to home view
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}