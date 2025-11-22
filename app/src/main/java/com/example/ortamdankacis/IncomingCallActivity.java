package com.example.ortamdankacis;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IncomingCallActivity extends AppCompatActivity {

    Ringtone ringtone;
    Vibrator vibrator;
    TextView tvName;
    FloatingActionButton btnAnswer, btnDecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. TAM EKRAN YAP (Saat, şarj vs gizle - Gerçekçi olması için)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Navigasyon tuşlarını (Geri, Home) gizlemeye çalış
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        setContentView(R.layout.activity_incoming_call);

        // 2. İsim Verisini Al
        tvName = findViewById(R.id.tvCallerName);
        String gelenIsim = getIntent().getStringExtra("ARAYAN_ISMI");
        if (gelenIsim != null) {
            tvName.setText(gelenIsim);
        }

        // 3. ZİL SESİ ÇAL
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ringtone.play();

        // 4. TİTREŞİM BAŞLAT
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            long[] pattern = {0, 1000, 1000}; // Bekle, Titre, Bekle...
            // Android sürümüne göre titreşim kodu
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));
            } else {
                vibrator.vibrate(pattern, 0);
            }
        }

        // 5. BUTON İŞLEMLERİ
        btnAnswer = findViewById(R.id.fabAnswer);
        btnDecline = findViewById(R.id.fabDecline);

        // Cevapla veya Reddet'e basınca her şeyi durdur ve çık
        View.OnClickListener stopListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopEverything();
                finish(); // Ana menüye dönmek istersen buraya Intent ekleyebilirsin
            }
        };

        btnAnswer.setOnClickListener(stopListener);
        btnDecline.setOnClickListener(stopListener);
    }

    // Uygulamadan çıkılırsa veya geri tuşuna basılırsa sus
    @Override
    protected void onDestroy() {
        stopEverything();
        super.onDestroy();
    }

    // Sesi ve titreşimi kesen fonksiyon
    private void stopEverything() {
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}