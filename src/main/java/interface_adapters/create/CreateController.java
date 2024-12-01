package interface_adapters.create;

import use_cases.create.CreateInputBoundary;
import use_cases.create.CreateInputData;

import java.util.List;

public class CreateController {
    private final CreateInputBoundary createInteractor;

    public CreateController(CreateInputBoundary createInteractor) {
        this.createInteractor = createInteractor;
    }

    /**
     * Executes the creation use case for card sets.
     * @param title the set's title.
     * @param description the set's description.
     * @param cards the set's cards (text).
     */
    public void create(String title, String description,
                       List<List<String>> cards) {
        createInteractor.create(new CreateInputData(title, description, cards));
    }

    /**
     * Executes the create and study use case.
     * @param title the title of the set to create and study.
     * @param description the description of the set to create and study.
     * @param cards the cards of the set to create and study.
     */
    public void createAndStudy(String title, String description,
                               List<List<String>> cards) {
        createInteractor.createAndStudy(new CreateInputData(title, description, cards));
    }

    /**
     * Executes the use case of switching to the home view.
     */
    public void switchToHomeView() {
        createInteractor.switchToHomeView();
    }
}
