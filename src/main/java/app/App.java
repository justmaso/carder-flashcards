package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The main class for the Carder app.
 */
public class App {
    /**
     * Builds and runs the Carder app.
     * @param args unused arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final AppBuilder builder = new AppBuilder();
            final JFrame app = builder
                    .addHomeView()
                    .addCreateView()
//                    .addEditView()
//                    .addStudyView()
                    .addHomeUseCase()
                    .addCreateUseCase()
                    .addEditUseCase()
                    .addStudyUseCase()
                    .build();

            app.setVisible(true);
        });
    }
}