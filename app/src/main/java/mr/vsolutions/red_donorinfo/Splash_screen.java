package mr.vsolutions.red_donorinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_screen extends AppCompatActivity {

  //  private InterstitialAd mInterstitialAd;
    private boolean gameIsInProgress;
    private long timerMilliseconds;
    private CountDownTimer countDownTimer;
    private static final long GAME_LENGTH_MILLISECONDS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

//        TypeWriter typeWriter = findViewById(R.id.tv);
//        typeWriter.setCharacterDelay(100);
//        typeWriter.animateText(getString(R.string.splash_Title));

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.test_addmob_intertitialId));
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                if (Comman.isNetworkAvailable(getApplicationContext())) {
//                    Intent intetn = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intetn);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                Intent i=new Intent(getBaseContext(),MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
       startGame();
      //  showInterstitial();

    }
    private void startGame() {
//        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
//            AdRequest adRequest = new AdRequest.Builder().build();
//            mInterstitialAd.loadAd(adRequest);
//        }
        resumeGame(GAME_LENGTH_MILLISECONDS);
    }

    private void resumeGame(long milliseconds) {
        gameIsInProgress = true;
        timerMilliseconds = milliseconds;
        createTimer(milliseconds);
        countDownTimer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }
    @Override
    public void onResume() {
        super.onResume();

        if (gameIsInProgress) {
            resumeGame(timerMilliseconds);
        }
    }
    private void createTimer(final long milliseconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                timerMilliseconds = millisUnitFinished;
            }

            @Override
            public void onFinish() {
                try {
                    gameIsInProgress = false;

                        Intent i=new Intent(getBaseContext(),MainActivity.class);
                        startActivity(i);
                        finish();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    Intent i=new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
    }
}