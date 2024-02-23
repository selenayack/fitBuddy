package com.example.fitbuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Diet1 extends AppCompatActivity {

    public Diet1() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet1);


            String[] dietListTitles1 = {
                "20 GRAM PROTEİNLİ, 1500 KALORİLİ DİYET"

        };

        String[] dietListContents1 = {
                "Bir Günlük Yiyecekler          Miktar         Ölçü\n" +

                        "\n" +
                        "Süt veya yoğurt                    250 gr          2 çay bardağı\n" +
                        "\n" +
                        "Yumurta                                40-50 gr       1 adet\n" +
                        "\n" +
                        "Et                                            30gr               2 köfte kadar\n" +
                        "\n" +
                        "Ekmek veya ekmek            125 gr            5 ince dilim\n" +
                        "yerine geçen yiyecekler\n" +
                        "\n" +
                        "Sebze                                   2 porsiyon     8 çorba kaşığı\n" +
                        "\n" +
                        "Meyve                                  2 porsiyon   Meyve değişim\n" +
                        "\n" +
                        "Şeker, bal veya reçel          140 gr            28 tatlı kaşığı\n" +
                        "\n" +
                        "Yağ                                        30 gr              6 tatlı kaşığı\n"+

                        "\n" +
                        "\n" +
                        "\n" +

                        "Sabah\n" +
                        "\n" +
                        "1 bardak çay\n" +
                        "1 yumurta \n" +
                        "2 tatlı kaşığı bal \n" +
                        "2 tatlı kaşığı tuzsuz tereyağı \n" +
                        "1 ince dilim ekmek\n" +
                        "\n" +
                        "Kuşluk\n" +
                        "\n" +
                        "1 porsiyon nişasta peltesi\n" +
                        "\n" +
                        "Öğle\n" +
                        "\n" +
                        "1 çay bardağı yoğurt veya yoğurtlu yayla çorbası\n" +
                        "5 çorba kaşığı pilav \n" +
                        "4 çorba kaşığı etsiz sebze yemeği \n" +
                        "1 porsiyon elma\n" +
                        "1 ince dilim ekmek\n" +
                        "\n" +
                        "İkindi\n" +
                        "\n" +
                        "1 porsiyon elma tatlısı\n" +
                        "\n" +
                        "Akşam\n" +
                        "\n" +
                        "5 çorba kaşığı makarna \n" +
                        "4 çorba kaşığı etsiz sebze yemeği \n" +
                        "1 porsiyon elma kompostosu\n" +
                        "1 ince dilim ekmek"+
                        "Gece\n" +
                        "\n" +
                        "1 çay bardağı süt(ballı)\n"+
                        "\n" +
                        "\n" +
                        "Bu liste 258 gr karbonhidrat, 42 gr yağ, 230 mg sodyum (575 mg tuz), 1280 mg potasyum içerir. Ekmek tuzlu yenirse alınacak olan sodyum miktarı 440 mg, (tuz miktarı 1100 mg) olacaktır. "



        };

        LinearLayout linearLayoutDietList = findViewById(R.id.linearLayoutDietList);

        for (int i = 0; i < dietListTitles1.length; i++) {
            View dietListItemView = getLayoutInflater().inflate(R.layout.diet_list_item, null);

            TextView textViewTitle = dietListItemView.findViewById(R.id.textViewTitle);
            TextView textViewContent = dietListItemView.findViewById(R.id.textViewContent);

            textViewTitle.setText(dietListTitles1[i]);
            textViewContent.setText(dietListContents1[i]);

            linearLayoutDietList.addView(dietListItemView);
        }
    }
}