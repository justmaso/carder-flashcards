package interface_adapters;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/**
 * The view model for clean architecture.
 * @param <T> the type of the state object contained in the model.
 */
public class ViewModel<T> {
    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Gets the view name of this view model.
     * @return the name of this view model.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Gets the state of this view model
     * @return the state of this view model.
     */
    public T getState() {
        return state;
    }

    /**
     * Updates the state of this view model.
     * @param newState the new state.
     */
    public void setState(T newState) {
        state = newState;
    }

    /**
     * Fires a property changed event for the state of this view model.
     */
    public void firePropertyChanged() {
        this.support.firePropertyChange("state", null, state);
    }

    /**
     * Fires a property changed event for a specified property name.
     * @param propertyName the name of the property that has changed.
     */
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, state);
    }

    /**
     * Adds a PropertyChangeListener to this view model.
     * @param listener the listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
