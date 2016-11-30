package hr.foi.air.discountlocator.iab;

import android.app.Activity;
import android.util.Log;

import hr.foi.air.core.PreferenceManagerHelper;
import hr.foi.air.discountlocator.iab_utils.IabHelper;
import hr.foi.air.discountlocator.iab_utils.IabResult;
import hr.foi.air.discountlocator.iab_utils.Inventory;
import hr.foi.air.discountlocator.iab_utils.Purchase;

/**
 * Created by Zlatko on 30.11.2016..
 */

public abstract class IabPerformer {
    private static IabHelper mHelper;
    private static Activity mActivity;
    private static Inventory mInventory;

    private static boolean mMapBought = false;
    public static final String TAG = "DISCOUNT_LOCATOR";

    // (arbitrary) request code for the purchase flow
    private static final int RC_REQUEST = 10001;

    //Production SKUs - App MUST be published in order for this to work.
    //protected static final String SKU_MAP = "hr.foi.air.discountlocator.iab.maps";

    //Testing SKUs
    protected static final String SKU_MAP = "android.test.purchased";
    //protected static final String SKU_MAP = "android.test.canceled";
    //protected static final String SKU_MAP = "android.test.refunded";
    //protected static final String SKU_MAP = "android.test.item_unavailable";

    // developers' public key
    private static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8....please make sure to use your onw key...";

    public static void BuyMap(Activity activity)
    {
        Log.d(TAG, "Launching purchase flow for map.");

        //TODO: for security, generate your payload here for verification. See the comments on
        //verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
        //an empty string, but on a production app you should carefully generate this.

        String payload = "";
        // when testing, uncomment different SKUs and check the clicking result
        mHelper.launchPurchaseFlow(mActivity, SKU_MAP, RC_REQUEST, mPurchaseFinishedListener, payload);

        /*
        //if buying subscriptions, the flow is slightly different
        if (!mHelper.subscriptionsSupported()) {
           complain("Subscriptions not supported on your device yet. Sorry!");
           return;
        }
        Log.d(TAG, "Launching purchase flow for subscription.");
        mHelper.launchPurchaseFlow(this,
           SKU_NOTIFICATIONS, IabHelper.ITEM_TYPE_SUBS,
           RC_REQUEST, mPurchaseFinishedListener, payload);
        */
    }

    public static void ConsumeMap(IabHelper.OnConsumeFinishedListener mConsumeFinishedListener)
    {
        if (mInventory != null) {
            //to consume use
            if (mInventory.hasPurchase(SKU_MAP)) {
                mHelper.consumeAsync(mInventory.getPurchase(SKU_MAP), mConsumeFinishedListener);
            }
        }
    }

    /**
     * Calling ConsumeMap method with default listener (logging result only).
     */
    public static void ConsumeMap()
    {
        ConsumeMap(mDefaultConsumeFinishedListener);
    }

    public static IabHelper setupIabHelper(Activity activity)
    {
        mActivity = activity;

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(mActivity, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    Log.d(TAG, "Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mDefaultGotInventoryListener);
            }
        });
        return mHelper;
    }

    // Callback for when a purchase is finished
    static IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                Log.d(TAG, "Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_MAP)) {
                Log.d(TAG, "Purchase is map. Saving...");
                PreferenceManagerHelper.setMapBought(true, mActivity);
            }
        }
    };

    /** Verifies the developer payload of a purchase. */
    static boolean verifyDeveloperPayload(Purchase p) {
        //String payload = p.getDeveloperPayload();

       /*
        * TODO: verify that the developer payload of the purchase is correct. It will be
        * the same one that you sent when initiating the purchase.
        *
        * WARNING: Locally generating a random string when starting a purchase and
        * verifying it here might seem like a good approach, but this will fail in the
        * case where the user purchases an item on one device and then uses your app on
        * a different device, because on the other device you will not have access to the
        * random string you originally generated.
        *
        * So a good developer payload has these characteristics:
        *
        * 1. If two different users purchase an item, the payload is different between them,
        *    so that one user's purchase can't be replayed to another user.
        *
        * 2. The payload must be such that you can verify it even when the app wasn't the
        *    one who initiated the purchase flow (so that items purchased by the user on
        *    one device work on other devices owned by the user).
        *
        * Using your own server to store and verify developer payloads across app
        * installations is recommended.
        */

        return true;
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    static IabHelper.QueryInventoryFinishedListener mDefaultGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            mInventory = inventory;

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */
            Purchase mapPurchase = inventory.getPurchase(SKU_MAP);
            mMapBought = (mapPurchase != null && verifyDeveloperPayload(mapPurchase));
            Log.d(TAG, "User bought MAP: " + (mMapBought ? "YES" : "NO"));

            PreferenceManagerHelper.setMapBought(mMapBought, mActivity);

            //updateUi();
            //setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // Called when consumption is complete
    static IabHelper.OnConsumeFinishedListener mDefaultConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;
            //check which sku returned
            //..
            //..

            //check result
            if (result.isSuccess()) {
                //successfully consumed
                //do the logic here
            }
            else {
                Log.d(TAG, "Error while consuming: " + result);
            }

            mHelper.queryInventoryAsync(mDefaultGotInventoryListener);

            //updateUi();
            //setWaitScreen(false);
            Log.d(TAG, "End consumption flow.");
        }
    };
}
