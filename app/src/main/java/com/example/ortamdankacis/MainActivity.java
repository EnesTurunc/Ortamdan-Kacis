package com.example.ortamdankacis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    // Tanımlamalar
    CardView cardCall, cardBattery, cardUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML'deki kartları Java'ya bağlıyoruz
        cardCall = findViewById(R.id.cardFakeCall);
        cardBattery = findViewById(R.id.cardFakeBattery);
        cardUpdate = findViewById(R.id.cardFakeUpdate);

        // --- DÜZELTİLEN KISIM (1. BUTON) ---
        // Sahte Arama Tıklaması
        cardCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // İçerideki fazladan onClickListener'ı sildik, direkt çalıştırıyoruz
                Intent intent = new Intent(MainActivity.this, CallSetupActivity.class);
                startActivity(intent);
            }
        });

        // 2. Şarj Bitti Tıklaması
        cardBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FakeScreenActivity.class);
                intent.putExtra("MOD", "SARJ");
                startActivity(intent);
            }
        });

        // 3. Güncelleme Tıklaması
        cardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FakeScreenActivity.class);
                intent.putExtra("MOD", "GUNCELLEME");
                startActivity(intent);
            }
        });
    }
}