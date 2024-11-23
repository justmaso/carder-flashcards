package data_access;

import entities.CardSet;

import entities.CardSetFactory;
import use_cases.create.CreateDataAccessInterface;
import use_cases.edit.EditDataAccessInterface;
import use_cases.home.HomeDataAccessInterface;
import use_cases.study.StudyDataAccessInterface;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An in-memory implementation of the data access object for storing card sets.
 * This implementation does NOT keep data persistently (like a database would).
 */
public class InMemoryDataAccessObject implements HomeDataAccessInterface,
        CreateDataAccessInterface, EditDataAccessInterface, StudyDataAccessInterface {
    private final List<CardSet> cardSets = new ArrayList<>();

    public InMemoryDataAccessObject() {
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
            List<String> courseFronts = fronts.stream()
                    .map(front -> String.format("%s %s", title, front))
                    .toList();
            List<String> courseBacks = backs.stream()
                    .map(back -> String.format("%s %s", title, back))
                    .toList();

            cardSets.add(cardSetFactory.create(
                    title,
                    description,
                    courseFronts,
                    courseBacks
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

    @Override
    public CardSet getCardSet(String cardSetTitle) {
        int k = 0;
        CardSet cards = new CardSet();
        for (CardSet cardSet : cardSets) {
            if (cardSet.getTitle().equals(cardSetTitle)) {
                cards = cardSets.get(k);
                break;
            }
        }
        return cards;
    }

    @Override
    public void updateSet(CardSet newSet) {
        final int setID = newSet.getID();
        int k = 0;
        for (CardSet cardSet : cardSets) {
            if (cardSet.getID() == setID) {
                cardSets.set(k, newSet);
            }
            k++;
        }
    }
}
