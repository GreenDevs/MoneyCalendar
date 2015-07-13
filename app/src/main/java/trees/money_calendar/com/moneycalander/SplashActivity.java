package trees.money_calendar.com.moneycalander;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int windowWidth = size.y / 2;


        final Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);


        final FrameLayout upper = (FrameLayout) findViewById(R.id.upeerpart);
        LinearLayout hide = (LinearLayout) findViewById(R.id.viewToCover);
        ImageView show = (ImageView) findViewById(R.id.viewToUnveal);
        ImageView image = (ImageView) findViewById(R.id.logo);
        TextView moneyCalander = (TextView) findViewById(R.id.text);

        upper.getLayoutParams().height = windowWidth;
        hide.getLayoutParams().height = windowWidth - 5;
        show.getLayoutParams().height = windowWidth - 5;

        show.getLayoutParams().width = windowWidth - 5;

        image.getLayoutParams().height = windowWidth / 3;
        moneyCalander.getLayoutParams().height = windowWidth / 3;





        upper.startAnimation(scale);



        Thread loadMain = new Thread() {
            public void run() {
                try {
                    sleep(1500);

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    finish();
                }
            }

        };

        loadMain.start();


    }


}

