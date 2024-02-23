package com.example.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Diet4 extends AppCompatActivity {

    public Diet4() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet4);


        String[] dietListTitles4 = {

                "VEJETERYAN DİYET "



        };
        String[] dietListContents4 ={


                "KAHVALTI\n" +
                        "\n" +
                        "Örnek Kahvaltı Menülerimiz;\n" +
                        "\n" +
                        "1.Gün: Dolgulu Omlet Bohça,Zeytin ezmesi, Biber, Salatalık, Domates Köy Ekmeği\n\n" +
                        "2.Gün: Hellimli Avokadolu Açık Sandviç, Sebze\n\n" +
                        "3.Gün: Susamlı Göz Yumurta, Süzme Peynir, Yeşil Zeytin, Domates- Salatalık, Çavdar Ekmeği\n\n" +
                        "4.Gün: Tulum Peynirli Tost, Kuru Erik- Badem, Sebzeler\n\n" +
                        "5.Gün: Menemen, Süzme Peynir, Yeşil Zeytin, Kepek Ekmeği\n\n" +
                        "6.Gün: Otlu Omlet, Dil Peyniri,  Ceviz, Salatalık, Maydanoz, Tam Buğday Ekmeği\n" +
                        "Vejetaryen Örnek Menü\n\n" +
                        "1.ARA:\n" +
                        "\n" +
                        "Örnek 1. Ara Menülerimiz;\n" +
                        "\n" +
                        "1. Gün: Yer Fıstıklı Atom Bar \n" +
                        "\n" +
                        "2. Gün: Karabuğdaylı Grisini- Kefir\n" +
                        "\n" +
                        "3. Gün: Mevsim Meyveleri ( Elma, Portakal, Mandalina vb.)\n" +
                        "\n" +
                        "4. Gün: Chialı Yoğurt, Keten Tohumlu Yoğurt, Meyveli Yoğurt\n" +
                        "\n" +
                        "5. Gün: Tortilla Cipsi  - Otlu Dip Sos\n" +
                        "\n" +
                        "6. Gün: Kuru Meyveler\n" +
                        "\n" +
                        "Vejetaryen Örnek Menü\n\n" +
                        " ÖĞLE\n" +
                        "\n" +
                        "Örnek Öğle Menülerimiz;\n" +
                        "\n" +
                        "1.Gün: Girit Usulü Kabak, Kepekli Bulgur Pilavı, Coleslaw\n\n" +
                        "2.Gün: Köri Soslu Mantar, Susamlı Kuskus, Yoğurt\n\n" +
                        "2.Gün: Zerdeçallı Ispanak Sote, Labne Soslu Spagetti\n\n" +
                        "4.Gün: Sütlü Brokoli Çorba, Hardallı Sebze Şiş, Arpa Şehriye Salatası\n\n" +
                        "5.Gün: Sebzeli Kuru Börülce, Göbek Salata, Yoğurt\n\n" +
                        "6.Gün: Havuçlu Bezelye, Kekikli Bulgur Pilavı, Çeşnili Yoğurt\n" +
                        " \n" +
                        "\n" +
                        "Vejetaryen Örnek Menü\n\n" +
                        "2. Ara :\n\n" +
                        "Örnek 2. Ara Menülerimiz;\n" +
                        "\n" +
                        "1.Gün: İncir Dolması\n\n" +
                        "2.Gün: Kefir Shake\n\n" +
                        "3.Gün: Damla Sakızlı Muhallebi\n\n" +
                        "4.Gün: Biscotti- Yeşil Çay\n\n" +
                        "5.Gün: Kakaolu Raw Bar\n\n" +
                        "6.Gün: Fırın Mücver- Dip Sos\n\n\n\n" +
                        "Vejetaryen Örnek Menü\n\n" +
                        "AKŞAM\n" +
                        "\n" +
                        "Örnek Akşam Menülerimiz;\n" +
                        "\n" +
                        "1. Gün :Sebzeli Kuru Barbunya, Cevizli Erişte, Pul Biberli Yoğurt\n\n" +
                        "\n" +
                        "2. Gün:İmam Bayıldı, Kuru Naneli Cacık, Kepek Ekmek\n\n" +
                        "\n" +
                        "3. Gün: Sebzeli Nohut Yemeği, Kinoa Kısırı, Yoğurt\n\n" +
                        "\n" +
                        "4. Gün: Sütlü Mısır  Çorbası, Fırında Çıtır Karnabahar, Yeşil Salata- Nar Ekşisi\n\n" +
                        "\n" +
                        "5. Gün: Kabak Çintmesi, Mor Dünya Mezesi, Çavdar Ekmeği\n\n" +
                        "\n" +
                        "6.Gün: Fit Burger, Elma Dilim Patates, Ayran\n"



        };

        LinearLayout linearLayoutDietList = findViewById(R.id.linearLayoutDietList4);

        for (int i = 0; i < dietListTitles4.length; i++) {
            View dietListItemView = getLayoutInflater().inflate(R.layout.diet_list_item, null);

            TextView textViewTitle = dietListItemView.findViewById(R.id.textViewTitle);
            TextView textViewContent = dietListItemView.findViewById(R.id.textViewContent);

            textViewTitle.setText(dietListTitles4[i]);
            textViewContent.setText(dietListContents4[i]);

            linearLayoutDietList.addView(dietListItemView);
        }
    }
}