// Sergio Esteban Pellejero
// Álvaro de Caso Morejón

package com.alvaro.sergio.smov_yamba;

import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

public class SupportServices {

    public static final String CONSUMER_KEY="rqFO2Q3L6HLGusJ43kpLSRh51";
    public static final String CONSUMER_SECRET="JZUmn8jKwJ8af3S9y64O8U0KxY77Uq400F0b6qhU8iCUuYOFHl";
    public static final String ACCESS_TOKEN="1052940451936907264-5embE34o8XzKyjFmXdKcr4rY0GSqAm";
    public static final String ACCESS_SECRET="mMYubNM7VnSNBOPxjN0E8MimVafBYhDRYf1Kcbl7Gxt1s";

    public static final String TAG="StatusActivity";

    public static final int MAX_CHARACTERS = 140;

    public static final String MESSAGE_SEND = "Message sent";
    public static final String MESSAGE_SEND_NOT_SEND = "Message not sent";
    public static final String INTERNET_FAIL = "Internet Access OFF";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String TAGService = "RefreshService";

    /*--------------- Constantes relacionadas con la bbdd ---------------*/

    public static final String DB_NAME = "timeline.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "status";


    public static final String AUTHORITY = "com.alvaro.sergio.smov_yamba.StatusProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int STATUS_ITEM = 1;
    public static final int STATUS_DIR = 2;

    public static final String ID = BaseColumns._ID;
    public static final String USER = "user";
    public static final String MESSAGE = "message";
    public static final String CREATED_AT = "created_at";

    public static final String DEFAULT_SORT = CREATED_AT +" DESC";


}

