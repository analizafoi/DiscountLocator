package hr.foi.air.core;

/**
 * Defines an interface of a listener waiting for ReadyForData event.
 * When module is ready for data it will raise this event.
 */
public interface ReadyForDataListener {
    /**
     * Callback method. It will be called asynchronously when module is ready for data.
     * @param navigationItem Reference to an object raising event.
     */
    public void onReadyForData(NavigationItem navigationItem);
}
