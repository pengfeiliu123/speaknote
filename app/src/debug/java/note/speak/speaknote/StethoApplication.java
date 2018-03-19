package note.speak.speaknote;

import com.facebook.stetho.Stetho;

import note.speak.speaknote.base.BaseApplication;


/**
 * Created by liupengfei on 2018/2/5 12:42.
 * chrome://inspect/#devices
 */

public class StethoApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
