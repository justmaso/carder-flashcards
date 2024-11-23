package use_cases.home;

import entities.CardSet;
import interface_adapters.study.StudyState;

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
    public void switchToEditView(String cardSetTitle) {
        homePresenter.switchToEditView(cardSetTitle);
    }

    @Override
    public void switchToStudyView(StudyState studyState) {
        homePresenter.switchToStudyView(studyState);
    }

    @Override
    public void execute() {
        final List<CardSet> allCardSets = homeDAO.getCardSets();

        List<Integer> IDs = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<List<String>> fronts = new ArrayList<>();
        List<List<String>> backs = new ArrayList<>();

        allCardSets.forEach(cardSet -> {
            IDs.add(cardSet.getID());
            titles.add(cardSet.getTitle());
            descriptions.add(cardSet.getDescription());
            fronts.add(cardSet.getFronts());
            backs.add(cardSet.getBacks());
        });
        final HomeOutputData homeOutputData = new HomeOutputData(
                IDs,
                titles,
                descriptions,
                fronts,
                backs
        );
        homePresenter.prepareSuccessView(homeOutputData);
    }

    @Override
    public void deleteCardSet(String cardSetTitle) {
        homeDAO.deleteCardSet(cardSetTitle);
    }
}
