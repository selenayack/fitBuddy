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

public class Diet3 extends AppCompatActivity {

    public Diet3() {
        // Boş yapıcı metot
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet3);


        String[] dietListTitles3 = {

                "1 HAFTADA 3 KİLO VERDİREN DİYET  "



        };
        String[] dietListContents3 ={


                "1. Gün Listesi\n" +
                        "Sabah: 3-4 yk lor peyniri, bir dilim tam buğday ekmeği ve 1 fincan şekersiz yeşilçay\n\n" +
                        "Öğle Yemeği: 8 yemek kaşığı susuz zeytinyağlı sebze yemeği\n\n" +
                        "Akşam: 1 kase mercimek çorbası+ Bol salata + 4 yemek kaşığı light yoğurt\n\n\n\n" +
                        "2. Gün Listesi\n\n" +
                        "Sabah: 2 dilim etimek üstüne sürülen yağsız krem peynir ve 1 fincan şekersiz yeşilçay\n\n" +
                        "Öğle Yemeği: 90 gr haşlanmış tavuk ve yağsız mevsim salata\n\n" +
                        "Akşam Yemeği: Doyana kadar haşlanmış sebze ve 1 kase yoğurt\n\n\n\n" +
                        "3. Gün Listesi\n" +
                        "Sabah: 1 su bardağı süt +1 porsiyon meyve +2 yemek kaşığı yulaf+ tarçın\n\n" +
                        "Öğle Yemeği: 5 yemek kaşığı lor peyniri+ mevsim yağsız salata\n\n" +
                        "Akşam Yemeği: 6 yemek kaşığı zeytinyağlı sebze yemeği\n\n\n\n" +
                        "4. Gün Listesi\n" +
                        "Sabah: 1 adet haşlanmış yumurta+ 1 dilim peynir ve bol maydanoz ile yapılmış omlet\n\n" +
                        "Öğle: 4 yemek kaşığı etli kabak yemeği ve 2 yemek kaşığı haşlanmış karabuğday\n\n" +
                        "Akşam: 1 kase light yoğurt+ 1 adet orta boy armut\n\n\n\n" +
                        "5. Gün Listesi\n" +
                        "Sabah: 1 yumurta + 50 gr mantar ile yapılmış mantarlı omlet\n\n" +
                        "Öğle: 100 gr ızgara somon balığı+ bol salata\n\n" +
                        "Akşam: 100 gr haşlanmış tavuk + bol salata\n\n\n\n" +
                        "6. Gün Listesi\n" +
                        "Sabah: 2 yumurta+2 dilim etimek+ 1 fincan şekersiz yeşilçay\n\n" +
                        "Öğle: 6 yemek kaşığı kurubaklagil yemeği+ 1 kase yoğurt\n\n" +
                        "Akşam: 1 su bardağı kefir+ 2 yk yulaf+ 1 porsiyon meyve blenderdan geçirilecek\n\n\n\n" +
                        "7. Gün Listesi\n" +
                        "Sabah: 1 su bardağı süt+ ½ muz +8 adet badem\n\n" +
                        "Öğle: 100 gr et+ bol mevsim-yağsız salata\n\n" +
                        "Akşam: 1 kase kremasız sebze çorbası + 4 yemek kaşığı light yoğurt\n"



        };

        LinearLayout linearLayoutDietList = findViewById(R.id.linearLayoutDietList3);

        for (int i = 0; i < dietListTitles3.length; i++) {
            View dietListItemView = getLayoutInflater().inflate(R.layout.diet_list_item, null);

            TextView textViewTitle = dietListItemView.findViewById(R.id.textViewTitle);
            TextView textViewContent = dietListItemView.findViewById(R.id.textViewContent);

            textViewTitle.setText(dietListTitles3[i]);
            textViewContent.setText(dietListContents3[i]);

            linearLayoutDietList.addView(dietListItemView);
        }
    }
}