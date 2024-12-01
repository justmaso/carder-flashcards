package use_cases.study;

public class StudyInteractor implements StudyInputBoundary {
    private final StudyOutputBoundary studyPresenter;

    public StudyInteractor(StudyOutputBoundary studyPresenter) {
        this.studyPresenter = studyPresenter;
    }

    @Override
    public void switchToHomeView() {
        studyPresenter.switchToHomeView();
    }

    @Override
    public void moveToPrevCard() {
        studyPresenter.moveToPrevCard();
    }

    @Override
    public void moveToNextCard() {
        studyPresenter.moveToNextCard();
    }

    @Override
    public void flip() {
        studyPresenter.flip();
    }

    @Override
    public void toggleShuffling() {
        studyPresenter.toggleShuffling();
    }

    @Override
    public void toggleSorting() {
        studyPresenter.toggleSorting();
    }

    @Override
    public void addToUnknown(String front, String back) {
        studyPresenter.addToUnknown(front, back);
    }

    @Override
    public void addToKnown() {
        studyPresenter.addToKnown();
    }

    @Override
    public void restart() {
        studyPresenter.restart();
    }

    @Override
    public void studyUnknown() {
        studyPresenter.studyUnknown();
    }

    @Override
    public void fullyRestart() {
        studyPresenter.fullyRestart();
    }

    @Override
    public void undo() {
        studyPresenter.undo();
    }



}
