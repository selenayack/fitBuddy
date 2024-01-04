package com.example.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Diet5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet5);




        String[] dietListTitles5 = {
                "KETOJONİK DİYET "


        };



        String[] dietListContents5={
                "1. Örnek Liste\n" +
                        "\n" +
                        "Sabah: Yumurta + avokado ve domatesli çırpılmış yumurta + yer fıstığı + dil peyniri\n\n" +
                        "Öğle: Etli cevizli karışık salata + yaban mersinli kefirli yoğurt (Örn: Cevizli kereviz salatası + Ev yapımı yoğurt)\n\n" +
                        "Ara öğün: Havuç + fıstık ezmesi\n\n" +
                        "Ara: Yumurta (Örn: Sebzeli omlet)\n\n" +
                        "Akşam: Kabak/kırmızı biber/zeytinyağı ve yer fıstığı ile pişmiş tavuk sote + Zeytinyağında sotelenmiş brokoli (Örn: Domatesli tavuk sote)\n" +
                        "\n" +
                        "2. Örnek Liste\n" +
                        "\n" +
                        "Sabah: Karışık biberli domatesli omlet + Karışık kuruyemiş + Dil peyniri (Örn: Kabaklı peynirli omlet)\n\n" +
                        "Öğle: Balıklı karışık salata (Ton balıklı salata)\n\n" +
                        "Ara öğün: Avokado (Avokadolu Tarifler)\n\n" +
                        "Ara öğün: Smoothie: (Ispanak + süt + Dondurulmuş vişne/yaban mersini/ahududu/böğürtlen vb. den biri) (Muzlu smoothie veya Yeşil smoothie)\n\n" +
                        "Akşam: Izgara ya da fırın tavuk + Zeytinyağlı sotelenmiş kabak/kırmızı/yeşil biber (Zeytinyağlı kabak)\n\n" +
                        "3. Örnek Liste\n" +
                        "\n" +
                        "Sabah: Tereyağlı 2 yumurta + avokado + böğürtlen\n\n" +
                        "Öğle: Karışık yeşillikli domatesli ızgara somon (Garnitürlü fırın somon)\n\n" +
                        "Ara öğün: Kale (kıvırcık kara lahana) cipsi\n\n" +
                        "Ara öğün: Peynir dilimleri + renkli dolma biberler (Zeytinyağlı biber dolması)\n\n" +
                        "Akşam: Izgara tavuk göğüs + karnabahar püresi + taze fasulye (Izgara tavuk şiş + karnabahar püresi + zeytinyağlı taze fasulye)\n"



        };

        LinearLayout linearLayoutDietList = findViewById(R.id.linearLayoutDietList5);

        for (int i = 0; i < dietListTitles5.length; i++) {
            View dietListItemView = getLayoutInflater().inflate(R.layout.diet_list_item, null);

            TextView textViewTitle = dietListItemView.findViewById(R.id.textViewTitle);
            TextView textViewContent = dietListItemView.findViewById(R.id.textViewContent);

            textViewTitle.setText(dietListTitles5[i]);
            textViewContent.setText(dietListContents5[i]);

            linearLayoutDietList.addView(dietListItemView);
        }
    }
}