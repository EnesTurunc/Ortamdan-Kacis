package com.example.ortamdankacis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CallSetupActivity extends AppCompatActivity {

    EditText etName, etTime;
    Button btnStart;
    LinearLayout rootLayout; // Arka plan rengini değiştirmek için
    TextView title; // Başlığı gizlemek için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_setup);

        // Tanımlamalar
        etName = findViewById(R.id.etName);
        etTime = findViewById(R.id.etTime);
        btnStart = findViewById(R.id.btnStartCall);
        rootLayout = (LinearLayout) btnStart.getParent(); // XML'deki ana layout'u alıyoruz
        // Eğer XML'de TextView'a id vermediysen kod patlamasın diye try-catch yapmaya gerek yok,
        // ama id verirsen daha temiz olur. Şimdilik child count ile gizleyeceğiz.

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Verileri Al
                String isim = etName.getText().toString();
                String sureString = etTime.getText().toString();

                if (isim.isEmpty()) isim = "Bilinmeyen Numara";
                if (sureString.isEmpty()) sureString = "5";

                int saniye = Integer.parseInt(sureString);

                // 2. SİHİR BURADA: EKRANI "SAHTE UYKU" MODUNA ALIYORUZ

                // a) Klavyeyi kapat (görüntüyü bozmasın)
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                // b) Tüm nesneleri gizle (Simsiyah olsun)
                for (int i = 0; i < rootLayout.getChildCount(); i++) {
                    rootLayout.getChildAt(i).setVisibility(View.GONE);
                }

                // c) Arka planı tam siyah yap
                rootLayout.setBackgroundColor(getResources().getColor(android.R.color.black));

                // d) Üstteki durum çubuğunu (saat, pil vs.) gizle
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                // e) Ekranın kapanmasını engelle (Gerçekten uykuya girmesin)
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                Toast.makeText(CallSetupActivity.this, "Telefonu masaya bırak...", Toast.LENGTH_SHORT).show();

                // 3. Zamanlayıcı
                final String finalIsim = isim;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Süre doldu! Arama Ekranını Aç
                        Intent intent = new Intent(CallSetupActivity.this, IncomingCallActivity.class);
                        intent.putExtra("ARAYAN_ISMI", finalIsim);
                        startActivity(intent);
                        finish(); // Bu ekranı öldür
                    }
                }, saniye * 1000);
            }
        });
    }
}