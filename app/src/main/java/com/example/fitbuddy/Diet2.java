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

public class Diet2 extends AppCompatActivity {

    public Diet2() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet2);


        String[] dietListTitles2 = {

                "30 GRAM PROTEİNLİ, 2000 KALORİLİ DİYET "

        };

        String[] dietListContents2 = {

                "Bir Günlük Yiyecekler          Miktar         Ölçü\n" +

                        "\n" +
                        "Süt veya yoğurt                    125 gr          1 çay bardağı\n" +
                        "\n" +
                        "Yumurta                                40-50 gr       1 adet\n" +
                        "\n" +
                        "\n" +
                        "Ekmek veya ekmek            125 gr            11 ince dilim\n" +
                        "yerine geçen yiyecekler\n" +
                        "\n" +
                        "Sebze                                   2 porsiyon     8 çorba kaşığı\n" +
                        "\n" +
                        "Meyve                                  3 porsiyon   Meyve değişim\n" +
                        "\n" +
                        "Şeker, bal veya reçel          180 gr            36 tatlı kaşığı\n" +
                        "\n" +
                        "Yağ                                        20 gr              4 tatlı kaşığı\n"+

                        "\n" +
                        "\n" +
                        "\n" +

                        "Sabah\n" +
                        "\n" +
                        "1 çay bardağı süt\n" +
                        "1 yumurta \n" +
                        "2 tatlı kaşığı bal \n" +
                        "2 tatlı kaşığı tuzsuz tereyağı \n" +
                        "3 ince dilim ekmek\n" +
                        "\n" +
                        "Öğle\n" +
                        "\n" +
                        "5 çorba kaşığı pilav \n" +
                        "4 çorba kaşığı etsiz sebze yemeği \n" +
                        "1 porsiyon ayva kompostosu\n" +
                        "3 ince dilim ekmek\n" +
                        "\n" +
                        "İkindi\n" +
                        "\n" +
                        "1 porsiyon elma tatlısı\n" +
                        "\n" +
                        "Akşam\n" +
                        "\n" +
                        "1 porsiyon yayla çorbası \n" +
                        "2 çorba kaşığı makarna \n" +
                        "4 çorba kaşığı etsiz sebze yemeği \n" +
                        "2 ince dilim ekmek\n\n"+
                        "Gece" +
                        "\n" +
                        "1 bardak çay 1 dilim şekersiz kek\n"+
                        "\n" +
                        "\n" +
                        " Bu liste 398 gr karbonhidrat, 32 gr yağ, 196 mg sodyum (498 mg tuz), 918 mg potasyum içerir. Ekmek tuzlu yenirse alınacak olan sodyum miktarı 1168 mg, (tuz miktarı 2970 mg) olacaktır. "


        };
        LinearLayout linearLayoutDietList = findViewById(R.id.linearLayoutDietList2);

        for (int i = 0; i < dietListTitles2.length; i++) {
            View dietListItemView = getLayoutInflater().inflate(R.layout.diet_list_item, null);

            TextView textViewTitle = dietListItemView.findViewById(R.id.textViewTitle);
            TextView textViewContent = dietListItemView.findViewById(R.id.textViewContent);

            textViewTitle.setText(dietListTitles2[i]);
            textViewContent.setText(dietListContents2[i]);

            linearLayoutDietList.addView(dietListItemView);
        }
    }
}