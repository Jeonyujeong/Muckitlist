package foodlist.muckitlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 매미 on 2017-12-01.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
    }
}