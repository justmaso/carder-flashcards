package interface_adapters.study;

import interface_adapters.ViewModel;

/**
 * The view model for the study view.
 */
<<<<<<< HEAD
public class StudyViewModel extends ViewModel<StudyState> {
    public StudyViewModel() {
        super("study");
        setState(new StudyState());
=======
public class StudyViewModel extends ViewModel<String> {
    public StudyViewModel() {
        super("study");
        setState("");
>>>>>>> 7f1ea95 (added starter home, create, edit, and study view models)
    }
}
