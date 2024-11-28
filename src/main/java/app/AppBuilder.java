package app;

import data_access.InMemoryDataAccessObject;
import entities.CardSetFactory;
import interface_adapters.ViewManagerModel;
import interface_adapters.create.CreateController;
import interface_adapters.create.CreatePresenter;
import interface_adapters.create.CreateViewModel;
// import interface_adapters.edit.EditController;
// import interface_adapters.edit.EditPresenter;
import interface_adapters.edit.EditViewModel;
import interface_adapters.home.HomeController;
import interface_adapters.home.HomePresenter;
import interface_adapters.home.HomeViewModel;
import interface_adapters.study.StudyController;
import interface_adapters.study.StudyPresenter;
import interface_adapters.study.StudyState;
import interface_adapters.study.StudyViewModel;
import use_cases.create.CreateInputBoundary;
import use_cases.create.CreateInteractor;
import use_cases.create.CreateOutputBoundary;
// import use_cases.edit.EditInputBoundary;
// import use_cases.edit.EditInteractor;
// import use_cases.edit.EditOutputBoundary;
import use_cases.home.HomeInputBoundary;
import use_cases.home.HomeInteractor;
import use_cases.home.HomeOutputBoundary;
import use_cases.study.StudyInputBoundary;
import use_cases.study.StudyInteractor;
import use_cases.study.StudyOutputBoundary;
import views.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.CardLayout;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private HomeView homeView;
    private HomeViewModel homeViewModel;
    private HomeController homeController;
    private CreateView createView;
    private CreateViewModel createViewModel;
    //private EditView editView;
    private EditViewModel editViewModel;
    private StudyView studyView;
    private StudyViewModel studyViewModel;

    private final InMemoryDataAccessObject dataAO = new InMemoryDataAccessObject();
    private final CardSetFactory cardSetFactory = new CardSetFactory();

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the home view to our app.
     * @return this builder.
     */
    public AppBuilder addHomeView() {
        homeViewModel = new HomeViewModel();
        homeView = new HomeView(homeViewModel);
        cardPanel.add(homeView, homeViewModel.getViewName());
        return this;
    }

    /**
     * Adds the create view to our app.
     * @return this builder.
     */
    public AppBuilder addCreateView() {
        createViewModel = new CreateViewModel();
        createView = new CreateView(createViewModel);
        cardPanel.add(createView, createViewModel.getViewName());
        return this;
    }

    /**
     * Adds the edit view to our app.
     * @return this builder.
     */
    public AppBuilder addEditView() {
        editViewModel = new EditViewModel();
        //editView = new EditView(editViewModel);
        //cardPanel.add(editView, editViewModel.getViewName());
        return this;
    }

    /**
     * Adds the study view to our app.
     * @return this builder.
     */
    public AppBuilder addStudyView() {
        studyViewModel = new StudyViewModel();
        studyView = new StudyView(studyViewModel);
        cardPanel.add(studyView, studyViewModel.getViewName());
        return this;
    }

    /**
     * Adds the home use case to our app.
     * @return this builder.
     */
    public AppBuilder addHomeUseCase() {
        final HomeOutputBoundary homeOutputBoundary = new HomePresenter(
                viewManagerModel,
                homeViewModel,
                createViewModel,
                editViewModel,
                studyViewModel
        );
        final HomeInputBoundary homeInputBoundary = new HomeInteractor(
                dataAO,
                homeOutputBoundary
        );
        homeController = new HomeController(homeInputBoundary);
        homeView.setHomeController(homeController);
        return this;
    }

    /**
     * Adds the creation use case to our app.
     * @return this builder.
     */
    public AppBuilder addCreateUseCase() {
        final CreateOutputBoundary createOutputBoundary = new CreatePresenter(
                viewManagerModel,
                createViewModel,
                homeViewModel,
                studyViewModel
        );
        final CreateInputBoundary createInputBoundary = new CreateInteractor(
                dataAO,
                createOutputBoundary,
                cardSetFactory
        );
        final CreateController createController = new CreateController(createInputBoundary);
        createView.setCreateController(createController);

        return this;
    }

    public AppBuilder addEditUseCase() {
        // TODO
        return this;
    }

    public AppBuilder addStudyUseCase() {
        final StudyOutputBoundary studyOutputBoundary = new StudyPresenter(
                viewManagerModel,
                homeViewModel,
                studyViewModel
        );
        final StudyInputBoundary studyInputBoundary = new StudyInteractor(studyOutputBoundary,
                dataAO
        );
        final StudyController studyController = new StudyController(studyInputBoundary);
        studyView.setStudyController(studyController);
        homeView.setStudyController(studyController);
        return this;
    }

    /**
     * Creates the frame for our app and sets the home as the initial view.
     * @return the app.
     */
    public JFrame build() {
        final JFrame app = new JFrame("Carder Flashcards");

        // app configuration
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.setSize(850, 450);
        app.setLocationRelativeTo(null);
        app.add(cardPanel);

        // executes the home use case and refreshes the home so we can
        // see pre-existing card sets from the DAO
        homeController.execute();

        // app initially shows the home view
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        return app;
    }
}