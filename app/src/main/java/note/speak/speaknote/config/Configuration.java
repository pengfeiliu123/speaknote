package note.speak.speaknote.config;

import android.net.Uri;

import com.nuance.speechkit.PcmFormat;

/**
 * All Nuance Developers configuration parameters can be set here.
 *
 * Copyright (c) 2015 Nuance Communications. All rights reserved.
 */
public class Configuration {

    //All fields are required.
    //Your credentials can be found in your Nuance Developers portal, under "Manage My Apps".
    public static final String APP_KEY = "7d0df2daca514634024a81a4fd3342fb3f8b649a293419cc99b295533eff64bb0d5695d7a8db0baa258d4f23c0911eff1c829a45b7b3115066a7a3a4a6085fb3";
    public static final String APP_ID = "NMDPTRIAL_854408100_qq_com20180317214539";
    public static final String SERVER_HOST = "sslsandbox-nmdp.nuancemobility.net";
    public static final String SERVER_PORT = "443";

    public static final String LANGUAGE = "!LANGUAGE!";

    public static final Uri SERVER_URI = Uri.parse("nmsps://" + APP_ID + "@" + SERVER_HOST + ":" + SERVER_PORT);

    //Only needed if using NLU
    public static final String CONTEXT_TAG = "!NLU_CONTEXT_TAG!";

    public static final PcmFormat PCM_FORMAT = new PcmFormat(PcmFormat.SampleFormat.SignedLinear16, 16000, 1);
    public static final String LANGUAGE_CODE = (Configuration.LANGUAGE.contains("!") ? "eng-USA" : Configuration.LANGUAGE);

}



