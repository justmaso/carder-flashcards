package interface_adapters.edit;

import interface_adapters.ViewManagerModel;
import interface_adapters.home.HomeState;
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

    @Override
    public void editSuccessful() {
        editViewModel.getState().setEditError(null);
        editViewModel.firePropertyChanged("edit");
        homeViewModel.firePropertyChanged("edited");
        switchToHomeView();
    }

    @Override
    public void editFailed(String editError) {
        editViewModel.getState().setEditError(editError);
        editViewModel.firePropertyChanged("edit");
    }

    @Override
    public void switchToHomeView() {
        editViewModel.getState().clear();
        editViewModel.firePropertyChanged("clear");
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
