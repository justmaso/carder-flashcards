package use_cases.study;

import java.util.ArrayList;
import java.util.List;

import entities.Card;
import entities.CardSet;

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
        final String title = studyInputData.getTitle();

        CardSet cards = studyDao.getCardSet(title);
        List<Card> shuffledCards = cards.getShuffledCards();
        List<String> shuffledFronts = new ArrayList<>();
        List<String> shuffledBacks = new ArrayList<>();
        for (int i = 0; i < shuffledCards.size(); i++) {
            shuffledFronts.add(i, shuffledCards.get(i).getFront());
            shuffledBacks.add(i, shuffledCards.get(i).getBack());
        }

        final StudyOutputData studyOutputData = new StudyOutputData(
                cards.getID(),
                cards.getTitle(),
                cards.getDescription(),
                cards.getFronts(),
                cards.getBacks(),
                shuffledFronts,
                shuffledBacks
                );
        studyPresenter.prepareSuccessView(studyOutputData);
    }
}
