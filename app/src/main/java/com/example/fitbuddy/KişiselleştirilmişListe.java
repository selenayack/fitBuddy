package com.example.fitbuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitbuddy.DbAdapter;
import com.example.fitbuddy.ModelInterpreter;
import com.example.fitbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KişiselleştirilmişListe extends AppCompatActivity {


    private DbAdapter dbAdapter;
    private ModelInterpreter modelInterpreter;
    private TextView resultTextView;
    private static List<List<String>> dietLists;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "DietPreferences2";
    private static final String DIET_LIST_KEY = "DietList2";

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisisellestirilmis_list);
        if (dietLists == null) {
            initializeDietLists();
        }

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        resultTextView = findViewById(R.id.result_text_view);

        dbAdapter = new DbAdapter(this) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                // DB creation logic if needed
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                // DB upgrade logic if needed
            }
        };
        dbAdapter.open();

        try {
            modelInterpreter = new ModelInterpreter(this);
        } catch (IOException e) {
            e.printStackTrace();
            resultTextView.setText("Model yüklenirken bir hata oluştu.");
            return;
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            String firebaseUserId = firebaseUser.getUid();
            int userId = (int) dbAdapter.getUserIdFromFirebaseId(firebaseUserId);
            float[] userData = dbAdapter.getUserData(userId);
            if (userData != null && userData.length == 5) {
                int prediction = modelInterpreter.predict(userData);
                displayDietList(prediction);
            } else {
                resultTextView.setText("Kullanıcı verileri alınamadı.");
            }
        } else {
            resultTextView.setText("Kullanıcı oturumu açık değil.");

        }
    }

    private void initializeDietLists() {
        dietLists = new ArrayList<>();

        // Diyet Listesi 1
        List<String> dietList1 = new ArrayList<>();
        dietList1.add("Diyet Listesi 1\n "+
                "Kahvaltı\n\n" +
                "Seçenek 1: Yumurta Kahvaltısı\n" +
                "2 adet haşlanmış yumurta (veya omlet)\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı beyaz peynir\n" +
                "Domates, salatalık, yeşil biber\n" +
                "5-6 adet zeytin\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)\n" +
                "Şekersiz çay veya bitki çayı\n\n" +
                "Seçenek 2: Yulaf Kahvaltısı\n" +
                "\n" +
                "1/2 su bardağı yulaf ezmesi\n" +
                "1 su bardağı süt veya bitki sütü (badem, soya, yulaf sütü)\n" +
                "1 çay kaşığı bal veya pekmez\n" +
                "1/2 muz dilimleri\n" +
                "1 yemek kaşığı chia tohumu veya keten tohumu\n" +
                "Biraz tarçın" +
                "ARA ÖĞÜN\n" +
                "\n" +
                "2 BÜYÜK BOY GALETA\n" +
                "2 DEĞİŞİM MEYVE\n" +
                "1 SU BARDAĞI AYRAN\n" +
                "Veya\n" +
                "4 YK YULAF\n" +
                "1 SU BARDAĞI SÜT\n" +
                "1 DEĞİŞİM MEYVE\n" +
                "1 TATLI KAŞIĞI BAL\n" +
                "1 TK FISTIK EZME \n" +
                "ÖĞLE YEMEĞİ\n" +
                "\n" +
                "15 YK SEBZE YEMEĞİ\n" +
                "1 SU BARDAĞI AYRAN\n" +
                "2 İNCE DİLİM TAM BUĞDAY EKMEĞİ\n" +
                "(ÇIKAN YEMEĞE GÖRE PORSİYONLAMA YAPILACAKTIR)\n" +
                "AKŞAM YEMEĞİ  \n" +
                "1 KASE ÇORBA\n" +
                "15 KAŞIK SEBZE YEMEĞİ + 3 KÖFTE KADAR ET\n" +
                "SINIRSIZ SALATA (1 YEMEK KAŞIĞI ZEYTİNYAĞI \n" +
                "İLE)\n" +
                "2 YEMEK KAŞIĞI YOĞURT\n" +
                "2 İNCE DİLİM TAM BUĞDAY EKMEĞİ \n" +
                "GECE \n" +
                "2 TAM CEVİZ VEYA 5 ADET FINDIK VEYA 5 ADET BADEM \n" +
                "1 ÇAY BARDAĞI LEBLEBİ\n" +
                "1 SU BARDAĞI AYRAN\n" +
                "ÖNERİLER\n" +
                "∙\tGÜNLÜK EN AZ 2-2,5 LT SU TÜKETMEYİ İHMAL ETMEYİNİZ.\n" +
                "∙\tHER GÜN LESLIE 15 DKLIK SPOR PROGRAMINI TAMAMLAYINIZ.\n" +
                "∙\tSALATALARA MUTLAKA LİMON EKLEYİNİZ.\n" +
                "∙\tTUZ HARİÇ DİĞER BAHARATLARI İSTEDİĞİNİZ KADAR TÜKETEBİLİRSİNİZ.\n" +
                "∙\tİLK ARA ÖĞÜNDE YULAFLI TARİF YAPILIRSA GECE ARA ÖĞÜNÜNDEKİ ÇİĞ BADEM TÜKETİLMEMELİDİR.\n");
        dietList1.add("Diyet Listesi 1 - Kahvaltı Seçenekleri\n" +
                "Seçenek 1: Yumurta Kahvaltısı\n" +
                "\n" +
                "2 adet haşlanmış yumurta (veya omlet)\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı beyaz peynir\n" +
                "Domates, salatalık, yeşil biber\n" +
                "5-6 adet zeytin\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)\n" +
                "Şekersiz çay veya bitki çayı\n" +
                "Seçenek 2: Yulaf Kahvaltısı\n" +
                "\n" +
                "1/2 su bardağı yulaf ezmesi\n" +
                "1 su bardağı süt veya bitki sütü (badem, soya, yulaf sütü)\n" +
                "1 çay kaşığı bal veya pekmez\n" +
                "1/2 muz dilimleri\n" +
                "1 yemek kaşığı chia tohumu veya keten tohumu\n" +
                "Biraz tarçın\n" +
                "Ara Öğün Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 büyük boy galeta\n" +
                "1 porsiyon meyve (örneğin 1 küçük armut veya 1 avuç üzüm)\n" +
                "1 su bardağı ayran\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı peynir\n" +
                "1 adet salatalık veya havuç\n" +
                "Öğle Yemeği Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 porsiyon sebze yemeği (örneğin zeytinyağlı fasulye veya ıspanak yemeği)\n" +
                "1 su bardağı ayran\n" +
                "2 ince dilim tam buğday ekmeği\n" +
                "1 küçük porsiyon yağsız et (örneğin 3 köfte kadar tavuk veya hindi)\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon ızgara tavuk veya hindi\n" +
                "1 porsiyon kinoalı veya bulgurlu salata (içinde bol yeşillik, domates, salatalık, limon sosu)\n" +
                "1 su bardağı ayran\n" +
                "Ara Öğün Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 avuç badem veya ceviz\n" +
                "1 küçük meyve (örneğin 1 mandalina veya 1/2 elma)\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 kase yoğurt\n" +
                "1 porsiyon meyve (örneğin 1 küçük kivi veya 1/2 mango)\n" +
                "Akşam Yemeği Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 kase çorba (örneğin mercimek veya sebze çorbası)\n" +
                "1 porsiyon ızgara balık veya tavuk göğsü\n" +
                "Sınırsız salata (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 ince dilim tam buğday ekmeği\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon ızgara somon veya yağsız et (örneğin biftek)\n" +
                "Sınırsız buharda pişmiş brokoli, karnabahar, havuç (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 kase yoğurt\n" +
                "Gece Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "2 tam ceviz veya 5 adet fındık veya 5 adet badem\n" +
                "1 su bardağı ayran\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 su bardağı süt veya bitki sütü\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)+" +
                "Öneriler\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList1.add("Diyet Listesi 1 - Kahvaltı Seçenekleri\n" +
                "Seçenek 1: Fıstık Ezmeli Tost\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 yemek kaşığı doğal fıstık ezmesi\n" +
                "1 muz dilimleri\n" +
                "1 bardak bitki çayı veya şekersiz kahve\n" +
                "Seçenek 2: Meyveli Yoğurt\n" +
                "\n" +
                "1 su bardağı yoğurt\n" +
                "1/2 su bardağı karışık meyve (örneğin çilek, yaban mersini)\n" +
                "1 yemek kaşığı yulaf ezmesi veya granola\n" +
                "Ara Öğün Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 avuç badem veya ceviz\n" +
                "1 küçük meyve (örneğin 1 mandalina veya 1/2 elma)\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı peynir\n" +
                "1 adet salatalık veya havuç\n" +
                "Öğle Yemeği Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 porsiyon ızgara tavuk veya hindi\n" +
                "1 porsiyon kinoalı veya bulgurlu salata (içinde bol yeşillik, domates, salatalık, limon sosu)\n" +
                "1 su bardağı ayran\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon sebze yemeği (örneğin zeytinyağlı enginar veya karnabahar)\n" +
                "1 su bardağı ayran\n" +
                "2 ince dilim tam buğday ekmeği\n" +
                "1 küçük porsiyon yağsız et (örneğin 3 köfte kadar tavuk veya hindi)\n" +
                "Ara Öğün Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 kase yoğurt\n" +
                "1 porsiyon meyve (örneğin 1 küçük kivi veya 1/2 mango)\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı peynir\n" +
                "1 adet salatalık veya havuç\n" +
                "Akşam Yemeği Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 kase sebze çorbası\n" +
                "1 porsiyon ızgara somon veya yağsız et (örneğin biftek)\n" +
                "Sınırsız buharda pişmiş brokoli, karnabahar, havuç (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 kase yoğurt\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon ızgara balık veya tavuk göğsü\n" +
                "Sınırsız salata (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 ince dilim tam buğday ekmeği\n" +
                "Gece Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "2 tam ceviz veya 5 adet fındık veya 5 adet badem\n" +
                "1 su bardağı ayran\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 su bardağı süt veya bitki sütü\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)+" +
                "Öneriler\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");

        // Diyet Listesi 2
        List<String> dietList2 = new ArrayList<>();
        dietList2.add("Diyet Listesi 2 - Menü 1: Kahvaltı - Meyveli yoğurt, Öğle - Mercimek çorbası, Akşam - Izgara tavuk");
        dietList2.add("Diyet Listesi 2 - Menü 2: Kahvaltı - Tam tahıllı ekmek ve peynir, Öğle - Tavuklu wrap, Akşam - Somon");
        dietList2.add("Diyet Listesi 2 - Menü 3: Kahvaltı - Fıstık ezmeli tost, Öğle - Nohutlu salata, Akşam - Sebzeli makarna");

        // Diyet Listesi 3
        List<String> dietList3 = new ArrayList<>();
        dietList3.add("Diyet Listesi 3 - Menü 1: Kahvaltı - Chia tohumlu puding, Öğle - Tavuklu sandviç, Akşam - Izgara köfte");
        dietList3.add("Diyet Listesi 3 - Menü 2: Kahvaltı - Omlet, Öğle - Kinoa salatası, Akşam - Izgara balık");
        dietList3.add("Diyet Listesi 3 - Menü 3: Kahvaltı - Muzlu smoothie, Öğle - Falafel, Akşam - Izgara tavuk");

        // Diyet Listesi 4
        List<String> dietList4 = new ArrayList<>();
        dietList4.add("Diyet Listesi 4 - Menü 1: Kahvaltı - Yulaf ezmesi ve çilek, Öğle - Ton balıklı salata, Akşam - Sebzeli tavuk");
        dietList4.add("Diyet Listesi 4 - Menü 2: Kahvaltı - Yoğurt ve ceviz, Öğle - Tavuklu wrap, Akşam - Sebzeli köfte");
        dietList4.add("Diyet Listesi 4 - Menü 3: Kahvaltı - Meyve tabağı, Öğle - Tavuklu sandviç, Akşam - Izgara somon");

        // Diyet listelerini ana listeye ekle
        dietLists.add(dietList1);
        dietLists.add(dietList2);
        dietLists.add(dietList3);
        dietLists.add(dietList4);
    }




    private void displayDietList(int prediction) {
        if (prediction >= 1 && prediction <= 4) {
            String storedDiet = sharedPreferences.getString(DIET_LIST_KEY + prediction, null);
            if (storedDiet == null) {
                List<String> selectedDietList = dietLists.get(prediction - 1);
                Random random = new Random();
                String randomMenu = selectedDietList.get(random.nextInt(selectedDietList.size()));
                sharedPreferences.edit().putString(DIET_LIST_KEY + prediction, randomMenu).apply();
                resultTextView.setText(randomMenu);
            } else {
                resultTextView.setText(storedDiet);
            }
        } else {
            resultTextView.setText("Geçersiz çıktı");
        }
    }








}
