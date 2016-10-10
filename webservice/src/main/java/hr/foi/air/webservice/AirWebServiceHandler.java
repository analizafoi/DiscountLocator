package hr.foi.air.webservice;

/**
 * Created by Ivan on 10.10.2016..
 */

public interface AirWebServiceHandler {
    void onDataArrived(Object result, boolean ok, long timestamp);
}
