package data_access;

import entities.CardSet;

import entities.CardSetFactory;
import use_cases.create.CreateDataAccessInterface;
import use_cases.edit.EditDataAccessInterface;
import use_cases.home.HomeDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An in-memory implementation of the data access object for storing card sets.
 * This implementation does NOT keep data persistently (like a database would).
 */
public class InMemoryDataAccessObject implements HomeDataAccessInterface,
        CreateDataAccessInterface, EditDataAccessInterface {
    private final List<CardSet> cardSets = new ArrayList<>();

    public InMemoryDataAccessObject() {
        // addPlaceHolderCards();
    }

    private void addPlaceHolderCards() {
        // in-memory testing data on app launch
        final CardSetFactory cardSetFactory = new CardSetFactory();
        final List<String> titles = List.of(
                "mat224h1",
                "pcl200h1",
                "csc207h1",
                "csc258h1",
                "imm428h1",
                "hmb265h1",
                "bio260h1",
                "hmb202h1",
                "pcl362h1",
                "hmb440h1",
                "lmp301h1"
        );
        final List<String> descriptions = List.of(
                "linear algebra 2",
                "drugs and the brain",
                "software design",
                "computer organization",
                "molecular immunology",
                "general & human genetics",
                "concepts in genetics",
                "introduction to health and disease",
                "introductory toxicology",
                "dementia",
                "biochemistry of human disease"
        );
        final List<String> fronts = List.of("f1", "f2", "f3");
        final List<String> backs = List.of("b1", "b2", "b3");
        for (int k = 0; k < titles.size(); k++) {
            String title = titles.get(k);
            String description = descriptions.get(k);
            List<List<String>> courseCards = new ArrayList<>();
            List<String> courseFronts = fronts.stream()
                    .map(front -> String.format("%s %s", title, front))
                    .toList();
            List<String> courseBacks = backs.stream()
                    .map(back -> String.format("%s %s", title, back))
                    .toList();

            for (int i = 0; i < courseFronts.size(); i++) {
                List<String> card = List.of(courseFronts.get(i), courseBacks.get(i));
                courseCards.add(card);
            }

            cardSets.add(cardSetFactory.create(
                    title,
                    description,
                    courseCards
            ));

            // prevents the IDs from being identical
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean setExistsByTitle(String cardSetTitle) {
        for (CardSet cardSet : cardSets) {
            if (cardSet.getTitle().equals(cardSetTitle)) return true;
        }
        return false;
    }

    @Override
    public Integer getIDByTitle(String cardSetTitle) {
        for (CardSet cardSet : cardSets) {
            if (cardSetTitle.equals(cardSet.getTitle())) return cardSet.getID();
        }
        return null;
    }

    @Override
    public void updateSet(CardSet newSet) {
        for (int k = 0; k < cardSets.size(); k++) {
            CardSet oldSet = cardSets.get(k);
            if (oldSet.getID() == newSet.getID()) {
                cardSets.set(k, newSet);
            }
        }
    }

    @Override
    public void saveSet(CardSet cardSet) {
        cardSets.add(cardSet);
    }

    @Override
    public List<CardSet> getCardSets() {
         return new ArrayList<>(cardSets).reversed();
    }

    @Override
    public void deleteCardSet(String cardSetTitle) {
        int k = 0;
        for (CardSet cardSet : cardSets) {
            if (cardSet.getTitle().equals(cardSetTitle)) {
                cardSets.remove(k);
                return;
            }
            k++;
        }
    }
}
