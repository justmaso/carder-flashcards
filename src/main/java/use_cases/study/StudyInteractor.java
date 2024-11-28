package use_cases.study;

import entities.CardSet;

import java.util.ArrayList;
import java.util.List;

/**
 * The study interactor.
 */
public class StudyInteractor implements StudyInputBoundary {
    private final StudyOutputBoundary studyPresenter;
    private final StudyDataAccessInterface studyDao;

    public StudyInteractor(StudyOutputBoundary studyPresenter, StudyDataAccessInterface studyDao) {
        this.studyDao = studyDao;
        this.studyPresenter = studyPresenter;
    }

    @Override
    public void switchToHomeView() {
        studyPresenter.switchToHomeView();
    }

    @Override
    public void execute(StudyInputData studyInputData) {
        String title = studyInputData.getTitle();
        CardSet cardSet = studyDao.getCardSet(title);
        StudyOutputData output = new StudyOutputData(cardSet.getTitle(), cardSet.getDescription(),
                cardSet.getFronts(), cardSet.getBacks());
        studyPresenter.prepareSuccessView(output);
    }
}
