package use_cases.study;

import interface_adapters.ViewManagerModel;
import interface_adapters.home.HomeViewModel;
import interface_adapters.study.StudyPresenter;
import interface_adapters.study.StudyState;
import interface_adapters.study.StudyViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the overlapping functionality of the study use case.
 */
public class StudyInteractorTest {
    private StudyInteractor studyInteractor;
    private StudyState studyState;
    private ViewManagerModel vmm;

    @BeforeEach
    public void setUp() {
        studyState = new StudyState();
        studyState.setTitle("setup-title");
        studyState.setDescription("setup-description");
        studyState.setOriginalCards(List.of(
                List.of("f1", "b1"),
                List.of("f2", "b2"),
                List.of("f3", "b3"),
                List.of("f4", "b4")
        ));
        vmm = new ViewManagerModel();
        vmm.setState("study");
        StudyViewModel svm = new StudyViewModel();
        HomeViewModel hvm = new HomeViewModel();
        svm.setState(studyState);
        StudyPresenter studyPresenter = new StudyPresenter(vmm, svm, hvm);
        studyInteractor = new StudyInteractor(studyPresenter);
    }

    @Test
    public void testInitialState() {
        assertEquals("setup-title", studyState.getTitle());
        assertEquals("setup-description", studyState.getDescription());
        assertEquals(0, studyState.getCurrentIndex());
        assertEquals(4, studyState.getActiveCards().size());
        assertTrue(studyState.getFrontVisible());
        assertFalse(studyState.getShufflingOn());
        assertFalse(studyState.getSortingOn());
    }

    @Test
    public void testUnsortMoveToLeft() {
        // moves to the next card initially
        studyInteractor.moveToNextCard();
        assertEquals(1, studyState.getCurrentIndex());
        assertTrue(studyState.getFrontVisible());

        // flips the card
        studyInteractor.flip();
        assertFalse(studyState.getFrontVisible());

        // moves to the previous card
        studyInteractor.moveToPrevCard();
        assertTrue(studyState.getFrontVisible());
        assertEquals(0, studyState.getCurrentIndex());
    }

    @Test
    public void testUnsortMoveToRight() {
        assertEquals(0, studyState.getCurrentIndex());
        assertTrue(studyState.getFrontVisible());

        // flips the first card
        studyInteractor.flip();
        assertFalse(studyState.getFrontVisible());

        // moves to the next card
        studyInteractor.moveToNextCard();
        assertEquals(1, studyState.getCurrentIndex());
        assertTrue(studyState.getFrontVisible());
    }

    @Test
    public void testFlip() {
        assertTrue(studyState.getFrontVisible());
        assertEquals("f1", studyState.getActiveFront());
        assertEquals("b1", studyState.getActiveBack());
        assertEquals(0, studyState.getCurrentIndex());
        studyInteractor.flip();
        assertFalse(studyState.getFrontVisible());
        assertEquals(0, studyState.getCurrentIndex());
        assertEquals("f1", studyState.getActiveFront());
        assertEquals("b1", studyState.getActiveBack());
    }

    @Test
    public void testToggleShuffle() {
        assertFalse(studyState.getShufflingOn());
        studyInteractor.toggleShuffling();
        assertTrue(studyState.getShufflingOn());
    }

    @Test
    public void testUnsortShuffle() {
        final List<List<String>> originalCards = studyState.getPreviousActiveCards();
        studyInteractor.moveToNextCard();
        studyInteractor.moveToNextCard();

        // shuffle -> restart at the beginning
        assertFalse(studyState.getShufflingOn());
        assertFalse(studyState.getSortingOn());
        assertFalse(studyState.getStudyingSorted());
        studyInteractor.toggleShuffling();
        assertTrue(studyState.getShufflingOn());
        assertFalse(studyState.getSortingOn());
        assertFalse(studyState.getStudyingSorted());
        assertNotSame(originalCards, studyState.getActiveCards());
    }

    @Test
    public void testShuffleReset() {
        studyInteractor.moveToNextCard();
        assertNotEquals(0, studyState.getCurrentIndex());
        studyInteractor.toggleShuffling();
        assertEquals(0, studyState.getCurrentIndex());
    }

    @Test
    public void testToggleSorting() {
        assertFalse(studyState.getSortingOn());
        studyInteractor.toggleSorting();
        assertTrue(studyState.getSortingOn());
    }

