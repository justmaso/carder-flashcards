package data_access;

import entities.Card;
import entities.CardSet;
import org.json.JSONArray;
import org.json.JSONObject;
import use_cases.create.CreateDataAccessInterface;
import use_cases.edit.EditDataAccessInterface;
import use_cases.home.HomeDataAccessInterface;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FileDataAccessObject implements HomeDataAccessInterface,
        CreateDataAccessInterface , EditDataAccessInterface {
    private JSONArray jsonCardSets;

    public FileDataAccessObject() {
        loadFile();
    }

    private void loadFile() {
        try {
            String jsonString = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("sets.json")).toURI()));
            jsonCardSets = !jsonString.isEmpty() ? new JSONArray(jsonString) : new JSONArray();
        } catch(IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean setExistsByTitle(String cardSetTitle) {
        for (int k = 0; k < jsonCardSets.length(); k++) {
            JSONObject cardSet = jsonCardSets.getJSONObject(k);
            if (cardSet.getString("title").equals(cardSetTitle)) return true;
        }
        return false;
    }

    @Override
    public Integer getIDByTitle(String cardSetTitle) {
        for (int k = 0; k < jsonCardSets.length(); k++) {
            final JSONObject cardSet = jsonCardSets.getJSONObject(k);
            if (cardSetTitle.equals(cardSet.getString("title"))) return cardSet.getInt("ID");
        }
        return null;
    }

    @Override
    public void updateSet(CardSet newSet) {
        for (int k = 0; k < jsonCardSets.length(); k++) {
            JSONObject cardSet = jsonCardSets.getJSONObject(k);
            if (cardSet.getInt("ID") == newSet.getID()) {
                cardSet.put("title", newSet.getTitle());
                cardSet.put("description", newSet.getDescription());
                final JSONArray jsonCards = new JSONArray();
                for (List<String> pair : newSet.getCards()) {
                    jsonCards.put(pair);
                }
                cardSet.put("cards", jsonCards);

                try {
                    Files.writeString(
                            Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("sets.json")).toURI()),
                            jsonCardSets.toString(4),
                            StandardOpenOption.WRITE);
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException("error updating card set", ex);
                }
                break;
            }
        }
    }

    @Override
    public void saveSet(CardSet cardSet) {
        JSONObject jsonCardSet = new JSONObject();
        jsonCardSet.put("ID", cardSet.getID());
        jsonCardSet.put("title", cardSet.getTitle());
        jsonCardSet.put("description", cardSet.getDescription());
        JSONArray jsonCards = new JSONArray();
        for (List<String> pair : cardSet.getCards()) {
            jsonCards.put(pair);
        }
        jsonCardSet.put("cards", jsonCards);
        jsonCardSets.put(jsonCardSet);

        try {
            Files.writeString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("sets.json")).toURI()),
                    jsonCardSets.toString(4),
                    StandardOpenOption.WRITE);
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("error saving card set", ex);
        }
    }

    @Override
    public List<CardSet> getCardSets() {
        final List<CardSet> cardSets = new ArrayList<>();

        for (int k = 0; k < jsonCardSets.length(); k++) {
            final JSONObject cardSetJson = jsonCardSets.getJSONObject(k);

            final int ID = cardSetJson.getInt("ID");
            final String title = cardSetJson.getString("title");
            final String description = cardSetJson.getString("description");

            List<Card> cards = new ArrayList<>();
            JSONArray cardsArray = cardSetJson.getJSONArray("cards");
            for (int j = 0; j < cardsArray.length(); j++) {
                final JSONArray cardJson = cardsArray.getJSONArray(j);
                cards.add(new Card(
                    cardJson.getString(0),
                    cardJson.getString(1)
                ));
            }

            CardSet cardSet = new CardSet(ID, title, description, cards);
            cardSets.add(cardSet);
        }
        return cardSets.reversed();
    }

    @Override
    public void deleteCardSet(String cardSetTitle) {
        boolean removed = false;
        for (int i = 0; i < jsonCardSets.length(); i++) {
            JSONObject cardSet = jsonCardSets.getJSONObject(i);
            if (cardSet.getString("title").equals(cardSetTitle)) {
                jsonCardSets.remove(i);
                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new IllegalArgumentException("card set not found: " + cardSetTitle);
        }

        try {
            Files.writeString(Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("sets.json")).toURI()), jsonCardSets.toString(4));
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("error deleting card set", ex);
        }
    }

    private void printFileContents() {
        try {
            System.out.println(Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("sets.json")).toURI())));
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
