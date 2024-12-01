package use_cases.edit;

import data_access.InMemoryDataAccessObject;
import entities.CardSetFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the edit use case.
 */
public class EditInteractorTest {
    private final CardSetFactory cardSetFactory = new CardSetFactory();
    private InMemoryDataAccessObject dataAO;
    private int setID;

    @BeforeEach
    void setUp() {
        dataAO = new InMemoryDataAccessObject();
        dataAO.saveSet(cardSetFactory.create(
                "title",
                "description",
                List.of(
                        List.of("f1", "b1"),
                        List.of("f2", "b2")
                )
        ));
        setID = dataAO.getIDByTitle("title");
    }

    @Test
    void testEditSuccess() {
        // the changes made to the card set
        EditInputData editInputData = new EditInputData(
                setID,
                "updated-title",
                "updated-description",
                List.of(
                        List.of("updatedF1", "updatedB1"),
                        List.of("updatedF2", "updatedB2")
                )
        );
        EditOutputBoundary editPresenter = new EditOutputBoundary() {
            @Override
            public void editSuccessful() {
                assertTrue(dataAO.setExistsByTitle("updated-title"));
                assertFalse(dataAO.setExistsByTitle("title"));
                assertEquals(setID, dataAO.getIDByTitle("updated-title"));
            }

            @Override
            public void editFailed(String error) {
                fail("successful edit failed unexpectedly");
            }

            @Override
            public void switchToHomeView() {
                // no need to test this
            }
        };

        EditInputBoundary editInteractor = new EditInteractor(dataAO, editPresenter, cardSetFactory);
        editInteractor.publishEdits(editInputData);
    }

    @Test
    void testEditFailureTitleOrDescriptionEmpty() {
        // title is removed from the set
        EditInputData editInputData = new EditInputData(
                setID,
                "",
                "updated-description",
                List.of(
                        List.of("updatedF1", "updatedB1"),
                        List.of("updatedF2", "updatedB2")
                )
        );
        EditOutputBoundary editPresenter = new EditOutputBoundary() {
            @Override
            public void editSuccessful() {
                fail("[empty title/description] unsuccessful edit succeeded unexpectedly");
            }

            @Override
            public void editFailed(String error) {
                assertTrue(dataAO.setExistsByTitle("title"));
                assertFalse(dataAO.setExistsByTitle("updated-title"));
                assertEquals("title/description missing\n" +
                        "please add a title/description", error);
            }

            @Override
            public void switchToHomeView() {
                // no need to test this
            }
        };
        EditInputBoundary editInteractor = new EditInteractor(dataAO, editPresenter, cardSetFactory);
        editInteractor.publishEdits(editInputData);

        // the title and description is removed from the set
        editInputData = new EditInputData(setID,
                "",
                "",
                List.of(
                        List.of("updatedF1", "updatedB1"),
                        List.of("updatedF2", "updatedB2")
                )
        );
        editInteractor.publishEdits(editInputData);
    }

    @Test
    void testEditFailureCardsEmpty() {
        // title is removed from the set
        EditInputData editInputData = new EditInputData(
                setID,
                "updated-title",
                "updated-description",
                List.of(
                        List.of("updatedF1", ""),
                        List.of("", "updatedB2")
                )
        );
        EditOutputBoundary editPresenter = new EditOutputBoundary() {
            @Override
            public void editSuccessful() {
                fail("[empty cards] unsuccessful edit succeeded unexpectedly");
            }

            @Override
            public void editFailed(String error) {
                assertTrue(dataAO.setExistsByTitle("title"));
                assertFalse(dataAO.setExistsByTitle("updated-title"));
                assertEquals("one or more cards are not filled.\n" +
                        "please fill all cards", error);
            }

            @Override
            public void switchToHomeView() {
                // no need to test this
            }
        };
        EditInputBoundary editInteractor = new EditInteractor(dataAO, editPresenter, cardSetFactory);
        editInteractor.publishEdits(editInputData);
    }

    @Test
    void testEditFailureTitleAlreadyExists() {
        // saves a new set to the DAO. we need id=888 because computers are too fast...
        dataAO.saveSet(cardSetFactory.create(
                888,
                "existing-title",
                "existing-description",
                List.of(
                        List.of("existingF1", "existingB1")
                )
        ));

        // the changes made to the existing set
        EditInputData editInputData = new EditInputData(
                setID,
                "existing-title",
                "updated-description",
                List.of(
                        List.of("updatedF1", "updatedB1"),
                        List.of("updatedF2", "updatedB2")
                )
        );

        EditOutputBoundary editPresenter = new EditOutputBoundary() {
            @Override
            public void editSuccessful() {
                fail("[existing title] unsuccessful edit succeeded unexpectedly");
            }

            @Override
            public void editFailed(String error) {
                assertTrue(dataAO.setExistsByTitle("title"));
                assertTrue(dataAO.setExistsByTitle("existing-title"));
                assertEquals("title already exists.\n" +
                        "please choose another title", error);
            }

            @Override
            public void switchToHomeView() {
                // no need to test this
            }
        };

        EditInputBoundary editInteractor = new EditInteractor(dataAO, editPresenter, cardSetFactory);
        editInteractor.publishEdits(editInputData);
    }

    @Test
    void testSwitchToHomeView() {
        EditOutputBoundary editPresenter = new EditOutputBoundary() {
            @Override
            public void editSuccessful() {
                // no need to test this
            }

            @Override
            public void editFailed(String error) {
                // no need to test this
            }

            @Override
            public void switchToHomeView() {
                // if nothing crashes, the test is successful
            }
        };

        EditInputBoundary editInteractor = new EditInteractor(dataAO, editPresenter, cardSetFactory);
        editInteractor.switchToHomeView();
    }
}