    @Test
    public void testSortingReset() {
        studyInteractor.moveToNextCard();
        studyInteractor.flip();
        assertNotEquals(0, studyState.getCurrentIndex());
        assertFalse(studyState.getFrontVisible());
        studyInteractor.toggleSorting();
        assertEquals(0, studyState.getCurrentIndex());
        assertTrue(studyState.getFrontVisible());
    }

    @Test
    public void testAddToUnknown() {
        studyInteractor.toggleSorting();
        assertTrue(studyState.getUnknownCards().isEmpty());
        studyInteractor.addToUnknown(
                studyState.getActiveFront(),
                studyState.getActiveBack()
        );
        assertFalse(studyState.getUnknownCards().isEmpty());
    }

    @Test
    public void testAddToKnown() {
        assertTrue(studyState.getUnknownCards().isEmpty());
        studyInteractor.addToKnown();
        assertTrue(studyState.getUnknownCards().isEmpty());
    }

    @Test
    public void testUnsortRestart() {
        // restarting keeps shuffling
        List<List<String>> initialCards = studyState.getActiveCards();
        studyInteractor.restart();
        assertSame(initialCards, studyState.getActiveCards());
    }

    @Test
    public void testStudySorted() {
        studyInteractor.toggleSorting();
        assertTrue(studyState.getSortingOn());

        // 0th card
        studyInteractor.addToUnknown(
                studyState.getActiveFront(),
                studyState.getActiveBack()
        );
        studyInteractor.moveToNextCard();

        // 1st card
        studyInteractor.addToKnown();
        studyInteractor.moveToNextCard();

        // 2nd card
        studyInteractor.addToUnknown(
                studyState.getActiveFront(),
                studyState.getActiveBack()
        );
        studyInteractor.moveToNextCard();

        // 3rd (and final card)
        studyInteractor.addToKnown();

        assertEquals(2, studyState.getUnknownCards().size());
        studyInteractor.studyUnknown();
        assertEquals(0, studyState.getCurrentIndex());
        assertEquals(0, studyState.getUnknownCards().size());
        final List<List<String>> newActiveCards = studyState.getActiveCards();
        assertEquals(2, newActiveCards.size());

        assertEquals("f1", newActiveCards.getFirst().getFirst());
        assertEquals("b1", newActiveCards.getFirst().getLast());
        assertEquals("f3", newActiveCards.getLast().getFirst());
        assertEquals("b3", newActiveCards.getLast().getLast());
    }

    @Test
    public void testFullRestart() {
        studyInteractor.toggleShuffling();
        studyInteractor.toggleSorting();
        studyInteractor.addToUnknown(
                studyState.getActiveFront(),
                studyState.getActiveBack()
        );
        studyInteractor.moveToNextCard();
        studyInteractor.addToKnown();
        studyInteractor.moveToNextCard();
        assertTrue(studyState.getShufflingOn());
        assertTrue(studyState.getSortingOn());
        assertFalse(studyState.getUnknownCards().isEmpty());
        assertEquals(2, studyState.getCurrentIndex());
        studyInteractor.fullyRestart();
        assertFalse(studyState.getShufflingOn());
        assertTrue(studyState.getSortingOn());
        assertTrue(studyState.getUnknownCards().isEmpty());
        assertEquals(0, studyState.getCurrentIndex());
    }

    @Test
    public void testToggleSort() {
        assertFalse(studyState.getSortingOn());
        studyInteractor.toggleSorting();
        assertTrue(studyState.getSortingOn());
    }

    @Test
    public void testUndo() {
        studyInteractor.toggleSorting();
        assertTrue(studyState.getUnknownCards().isEmpty());
        assertEquals(0, studyState.getCurrentIndex());
        studyInteractor.addToUnknown(
                studyState.getActiveFront(),
                studyState.getActiveBack()
        );
        assertFalse(studyState.getUnknownCards().isEmpty());
        studyInteractor.undo();
        assertTrue(studyState.getUnknownCards().isEmpty());
    }

    @Test
    public void testSwitchToHomeView() {
        assertNotNull(studyState.getActiveCards());
        assertEquals("study", vmm.getState());
        studyInteractor.switchToHomeView();
        assertNull(studyState.getActiveCards());
        assertEquals("home", vmm.getState());
    }

}
