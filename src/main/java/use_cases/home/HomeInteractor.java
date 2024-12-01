package use_cases.home;

import entities.CardSet;
import use_cases.edit.EditInputData;
import use_cases.study.StudyInputData;

import java.util.ArrayList;
import java.util.List;

/**
 * The home interactor.
 */
public class HomeInteractor implements HomeInputBoundary {
    private final HomeOutputBoundary homePresenter;
    private final HomeDataAccessInterface homeDAO;

    public HomeInteractor(HomeDataAccessInterface homeDAO, HomeOutputBoundary homePresenter) {
        this.homeDAO = homeDAO;
        this.homePresenter = homePresenter;
    }

    @Override
    public void switchToCreateView() {
        homePresenter.switchToCreateView();
    }

    @Override
    public void switchToEditView(EditInputData editInputData) {
        homePresenter.switchToEditView(editInputData);
    }

    @Override
    public void switchToStudyView(StudyInputData cardSetData) {
        homePresenter.switchToStudyView(cardSetData);
    }

    @Override
    public void refresh() {
        final List<CardSet> allCardSets = homeDAO.getCardSets();

        List<Integer> IDs = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<List<List<String>>> cards = new ArrayList<>();

        allCardSets.forEach(cardSet -> {
            IDs.add(cardSet.getID());
            titles.add(cardSet.getTitle());
            descriptions.add(cardSet.getDescription());
            cards.add(cardSet.getCards());
        });
        final HomeOutputData homeOutputData = new HomeOutputData(
                IDs,
                titles,
                descriptions,
                cards
        );
        homePresenter.refresh(homeOutputData);
    }

    @Override
    public void deleteCardSet(String cardSetTitle) {
        homeDAO.deleteCardSet(cardSetTitle);
    }
}
