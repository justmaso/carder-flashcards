package use_cases.home;

import data_access.InMemoryDataAccessObject;
import entities.CardSet;
import entities.CardSetFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_cases.edit.EditInputData;
import use_cases.study.StudyInputData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HomeInteractorTest {
    private final CardSetFactory cardSetFactory = new CardSetFactory();
    private InMemoryDataAccessObject dataAO;

    @BeforeEach
    void setUp() {
        dataAO = new InMemoryDataAccessObject();
        dataAO.saveSet(cardSetFactory.create(
                "1st-title",
                "1st-description",
                List.of(
                        List.of("f1", "b1"),
                        List.of("f2", "b2")
                )
        ));
    }

    @Test
    void testRefresh() {
        HomeOutputBoundary homePresenter = new HomeOutputBoundary() {
            @Override
            public void refresh(HomeOutputData homeOutputData) {
                assertEquals(2, homeOutputData.getIDs().size());
                assertEquals(2, homeOutputData.getTitles().size());
                assertEquals(2, homeOutputData.getDescriptions().size());
                assertEquals(2, homeOutputData.getCards().size());
                assertTrue(dataAO.setExistsByTitle("2nd-title"));
            }

            @Override
            public void switchToCreateView() {
                // no need to test this
            }

            @Override
            public void switchToEditView(EditInputData editInputData) {
                // no need to test this
            }

            @Override
            public void switchToStudyView(StudyInputData cardSetData) {
                // no need to test this
            }
        };

        // add a new set to
        dataAO.saveSet(cardSetFactory.create(
                "2nd-title",
                "2nd-description",
                List.of(
                        List.of("newF1", "newB1"),
                        List.of("newF2", "newB2")
                )
        ));

        HomeInputBoundary homeInteractor = new HomeInteractor(dataAO, homePresenter);
        homeInteractor.refresh();
    }

    @Test
    void testSwitchToCreateView() {
        HomeOutputBoundary homePresenter = new HomeOutputBoundary() {
            @Override
            public void refresh(HomeOutputData homeOutputData) {
                // no need to test this
            }

            @Override
            public void switchToCreateView() {
                // as long as this doesn't crash, the test is successful
            }

            @Override
            public void switchToEditView(EditInputData editInputData) {
                // no need to test this
            }

            @Override
            public void switchToStudyView(StudyInputData cardSetData) {
                // no need to test this
            }
        };

        HomeInputBoundary homeInteractor = new HomeInteractor(dataAO, homePresenter);
        homeInteractor.switchToCreateView();
    }

    @Test
    void testSwitchToEditView() {
        final CardSet cardSet = dataAO.getCardSets().getFirst();
        EditInputData editInputData = new EditInputData(
                cardSet.getID(),
                cardSet.getTitle(),
                cardSet.getDescription(),
                cardSet.getCards()
        );
        HomeOutputBoundary homePresenter = new HomeOutputBoundary() {
            @Override
            public void refresh(HomeOutputData homeOutputData) {
                // no need to test this
            }

            @Override
            public void switchToCreateView() {
                // no need to test this
            }

            @Override
            public void switchToEditView(EditInputData editInputData) {
                assertEquals(cardSet.getID(), editInputData.getID());
                assertEquals(cardSet.getTitle(), editInputData.getTitle());
                assertEquals(cardSet.getDescription(), editInputData.getDescription());
                assertEquals(cardSet.getCards(), editInputData.getCards());
                assertTrue(dataAO.setExistsByTitle(editInputData.getTitle()));
            }

            @Override
            public void switchToStudyView(StudyInputData cardSetData) {
                // no need to test this
            }
        };

        HomeInputBoundary homeInteractor = new HomeInteractor(dataAO, homePresenter);
        homeInteractor.switchToEditView(editInputData);
    }

    @Test
    void testSwitchToStudyView() {
        final CardSet cardSet = dataAO.getCardSets().getFirst();
        StudyInputData studyInputData = new StudyInputData(
                cardSet.getTitle(),
                cardSet.getDescription(),
                cardSet.getCards()
        );
        HomeOutputBoundary homePresenter = new HomeOutputBoundary() {
            @Override
            public void refresh(HomeOutputData homeOutputData) {
                // no need to test this
            }

            @Override
            public void switchToCreateView() {
                // no need to test this
            }

            @Override
            public void switchToEditView(EditInputData editInputData) {
                // no need to test this
            }

            @Override
            public void switchToStudyView(StudyInputData cardSetData) {
                assertEquals(cardSet.getTitle(), studyInputData.getTitle());
                assertEquals(cardSet.getDescription(), studyInputData.getDescription());
                assertEquals(cardSet.getCards(), studyInputData.getCards());
                assertTrue(dataAO.setExistsByTitle(studyInputData.getTitle()));
            }
        };


        HomeInputBoundary homeInteractor = new HomeInteractor(dataAO, homePresenter);
        homeInteractor.switchToStudyView(studyInputData);
    }

    @Test
    void testDeleteCardSet() {
        HomeOutputBoundary homePresenter = new HomeOutputBoundary() {
            @Override
            public void refresh(HomeOutputData homeOutputData) {
                // no need to test this
            }

            @Override
            public void switchToCreateView() {
                // no need to test this
            }

            @Override
            public void switchToEditView(EditInputData editInputData) {
                // no need to test this
            }

            @Override
            public void switchToStudyView(StudyInputData cardSetData) {
                // no need to test this
            }
        };
        final String existingTitle = "1st-title";
        assertTrue(dataAO.setExistsByTitle(existingTitle));
        HomeInputBoundary homeInteractor = new HomeInteractor(dataAO, homePresenter);
        homeInteractor.deleteCardSet(existingTitle);
        assertFalse(dataAO.setExistsByTitle(existingTitle));
    }
}
