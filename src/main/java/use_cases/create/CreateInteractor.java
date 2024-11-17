package use_cases.create;

import entities.CardSet;
import entities.CardSetFactory;

import java.util.List;

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
    public void execute(CreateInputData createInputData) {
        final String title = createInputData.getTitle();
        final String description = createInputData.getDescription();
        final List<String> fronts = createInputData.getFronts();
        final List<String> backs = createInputData.getBacks();

        // validate the title and description
        if (title.isEmpty() || description.isEmpty()) {
            createPresenter.prepareFailView("title/description missing\n" +
                    "please add a title/description");
            return;
        } else if (createDAO.setExistsByTitle(title)) {
            createPresenter.prepareFailView("title already exists.\n" +
                    "please choose another title");
            return;
        }

        // validate the cards
        for (int k = 0; k < fronts.size(); k++) {
            if (fronts.get(k).isEmpty() || backs.get(k).isEmpty()) {
                createPresenter.prepareFailView("one or more cards are not filled.\n" +
                        "please fill all cards");
                return;
            }
        }

        // all input has been validated, go ahead and create the set
        final CardSet cardSet = cardSetFactory.create(title, description, fronts, backs);
        createDAO.saveSet(cardSet);

        final CreateOutputData createOutputData = new CreateOutputData(
                cardSet.getID(),
                cardSet.getTitle(),
                cardSet.getDescription(),
                cardSet.getFronts(),
                cardSet.getBacks()
        );
        // clears the create view and switches to home
        createPresenter.prepareSuccessView(createOutputData);
    }

    @Override
    public void switchToHomeView() {
        createPresenter.switchToHomeView();
    }
}
