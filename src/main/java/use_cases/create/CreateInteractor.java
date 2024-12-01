package use_cases.create;

import entities.CardSet;
import entities.CardSetFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateInteractor implements CreateInputBoundary {
    private final CreateDataAccessInterface createDAO;
    private final CreateOutputBoundary createPresenter;
    private final CardSetFactory cardSetFactory;

    public CreateInteractor(CreateDataAccessInterface createDAO,
                            CreateOutputBoundary createPresenter,
                            CardSetFactory cardSetFactory) {
        this.createDAO = createDAO;
        this.createPresenter = createPresenter;
        this.cardSetFactory = cardSetFactory;
    }

    @Override
    public void create(CreateInputData createInputData) {
        final CreateOutputData createOutputData = validateCreateInputData(createInputData);
        if (createOutputData != null) createPresenter.createSuccessful(createOutputData);
    }

    @Override
    public void createAndStudy(CreateInputData createInputData) {
        final CreateOutputData createAndStudyOutputData = validateCreateInputData(createInputData);
        if (createAndStudyOutputData != null) createPresenter.switchToStudyView(createAndStudyOutputData);
    }

    private CreateOutputData validateCreateInputData(CreateInputData createInputData) {
        final String title = createInputData.getTitle().strip();
        final String description = createInputData.getDescription().strip();
        final List<List<String>> cards = createInputData.getCards().stream()
                .map(card -> List.of(card.getFirst().strip(), card.getLast().strip()))
                .toList();

        // validate the title and description
        if (title.isBlank() || description.isBlank()) {
            createPresenter.prepareFailView("title/description missing\n" +
                    "please add a title/description");
            return null;
        } else if (createDAO.setExistsByTitle(title)) {
            createPresenter.prepareFailView("title already exists.\n" +
                    "please choose another title");
            return null;
        }

        // validate the cards
        for (List<String> pairs : cards) {
            if (pairs.getFirst().isBlank() || pairs.getLast().isBlank()) {
                createPresenter.prepareFailView("one or more cards are not filled.\n" +
                        "please fill all cards");
                return null;
            }
        }

        // all input has been validated, go ahead and create the set
        final CardSet cardSet = cardSetFactory.create(title, description, cards);
        createDAO.saveSet(cardSet);

        return new CreateOutputData(
                cardSet.getID(),
                title,
                description,
                cards
        );
    }

    @Override
    public void switchToHomeView() {
        createPresenter.switchToHomeView();
    }
}
