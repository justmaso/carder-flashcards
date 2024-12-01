package use_cases.create;

import data_access.InMemoryDataAccessObject;
import entities.CardSetFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the creation use case.
 */
public class CreateInteractorTest {
    @Test
    void testSuccessCreate() {
        final String title = "success-title";
        final String description = "success-description";

        CreateInputData createInputData = new CreateInputData(title, description,
                List.of(
                        List.of("f1", "b1"),
                        List.of("f1", "b2")
                )
        );
        CreateDataAccessInterface dataAO = new InMemoryDataAccessObject();
        CreateOutputBoundary successPresenter = new CreateOutputBoundary() {
            @Override
            public void createSuccessful(CreateOutputData createOutputData) {
                assertInstanceOf(Integer.class, createOutputData.getID());
                assertEquals(title, createOutputData.getTitle());
                assertEquals(description, createOutputData.getDescription());
                assertTrue(dataAO.setExistsByTitle(title));
            }

            @Override
            public void prepareFailView(String error) {
                fail("creation failed unexpectedly");
            }

            @Override
            public void switchToHomeView() {
                // no need to test this; it's expected
            }

            @Override
            public void switchToStudyView(CreateOutputData createAndStudyOutputData) {
                // no need to test this; it's expected
            }
        };

        CreateInputBoundary createInteractor = new CreateInteractor(dataAO, successPresenter, new CardSetFactory());
        createInteractor.create(createInputData);
    }

    @Test
    void testSuccessCreateAndStudy() {
        final String title = "success-title";
        final String description = "success-description";
        final List<List<String>> cards = List.of(
                List.of("f1", "b1"),
                List.of("f1", "b2")
        );

        CreateInputData createInputData = new CreateInputData(title, description, cards);
        CreateDataAccessInterface dataAO = new InMemoryDataAccessObject();
        CreateOutputBoundary successPresenter = new CreateOutputBoundary() {
            @Override
            public void createSuccessful(CreateOutputData createOutputData) {
                assertInstanceOf(Integer.class, createOutputData.getID());
                assertEquals(title, createOutputData.getTitle());
                assertEquals(description, createOutputData.getDescription());
                assertEquals(cards, createOutputData.getCards());
                assertTrue(dataAO.setExistsByTitle(title));
            }

            @Override
            public void prepareFailView(String error) {
                fail("creation failed unexpectedly");
            }

            @Override
            public void switchToHomeView() {
                // no need to test this; it's expected
            }

            @Override
            public void switchToStudyView(CreateOutputData createAndStudyOutputData) {
                assertInstanceOf(Integer.class, createAndStudyOutputData.getID());
                assertEquals(title, createAndStudyOutputData.getTitle());
                assertEquals(description, createAndStudyOutputData.getDescription());
                assertEquals(cards, createAndStudyOutputData.getCards());
                assertTrue(dataAO.setExistsByTitle(title));
            }
        };

        CreateInputBoundary createInteractor = new CreateInteractor(dataAO, successPresenter, new CardSetFactory());
        createInteractor.createAndStudy(createInputData);
    }

    @Test
    void testFailureTitleOrDescriptionEmpty() {
        CreateInputData createInputData = new CreateInputData("", "non-empty-description",
                List.of(
                        List.of("f1", "b1"),
                        List.of("f1", "b2")
                )
        );
        CreateDataAccessInterface dataAO = new InMemoryDataAccessObject();
        CreateOutputBoundary createPresenter = new CreateOutputBoundary() {
            @Override
            public void createSuccessful(CreateOutputData createOutputData) {
                fail("creation succeeded unexpectedly");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("title/description missing\n" +
                        "please add a title/description", error);
            }

            @Override
            public void switchToHomeView() {
                // no need to test this; it's expected
            }

            @Override
            public void switchToStudyView(CreateOutputData createAndStudyOutputData) {
                // no need to test this; it's expected
            }
        };

        CreateInputBoundary createInteractor = new CreateInteractor(dataAO, createPresenter, new CardSetFactory());
        createInteractor.create(createInputData);

        createInputData = new CreateInputData("non-empty-title", "",
                List.of(
                        List.of("f1", "b1"),
                        List.of("f1", "b2")
                )
        );
        createInteractor.create(createInputData);
    }

    @Test
    void testFailureCardsEmpty() {
        CreateInputData createInputData = new CreateInputData("title", "description",
                List.of(
                        List.of("f1", ""),
                        List.of("", "b2")
                )
        );
        CreateDataAccessInterface dataAO = new InMemoryDataAccessObject();
        CreateOutputBoundary createPresenter = new CreateOutputBoundary() {
            @Override
            public void createSuccessful(CreateOutputData createOutputData) {
                fail("[empty title/description] creation succeeded unexpectedly");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("one or more cards are not filled.\n" +
                        "please fill all cards", error);
            }

            @Override
            public void switchToHomeView() {
                // no need to test this; it's expected
            }

            @Override
            public void switchToStudyView(CreateOutputData createAndStudyOutputData) {
                // no need to test this; it's expected
            }
        };

        CreateInputBoundary createInteractor = new CreateInteractor(dataAO, createPresenter, new CardSetFactory());
        createInteractor.create(createInputData);
    }

    @Test
    void testFailureTitleAlreadyExists() {
        final CardSetFactory cardSetFactory = new CardSetFactory();
        CreateDataAccessInterface dataAO = new InMemoryDataAccessObject();
        final String title = "already-exists-title";
        dataAO.saveSet(cardSetFactory.create(title,
                "already-exists-description",
                List.of(
                    List.of("f1", "b1"),
                    List.of("f1", "b2")
                )
        ));

        CreateInputData createInputData = new CreateInputData(title, "description",
                List.of(
                        List.of("newf1", "newf2"),
                        List.of("newf2", "newb2")
                )
        );
        CreateOutputBoundary createPresenter = new CreateOutputBoundary() {
            @Override
            public void createSuccessful(CreateOutputData createOutputData) {
                fail("[matching titles] creation succeeded unexpectedly");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("title already exists.\n" +
                        "please choose another title", error);
            }

            @Override
            public void switchToHomeView() {
                // no need to test this; it's expected
            }

            @Override
            public void switchToStudyView(CreateOutputData createAndStudyOutputData) {
                // no need to test this; it's expected
            }
        };

        CreateInputBoundary createInteractor = new CreateInteractor(dataAO, createPresenter, new CardSetFactory());
        createInteractor.create(createInputData);
    }

    @Test
    void testSwitchToHomeView() {
        CreateOutputBoundary successPresenter = new CreateOutputBoundary() {
            @Override
            public void createSuccessful(CreateOutputData createOutputData) {

            }

            @Override
            public void prepareFailView(String error) {

            }

            @Override
            public void switchToHomeView() {

            }

            @Override
            public void switchToStudyView(CreateOutputData createAndStudyOutputData) {

            }
        };
        CreateDataAccessInterface dataAO = new InMemoryDataAccessObject();
        CreateInputBoundary createInteractor = new CreateInteractor(dataAO, successPresenter, new CardSetFactory());
        createInteractor.switchToHomeView();
    }
}
