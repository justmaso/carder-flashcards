package interface_adapters.study;

import interface_adapters.ViewModel;

/**
 * The view model for the study view.
 */
public class StudyViewModel extends ViewModel<StudyState> {
    public StudyViewModel() {
        super("study");
        setState(new StudyState());
    }
}
