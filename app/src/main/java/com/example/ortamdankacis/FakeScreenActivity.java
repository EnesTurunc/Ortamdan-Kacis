package com.example.ortamdankacis;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FakeScreenActivity extends AppCompatActivity {

    LinearLayout layoutBattery, layoutUpdate;
    ProgressBar progressBar;
    TextView tvProgress;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tam Ekran Yap (Status bar gizle)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ekranın kapanmasını engelle (Uyanık kalsın)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_fake_screen);

        layoutBattery = findViewById(R.id.layoutBattery);
        layoutUpdate = findViewById(R.id.layoutUpdate);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);

        // MainActivity'den gelen emri oku
        String mod = getIntent().getStringExtra("MOD");

        if (mod != null && mod.equals("SARJ")) {
            // Şarj Modunu Aç
            layoutBattery.setVisibility(View.VISIBLE);
            layoutUpdate.setVisibility(View.GONE);
        } else {
            // Güncelleme Modunu Aç
            layoutBattery.setVisibility(View.GONE);
            layoutUpdate.setVisibility(View.VISIBLE);
            startFakeUpdate(); // İlerlemeyi başlat
        }
    }

    // Sahte Güncelleme İlerlemesi
    private void startFakeUpdate() {
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1; // %1 arttır

                    // Android Studio UI güncellemesi için Handler kullanır
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            tvProgress.setText("%" + progressStatus);
                        }
                    });

                    try {
                        // Rastgele bekleme süresi (Gerçekçi olsun diye bazen hızlı bazen yavaş)
                        long sleepTime = (long) (Math.random() * 500 + 200);
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // %100 olunca ne olsun? (İstersen buraya telefon kapanıyormuş gibi siyah ekran kodu ekleyebiliriz)
            }
        }).start();
    }

    // ŞAKA İÇİN EN ÖNEMLİ KISIM: GERİ TUŞUNU İPTAL ETME
    // Kullanıcı geri tuşuna basınca hiçbir şey yapma :)
    @Override // Bu kod Android'in kendi geri tuşu fonksiyonunu ezer
    public void onBackPressed() {
        // İpucu: Çıkmak için kullanıcıya tüyo vermiyoruz ama sen geliştiricisin.
        // Çıkmak için "Home" tuşuna basıp uygulamayı kapatman gerekecek.
        // Veya buraya "Toast.makeText(this, "Sistem Meşgul!", Toast.LENGTH_SHORT).show();" yazabilirsin.
        super.onBackPressed();
    }
}