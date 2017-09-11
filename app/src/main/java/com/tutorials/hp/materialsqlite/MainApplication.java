package com.tutorials.hp.materialsqlite;

import android.app.Application;
import com.tutorials.hp.materialsqlite.mData.Galaxy;
import java.util.ArrayList;
import java.util.List;
import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;
/**
 * Created by Oclemy on for ProgrammingWizards Channel and http://www.camposha.info.
 - MainApplication class.
 - Extends android.app.Application.
 - This is a global class available to all classes within your project.
 - We initialize our RushORM configuration here using the initialize() method.
 */
public class MainApplication extends Application {
    /*
    when our app is created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Rush is initialized asynchronously to recieve a callback after it initialized
        // set an InitializeListener on the config object
        // All classes that extend RushObject or implement Rush must be added to the config
        List<Class<? extends Rush>> rushClasses = new ArrayList<>();
        rushClasses.add(Galaxy.class);

        //CONFIGURE AND INITIALIZE RUSHORM
        AndroidInitializeConfig config=new AndroidInitializeConfig(getApplicationContext(),rushClasses);
        RushCore.initialize(config);
    }
}