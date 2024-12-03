package use_cases.edit;

import entities.CardSet;
import entities.CardSetFactory;

import java.util.List;

public class EditInteractor implements EditInputBoundary {
    private final EditDataAccessInterface editDAO;
    private final EditOutputBoundary editPresenter;
    private final CardSetFactory cardSetFactory;

    public EditInteractor(EditDataAccessInterface editDAO,
                          EditOutputBoundary editPresenter,
                          CardSetFactory cardSetFactory) {
        this.editDAO = editDAO;
        this.editPresenter = editPresenter;
        this.cardSetFactory = cardSetFactory;
    }

    @Override
    public void publishEdits(EditInputData editInputData) {
        if (editsValid(editInputData)) {
            editPresenter.editSuccessful();
        }
    }

    private boolean editsValid(EditInputData editInputData) {
        final int ID = editInputData.getID();
        final String title = editInputData.getTitle().strip();
        final String description = editInputData.getDescription().strip();
        final List<List<String>> cards = editInputData.getCards().stream()
                .map(card -> List.of(card.getFirst().strip(), card.getLast().strip()))
                .toList();

        // validate the title and description
        if (title.isBlank() || description.isBlank()) {
            editPresenter.editFailed("title/description missing\n" +
                    "please add a title/description");
            return false;
        } else if (editDAO.setExistsByTitle(title) && editDAO.getIDByTitle(title) != ID) {
            editPresenter.editFailed("title already exists.\n" +
                    "please choose another title");
            return false;
        }
        // validate the cards
        for (List<String> pairs : cards) {
            if (pairs.getFirst().isBlank() || pairs.getLast().isBlank()) {
                editPresenter.editFailed("one or more cards are not filled.\n" +
                        "please fill all cards");
                return false;
            }
        }
        // edits have been validated, add to DAO
        editDAO.updateSet(cardSetFactory.create(editInputData.getID(), title, description, cards));
        return true;
    }

    @Override
    public void switchToHomeView() {
        editPresenter.switchToHomeView();
    }
}