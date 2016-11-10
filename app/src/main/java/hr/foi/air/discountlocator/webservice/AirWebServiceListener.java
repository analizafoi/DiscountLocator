package hr.foi.air.discountlocator.webservice;

import java.lang.reflect.Type;

/**
 * Created by Ivan on 24.10.2016..
 */

public interface AirWebServiceListener {
    void onDataArrived(Object result, Type resultType);
}
