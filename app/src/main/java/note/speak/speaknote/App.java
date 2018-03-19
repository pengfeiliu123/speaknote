package note.speak.speaknote;

import com.facebook.stetho.Stetho;

import org.litepal.LitePal;

import note.speak.speaknote.base.BaseApplication;


/**
 * Created by liupengfei on 2018/3/19 19:13.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        LitePal.initialize(this);
    }
}
