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
     * @param fronts the set's front text(s).
     * @param backs the set's back text(s).
     */
    public void execute(String title, String description,
                        List<String> fronts, List<String> backs) {
        createInteractor.execute(new CreateInputData(title, description, fronts, backs));
    }

    /**
     * Executes the use case of switching to the home view.
     */
    public void switchToHomeView() {
        createInteractor.switchToHomeView();
    }
}
