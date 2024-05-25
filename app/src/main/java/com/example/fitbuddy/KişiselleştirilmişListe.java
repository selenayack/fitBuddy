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
    private static final String PREFS_NAME = "DietPreferences5";
    private static final String DIET_LIST_KEY = "DietList5";

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
                "\n" +
                "Kahvaltı\n\n" +
                "-Seçenek 1: Yumurta Kahvaltısı\n" +
                "2 adet haşlanmış yumurta (veya omlet)\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı beyaz peynir\n" +
                "Domates, salatalık, yeşil biber\n" +
                "5-6 adet zeytin\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)\n" +
                "Şekersiz çay veya bitki çayı\n\n" +
                "\n" +
                "-Seçenek 2: Yulaf Kahvaltısı\n" +
                "\n" +
                "1/2 su bardağı yulaf ezmesi\n" +
                "1 su bardağı süt veya bitki sütü (badem, soya, yulaf sütü)\n" +
                "1 çay kaşığı bal veya pekmez\n" +
                "1/2 muz dilimleri\n" +
                "1 yemek kaşığı chia tohumu veya keten tohumu\n" +
                "Biraz tarçın" +
                "\n" +
                "ARA ÖĞÜN\n" +
                "\n" +
                "2 BÜYÜK BOY GALETA\n" +
                "2 PORSİYON MEYVE\n" +
                "1 SU BARDAĞI AYRAN\n" +
                "\n" +
                "Veya\n" +
                "\n" +
                "4 YK YULAF\n" +
                "1 SU BARDAĞI SÜT\n" +
                "1 PORSİYON MEYVE\n" +
                "1 TATLI KAŞIĞI BAL\n" +
                "1 TK FISTIK EZME \n" +
                "\n" +
                "ÖĞLE YEMEĞİ\n" +
                "\n" +
                "15 YK SEBZE YEMEĞİ\n" +
                "1 SU BARDAĞI AYRAN\n" +
                "2 İNCE DİLİM TAM BUĞDAY EKMEĞİ\n" +
                "(ÇIKAN YEMEĞE GÖRE PORSİYONLAMA YAPILACAKTIR)\n" +
                "\n" +
                "AKŞAM YEMEĞİ  \n" +
                "\n" +
                "1 KASE ÇORBA\n" +
                "15 KAŞIK SEBZE YEMEĞİ + 3 KÖFTE KADAR ET\n" +
                "SINIRSIZ SALATA (1 YEMEK KAŞIĞI ZEYTİNYAĞI \n" +
                "İLE)\n" +
                "2 YEMEK KAŞIĞI YOĞURT\n" +
                "2 İNCE DİLİM TAM BUĞDAY EKMEĞİ \n" +
                "\n" +
                "GECE \n" +
                "\n" +
                "2 TAM CEVİZ VEYA 5 ADET FINDIK VEYA 5 ADET BADEM \n" +
                "1 ÇAY BARDAĞI LEBLEBİ\n" +
                "1 SU BARDAĞI AYRAN\n" +
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList1.add("Diyet Listesi 1 \n"+"" +
                "\n" +
                " Kahvaltı Seçenekleri\n" +
                "Seçenek 1: Yumurta Kahvaltısı\n" +
                "\n" +
                "2 adet haşlanmış yumurta (veya omlet)\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı beyaz peynir\n" +
                "Domates, salatalık, yeşil biber\n" +
                "5-6 adet zeytin\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)\n" +
                "Şekersiz çay veya bitki çayı\n" +
                "\n" +
                "Seçenek 2: Yulaf Kahvaltısı\n" +
                "\n" +
                "1/2 su bardağı yulaf ezmesi\n" +
                "1 su bardağı süt veya bitki sütü (badem, soya, yulaf sütü)\n" +
                "1 çay kaşığı bal veya pekmez\n" +
                "1/2 muz dilimleri\n" +
                "1 yemek kaşığı chia tohumu veya keten tohumu\n" +
                "Biraz tarçın\n" +
                "\n" +
                "Ara Öğün Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 büyük boy galeta\n" +
                "1 porsiyon meyve (örneğin 1 küçük armut veya 1 avuç üzüm)\n" +
                "1 su bardağı ayran\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı peynir\n" +
                "1 adet salatalık veya havuç\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 porsiyon sebze yemeği (örneğin zeytinyağlı fasulye veya ıspanak yemeği)\n" +
                "1 su bardağı ayran\n" +
                "2 ince dilim tam buğday ekmeği\n" +
                "1 küçük porsiyon yağsız et (örneğin 3 köfte kadar tavuk veya hindi)\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon ızgara tavuk veya hindi\n" +
                "1 porsiyon kinoalı veya bulgurlu salata (içinde bol yeşillik, domates, salatalık, limon sosu)\n" +
                "1 su bardağı ayran\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 avuç badem veya ceviz\n" +
                "1 küçük meyve (örneğin 1 mandalina veya 1/2 elma)\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 kase yoğurt\n" +
                "1 porsiyon meyve (örneğin 1 küçük kivi veya 1/2 mango)\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 kase çorba (örneğin mercimek veya sebze çorbası)\n" +
                "1 porsiyon ızgara balık veya tavuk göğsü\n" +
                "Sınırsız salata (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 ince dilim tam buğday ekmeği\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon ızgara somon veya yağsız et (örneğin biftek)\n" +
                "Sınırsız buharda pişmiş brokoli, karnabahar, havuç (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 kase yoğurt\n" +
                "\n" +
                "Gece Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "2 tam ceviz veya 5 adet fındık veya 5 adet badem\n" +
                "1 su bardağı ayran\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 su bardağı süt veya bitki sütü\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)\n" +
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList1.add("Diyet Listesi 1 "+"" +
                " Kahvaltı Seçenekleri\n" +
                "\n" +
                "Seçenek 1: Fıstık Ezmeli Tost\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 yemek kaşığı doğal fıstık ezmesi\n" +
                "1 muz dilimleri\n" +
                "1 bardak bitki çayı veya şekersiz kahve\n" +
                "\n" +
                "Seçenek 2: Meyveli Yoğurt\n" +
                "\n" +
                "1 su bardağı yoğurt\n" +
                "1/2 su bardağı karışık meyve (örneğin çilek, yaban mersini)\n" +
                "1 yemek kaşığı yulaf ezmesi veya granola\n" +
                "\n" +
                "Ara Öğün Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 avuç badem veya ceviz\n" +
                "1 küçük meyve (örneğin 1 mandalina veya 1/2 elma)\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı peynir\n" +
                "1 adet salatalık veya havuç\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 porsiyon ızgara tavuk veya hindi\n" +
                "1 porsiyon kinoalı veya bulgurlu salata (içinde bol yeşillik, domates, salatalık, limon sosu)\n" +
                "1 su bardağı ayran\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon sebze yemeği (örneğin zeytinyağlı enginar veya karnabahar)\n" +
                "1 su bardağı ayran\n" +
                "2 ince dilim tam buğday ekmeği\n" +
                "1 küçük porsiyon yağsız et (örneğin 3 köfte kadar tavuk veya hindi)\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 kase yoğurt\n" +
                "1 porsiyon meyve (örneğin 1 küçük kivi veya 1/2 mango)\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı peynir\n" +
                "1 adet salatalık veya havuç\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "1 kase sebze çorbası\n" +
                "1 porsiyon ızgara somon veya yağsız et (örneğin biftek)\n" +
                "Sınırsız buharda pişmiş brokoli, karnabahar, havuç (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 kase yoğurt\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 porsiyon ızgara balık veya tavuk göğsü\n" +
                "Sınırsız salata (1 yemek kaşığı zeytinyağı ile)\n" +
                "1 ince dilim tam buğday ekmeği\n" +
                "\n" +
                "Gece Seçenekleri\n" +
                "\n" +
                "Seçenek 1:\n" +
                "\n" +
                "2 tam ceviz veya 5 adet fındık veya 5 adet badem\n" +
                "1 su bardağı ayran\n" +
                "\n" +
                "Seçenek 2:\n" +
                "\n" +
                "1 su bardağı süt veya bitki sütü\n" +
                "1 porsiyon meyve (örneğin 1 küçük elma veya 1/2 greyfurt)\n" +
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");

        // Diyet Listesi 2
        List<String> dietList2 = new ArrayList<>();
        dietList2.add("Diyet Listesi 2\n "+
                        "\n" +
                       "Kahvaltı Seçenekleri:\n" +
                        "\n" +
                        "-3 yumurta beyazı omlet (1 tam yumurta ile), 1 dilim tam buğday ekmeği, 1 avokado dilimi, domates, salatalık ve biber dilimleri, bir bardak şekersiz yeşil çay.\n" +
                        "-Peynirli tam buğday simit, zeytin ve salatalık dilimleri, 2 dilim hindi füme.\n" +
                        "\n" +
                        "Ara Öğün Seçenekleri:\n" +
                        "\n" +
                        "-Bir avuç fındık veya badem.\n" +
                        "-Bir adet armut veya şeftali.\n" +
                        "\n" +
                        "Öğle Yemeği Seçenekleri:\n" +
                        "\n" +
                        "-Izgara tavuk göğsü, 1 su bardağı esmer pirinç, sebzeli salata (marul, roka, salatalık, domates).\n" +
                        "-Izgara köfte (kıyma veya tavuk), bulgur pilavı, fırınlanmış sebzeler (patlıcan, kabak, biber).\n" +
                        "\n" +
                        "2.Ara Öğün Seçenekleri:\n" +
                        "\n" +
                        "-Bir bardak yoğurt, yarım muz dilimi.\n" +
                        "-Bir bardak süzme yoğurt, çilek dilimleri veya muz.\n" +
                        "\n" +
                        "Akşam Yemeği Seçenekleri:\n" +
                        "\n" +
                        "-Buharda pişmiş somon balığı, izgara sebzeler (brokoli, havuç, kabak), 1 dilim tam buğday ekmeği.\n" +
                        "-Izgara somon balığı, kepekli makarna, yeşil salata (marul, roka, salatalık, biber).\n" +
                        "\n" +
                        "Gece Seçenekleri:\n" +
                        "\n" +
                        "-1 kase light yoğurt, bir avuç fındık veya badem.\n" +
                        "-1 kase süzme yoğurt veya light süt, bir avuç badem veya ceviz.\n"+
                        "\n" +
                        "Öneriler\n" +
                        "\n" +
                        "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                        "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                        "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                        "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList2.add("Diyet Listesi 2 \n " +
                "\n" +
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-Yulaf ezmesi (yarım su bardağı), bir avuç çilek dilimi, 1 su bardağı süt veya badem sütü, bir avuç ceviz içi.\n" +
                "-Yulaf ezmesi, ceviz ve tarçın, bir avuç böğürtlen veya frambuaz, badem sütü veya süzme yoğurt.\n" +
                "-2 yumurta beyazı omlet (1 tam yumurta ile), 1 dilim tam buğday ekmeği, 1 avokado dilimi, domates, salatalık ve biber dilimleri, bir bardak şekersiz yeşil çay.\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-1 dilim tam buğday ekmek, lor peyniri ve salatalık dilimleri.\n" +
                "-Bir bardak yeşil smoothie (ıspanak, muz, elma, limon suyu).\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Izgara hindi göğsü, 1 küçük patates (fırında pişirilmiş), sebzeli salata (marul, domates, salatalık).\n" +
                "-Izgara biftek (yağsız), fırında patates kızartması (az yağlı), sebzeli bulgur pilavı.\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-1 dilim tam buğday ekmeği, 1 dilim az yağlı beyaz peynir.\n" +
                "-Bir bardak karışık meyve smoothiesi (muz, çilek, mango).\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Izgara biftek (dana veya hindi), fırında patates kızartması (az yağlı), sebzeli bulgur pilavı.\n" +
                "-Izgara köfte (kıyma veya tavuk), bulgur pilavı veya esmer pirinç, çoban salata (domates, salatalık, biber, beyaz peynir).\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-1 bardak süt veya badem sütü.\n" +
                "-1 bardak badem sütü veya sade yoğurt, bir avuç fındık veya ceviz.\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList2.add("Diyet Listesi 2 \n"+
                "\n" +
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-2 dilim tam buğday ekmeği, 2 dilim az yağlı beyaz peynir, domates, salatalık, yeşil biber dilimleri, 2 haşlanmış yumurta.\n" +
                "-Peynirli tam buğday simit, zeytin ve salatalık dilimleri, 2 dilim hindi füme.\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Bir avuç üzüm veya karpuz dilimleri.\n" +
                "-Bir adet elma veya armut, bir avuç badem veya ceviz.\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Izgara ton balığı, 1 küçük patates (ızgara veya fırında), sebzeli salata (marul, roka, domates, havuç).\n" +
                "-Izgara köfte (kıyma veya tavuk), bulgur pilavı veya esmer pirinç, çoban salata (domates, salatalık, biber, beyaz peynir).\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Bir dilim tam buğday ekmek, fındık ezmesi ve muz dilimleri.\n" +
                "-1 bardak yoğurt, yarım muz dilimi.\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Izgara levrek veya çipura, bulgur pilavı ve mercimek çorbası, izgara sebzeler (patlıcan, kabak, biber).\n" +
                "-Izgara köfte (kıyma veya tavuk), bulgur pilavı veya esmer pirinç, çoban salata (domates, salatalık, biber, beyaz peynir).\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-1 kase süzme yoğurt veya light süt, bir avuç badem veya ceviz.\n" +
                "-1 kase süzme yoğurt veya light süt, bir avuç fındık veya badem.\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");

        // Diyet Listesi 3
        List<String> dietList3 = new ArrayList<>();
        dietList3.add("Diyet Listesi 3\n"+
                "\n" +
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-Tam Buğday Ekmeği ve Proteinli Omlet:\n" +
                "\n" +
                "2 dilim tam buğday ekmeği\n" +
                "3 yumurta beyazı omlet (1 tam yumurta ile)\n" +
                "1 avokado dilimi\n" +
                "Domates, salatalık ve biber dilimleri\n" +
                "Bir bardak şekersiz yeşil çay\n" +
                "\n" +
                "-Meyve ve Peynirli Tam Buğday Simit:\n" +
                "\n" +
                "Peynirli tam buğday simit\n" +
                "Zeytin ve salatalık dilimleri\n" +
                "2 dilim hindi füme\n" +
                "\n" +
                "-Yoğurt ve Yulaf:\n" +
                "\n" +
                "1 su bardağı yoğurt\n" +
                "Yulaf ezmesi (yarım su bardağı)\n" +
                "Bir avuç çilek dilimi\n" +
                "Bir avuç ceviz içi\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Kuru Yemişler:\n" +
                "\n" +
                "Bir avuç fındık veya badem\n" +
                "\n" +
                "-Meyve ve Fıstık Ezmesi:\n" +
                "\n" +
                "1 adet armut veya şeftali\n" +
                "Bir avuç badem veya ceviz\n" +
                "\n" +
                "-Tahıllar ve Proteini:\n" +
                "\n" +
                "Bir dilim tam buğday ekmek\n" +
                "1 dilim az yağlı beyaz peynir\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Tavuk ve Pirinç:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "1 su bardağı esmer pirinç\n" +
                "Sebzeli salata (marul, roka, salatalık, domates)\n" +
                "\n" +
                "-Köfte ve Sebzeler:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Bulgur pilavı\n" +
                "Fırınlanmış sebzeler (patlıcan, kabak, biber)\n" +
                "\n" +
                "-Balık ve Ekmek:\n" +
                "\n" +
                "Buharda pişmiş somon balığı\n" +
                "Izgara sebzeler (brokoli, havuç, kabak)\n" +
                "1 dilim tam buğday ekmeği\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Yoğurt ve Muz:\n" +
                "\n" +
                "1 bardak yoğurt\n" +
                "Yarım muz dilimi\n" +
                "\n" +
                "-Sütlü Smoothie:\n" +
                "\n" +
                "1 bardak süt veya badem sütü\n" +
                "Bir avuç çilek dilimleri veya muz\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Balık ve Sebzeler:\n" +
                "\n" +
                "Buharda pişmiş somon balığı\n" +
                "Izgara sebzeler (brokoli, havuç, kabak)\n" +
                "1 dilim tam buğday ekmeği\n" +
                "\n" +
                "-Kırmızı Et ve Pilav:\n" +
                "\n" +
                "Izgara biftek (yağsız)\n" +
                "Fırında patates kızartması (az yağlı)\n" +
                "Sebzeli bulgur pilavı\n" +
                "\n" +
                "-Tavuk ve Esmer Pirinç:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "1 su bardağı esmer pirinç\n" +
                "Buharda pişmiş sebzeler (brokoli, karnabahar, havuç)\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-Yoğurt ve Kuruyemiş:\n" +
                "\n" +
                "1 kase light yoğurt\n" +
                "Bir avuç fındık veya badem\n" +
                "\n" +
                "-Süt ve Yulaf:\n" +
                "\n" +
                "1 bardak süt veya badem sütü\n" +
                "2 yemek kaşığı yulaf ezmesi\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList3.add("Diyet Listesi 3\n"+
                "\n" +
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-Yulaf Ezmesi ve Meyve:\n" +
                "\n" +
                "Yulaf ezmesi (yarım su bardağı)\n" +
                "Bir avuç çilek dilimi\n" +
                "1 su bardağı süt veya badem sütü\n" +
                "Bir avuç ceviz içi\n" +
                "\n" +
                "-Yulaf Ezmesi ve Kuruyemiş:\n" +
                "\n" +
                "Yulaf ezmesi, ceviz ve tarçın\n" +
                "Bir avuç böğürtlen veya frambuaz\n" +
                "Badem sütü veya süzme yoğurt\n" +
                "\n" +
                "-Meyve ve Peynirli Simit:\n" +
                "\n" +
                "Peynirli tam buğday simit\n" +
                "Zeytin ve salatalık dilimleri\n" +
                "2 dilim hindi füme\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Tahıllar ve Protein:\n" +
                "\n" +
                "Bir dilim tam buğday ekmek\n" +
                "1 dilim az yağlı beyaz peynir\n" +
                "\n" +
                "-Smoothie:\n" +
                "\n" +
                "Bir bardak yeşil smoothie (ıspanak, muz, elma, limon suyu)\n" +
                "\n" +
                "-Meyve ve Protein:\n" +
                "\n" +
                "1 dilim tam buğday ekmeği\n" +
                "1 dilim az yağlı beyaz peynir\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Hindi ve Patates:\n" +
                "\n" +
                "Izgara hindi göğsü\n" +
                "1 küçük patates (fırında pişirilmiş)\n" +
                "Sebzeli salata (marul, domates, salatalık)\n" +
                "\n" +
                "-Biftek ve Bulgur Pilavı:\n" +
                "\n" +
                "Izgara biftek (yağsız)\n" +
                "Bulgur pilavı\n" +
                "Sebzeli salata (marul, roka, domates, havuç)\n" +
                "\n" +
                "-Tavuk ve Kinoa:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Kinoa pilavı\n" +
                "Haşlanmış brokoli ve havuç\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Tahıllar ve Süt Ürünü:\n" +
                "\n" +
                "Bir dilim tam buğday ekmek\n" +
                "Lor peyniri ve salatalık dilimleri\n" +
                "\n" +
                "-Meyve ve Yoğurt:\n" +
                "\n" +
                "Bir bardak karışık meyve smoothiesi (muz, çilek, mango)\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Biftek ve Patates:\n" +
                "\n" +
                "Izgara biftek (dana veya hindi)\n" +
                "Fırında patates kızartması (az yağlı)\n" +
                "Sebzeli bulgur pilavı\n" +
                "\n" +
                "-Köfte ve Salata:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Bulgur pilavı veya esmer pirinç\n" +
                "Çoban salata (domates, salatalık, biber, beyaz peynir)\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-Süt ve Kuruyemiş:\n" +
                "\n" +
                "1 bardak süt veya badem sütü\n" +
                "Bir avuç fındık veya ceviz\n" +
                "\n" +
                "-Yoğurt ve Meyve:\n" +
                "\n" +
                "1 kase süzme yoğurt veya light süt\n" +
                "Bir avuç çilek dilimleri veya muz\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList3.add("Diyet Listesi 3\n"+
                "\n" +
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-Tam Buğday Ekmeği ve Protein:\n" +
                "\n" +
                "2 dilim tam buğday ekmeği\n" +
                "2 dilim az yağlı beyaz peynir\n" +
                "Domates, salatalık, yeşil biber dilimleri\n" +
                "2 haşlanmış yumurta\n" +
                "\n" +
                "-Peynirli Simit ve Füme Hindi:\n" +
                "\n" +
                "Peynirli tam buğday simit\n" +
                "Zeytin ve salatalık dilimleri\n" +
                "2 dilim hindi füme\n" +
                "\n" +
                "-Yumurta ve Sebzeler:\n" +
                "\n" +
                "Omlet: 2 yumurta, ıspanak ve mantar\n" +
                "Tam buğday ekmeği dilimi\n" +
                "Bir avuç kiraz domates\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Meyve ve Kuruyemiş:\n" +
                "\n" +
                "Bir avuç üzüm veya karpuz dilimleri\n" +
                "Bir avuç badem veya ceviz\n" +
                "\n" +
                "-Meyve ve Tahıllar:\n" +
                "\n" +
                "Bir adet elma veya armut\n" +
                "Bir avuç badem veya ceviz\n" +
                "\n" +
                "-Smoothie ve Protein:\n" +
                "\n" +
                "Bir dilim tam buğday ekmek\n" +
                "Fındık ezmesi ve muz dilimleri\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Balık ve Patates:\n" +
                "\n" +
                "Izgara levrek veya çipura\n" +
                "Bulgur pilavı ve mercimek çorbası\n" +
                "Izgara sebzeler (patlıcan, kabak, biber)\n" +
                "\n" +
                "-Köfte ve Pilav:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Bulgur pilavı veya esmer pirinç\n" +
                "Çoban salata (domates, salatalık, biber, beyaz peynir)\n" +
                "\n" +
                "-Tavuk ve Bulgur:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Bulgur pilavı veya esmer pirinç\n" +
                "Sebzeli salata (marul, roka, domates, havuç)\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Tahıllar ve Süt Ürünü:\n" +
                "\n" +
                "Bir dilim tam buğday ekmek\n" +
                "Fındık ezmesi ve muz dilimleri\n" +
                "\n" +
                "-Yoğurt ve Meyve:\n" +
                "\n" +
                "1 bardak yoğurt\n" +
                "Yarım muz dilimi\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Tavuk ve Pilav:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Bulgur pilavı veya esmer pirinç\n" +
                "Buharda pişmiş sebzeler (brokoli, karnabahar, havuç)\n" +
                "\n" +
                "-Köfte ve Bulgur:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Bulgur pilavı veya esmer pirinç\n" +
                "Sebzeli salata (marul, roka, domates, havuç)\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-Yoğurt ve Kuruyemiş:\n" +
                "\n" +
                "1 kase süzme yoğurt veya light süt\n" +
                "Bir avuç fındık veya badem\n" +
                "-Süt ve Meyve:\n" +
                "\n" +
                "1 kase süzme yoğurt veya light süt\n" +
                "Bir avuç üzüm veya karpuz dilimleri\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");

        // Diyet Listesi 4
        List<String> dietList4 = new ArrayList<>();
        dietList4.add("Diyet Listesi 4 \n"+
                "\n" +
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-Yüksek Protein Kahvaltı:\n" +
                "\n" +
                "3 yumurta beyazı omlet (1 tam yumurta ile)\n" +
                "2 dilim tam buğday ekmeği\n" +
                "Avokado dilimi, domates, salatalık ve biber dilimleri\n" +
                "Bir bardak süt veya protein shake\n" +
                "\n" +
                "-Yoğurtlu Müsli:\n" +
                "\n" +
                "1 su bardağı yulaf ezmesi\n" +
                "1 su bardağı yoğurt\n" +
                "Yarım su bardağı kuru yemiş (fındık, ceviz, badem)\n" +
                "Bir avuç çilek dilimi veya muz dilimleri\n" +
                "\n" +
                "-Tost ve Meyve:\n" +
                "\n" +
                "2 dilim tam buğday ekmeği\n" +
                "2 dilim az yağlı beyaz peynir\n" +
                "Bir avuç kiraz domates\n" +
                "Bir adet elma veya armut\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Badem ve Kuruyemiş:\n" +
                "\n" +
                "Bir avuç badem veya ceviz\n" +
                "Bir avuç kuru üzüm veya kayısı\n" +
                "\n" +
                "-Meyve ve Yulaf:\n" +
                "\n" +
                "Bir adet muz\n" +
                "Yarım su bardağı yulaf ezmesi\n" +
                "Bir su bardağı süt veya badem sütü\n" +
                "-Protein Shake:\n" +
                "\n" +
                "Bir ölçek whey protein\n" +
                "Bir muz\n" +
                "Bir yemek kaşığı fıstık ezmesi\n" +
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Tavuklu Pilav:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "1 su bardağı esmer pirinç pilavı\n" +
                "Sebzeli salata (marul, roka, domates, salatalık)\n" +
                "\n" +
                "-Balıklı Makarna:\n" +
                "\n" +
                "Izgara somon balığı\n" +
                "Kepekli makarna\n" +
                "Yeşil salata (marul, roka, salatalık, biber)\n" +
                "\n" +
                "-Köfte ve Bulgur:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Bulgur pilavı\n" +
                "Sebzeli bulgur pilavı\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Yoğurt ve Meyve:\n" +
                "\n" +
                "Bir bardak yoğurt\n" +
                "Yarım muz dilimi\n" +
                "Bir avuç çilek dilimi\n" +
                "\n" +
                "-Sandviç:\n" +
                "\n" +
                "Tam buğday ekmeği\n" +
                "Hindi füme veya az yağlı beyaz peynir\n" +
                "Domates ve marul\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Tavuklu Nohut Pilavı:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Nohutlu pilav\n" +
                "Fırınlanmış sebzeler (patlıcan, kabak, biber)\n" +
                "\n" +
                "-Etli Sebzeli Güveç:\n" +
                "\n" +
                "Dana eti veya kuzu eti\n" +
                "Sebzeli güveç\n" +
                "Tam buğday ekmeği dilimi\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-Yoğurt ve Kuruyemiş:\n" +
                "\n" +
                "Bir kase süzme yoğurt\n" +
                "Bir avuç fındık veya badem\n" +
                "\n" +
                "-Meyve ve Süt:\n" +
                "\n" +
                "Bir adet elma veya armut\n" +
                "Bir bardak süt veya badem sütü\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");
        dietList4.add("Diyet Listesi 4 \n" +
                "\n" +
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-Yulaf Ezmesi ve Protein:\n" +
                "\n" +
                "1 su bardağı yulaf ezmesi\n" +
                "Bir ölçek whey protein\n" +
                "Bir avuç çilek dilimi veya muz dilimleri\n" +
                "\n" +
                "-Süt ve Yumurta:\n" +
                "\n" +
                "3 yumurta beyazı omlet (1 tam yumurta ile)\n" +
                "Bir bardak süt veya badem sütü\n" +
                "Bir dilim tam buğday ekmeği\n" +
                "\n" +
                "-Tost ve Meyve:\n" +
                "\n" +
                "2 dilim tam buğday ekmeği\n" +
                "2 dilim az yağlı beyaz peynir\n" +
                "Bir avuç kiraz domates\n" +
                "Bir adet elma veya armut\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Badem ve Kuruyemiş:\n" +
                "\n" +
                "Bir avuç badem veya ceviz\n" +
                "Bir avuç kuru üzüm veya kayısı\n" +
                "-Meyve ve Yoğurt:\n" +
                "\n" +
                "Bir avuç kuru incir veya kuru kayısı\n" +
                "Bir kase yoğurt\n" +
                "Yarım muz dilimi\n" +
                "\n" +
                "-Protein Bar:\n" +
                "\n" +
                "Bir adet protein bar\n" +
                "Bir elma veya armut\n"+
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Izgara Tavuk ve Sebzeler:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Izgara sebzeler (brokoli, havuç, kabak)\n" +
                "Bir su bardağı bulgur pilavı veya esmer pirinç\n" +
                "\n" +
                "-Balık ve Salata:\n" +
                "\n" +
                "Buharda pişmiş somon balığı\n" +
                "Yeşil salata (marul, roka, salatalık, domates)\n" +
                "Bir dilim tam buğday ekmeği veya yulaf ekmeği\n" +
                "\n" +
                "-Kıyma ve Makarna:\n" +
                "\n" +
                "Kıymalı makarna\n" +
                "Haşlanmış brokoli veya bezelye\n" +
                "Bir avuç badem veya ceviz\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Meyve ve Yoğurt:\n" +
                "\n" +
                "Bir adet muz\n" +
                "Bir kase yoğurt\n" +
                "Bir avuç fındık veya badem\n" +
                "\n" +
                "-Süzme Peynir ve Kraker:\n" +
                "\n" +
                "Bir dilim tam buğday kraker\n" +
                "Bir dilim az yağlı beyaz peynir\n" +
                "Bir avuç üzüm veya karpuz dilimleri\n" +
                "\n" +
                "-Protein Shake:\n" +
                "\n" +
                "Bir ölçek whey protein shake\n" +
                "Bir avuç çilek veya muz dilimleri\n" +
                "Bir yarım bardak yulaf ezmesi\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Izgara Biftek ve Patates:\n" +
                "\n" +
                "Izgara biftek (dana veya hindi)\n" +
                "Fırında patates kızartması (az yağlı)\n" +
                "Bir avuç yeşil fasulye veya havuç\n" +
                "\n" +
                "-Tavuk ve Pilav:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Bir su bardağı esmer pirinç pilavı\n" +
                "Buharda pişmiş sebzeler (brokoli, karnabahar, havuç)\n" +
                "\n" +
                "-Köfte ve Bulgur Pilavı:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Sebzeli bulgur pilavı\n" +
                "Bir avuç kiraz domates veya salatalık dilimleri\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-Süt ve Kuruyemiş:\n" +
                "\n" +
                "Bir bardak süt veya badem sütü\n" +
                "Bir avuç fındık veya ceviz\n" +
                "Bir avuç kuru üzüm veya kayısı\n" +
                "\n" +
                "-Meyve ve Yoğurt:\n" +
                "\n" +
                "Bir adet armut veya şeftali\n" +
                "Bir kase yoğurt\n" +
                "Bir dilim tam buğday ekmek veya kraker\n" +
                "\n" +
                "-Puding ve Kuruyemiş:\n" +
                "\n" +
                "Bir porsiyon vanilyalı veya çikolatalı puding\n" +
                "Bir avuç badem veya fındık\n" +
                "Bir avuç kuru üzüm veya kayısı\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");

        dietList4.add("Diyet Listesi 4 \n"+
                "Kahvaltı Seçenekleri:\n" +
                "\n" +
                "-Yumurta ve Ekmek:\n" +
                "\n" +
                "3 yumurta beyazı omlet (1 tam yumurta ile)\n" +
                "2 dilim tam buğday ekmeği\n" +
                "Bir avuç ıspanak veya mantar\n" +
                "Bir dilim avokado\n" +
                "\n" +
                "-Protein Shake ve Meyve:\n" +
                "\n" +
                "Bir ölçek whey protein shake\n" +
                "Bir avuç çilek veya muz\n" +
                "Bir yarım bardak yulaf ezmesi\n" +
                "\n" +
                "-Ton Balığı ve Peynir:\n" +
                "\n" +
                "1 kutu ton balığı\n" +
                "2 dilim az yağlı beyaz peynir\n" +
                "2 dilim tam buğday ekmeği\n" +
                "\n" +
                "Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Fıstık Ezmesi ve Muz:\n" +
                "\n" +
                "2 yemek kaşığı fıstık ezmesi\n" +
                "Bir adet muz\n" +
                "Badem ve Kuru Üzüm:\n" +
                "\n" +
                "Bir avuç badem\n" +
                "Bir avuç kuru üzüm\n" +
                "\n" +
                "-Yoğurt ve Kuru Meyve:\n" +
                "\n" +
                "Bir kase yoğurt\n" +
                "Bir avuç kuru kayısı veya kuru incir\n"+
                "\n" +
                "Öğle Yemeği Seçenekleri:\n" +
                "\n" +
                "-Tavuk ve Bulgur Pilavı:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Bir su bardağı bulgur pilavı\n" +
                "Bir avuç sebzeli salata (marul, roka, domates)\n" +
                "\n" +
                "-Köfte ve Sebzeler:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Fırında pişirilmiş sebzeler (patlıcan, kabak, biber)\n" +
                "Bir dilim tam buğday ekmeği veya yulaf ekmeği\n" +
                "\n" +
                "-Balık ve Patates:\n" +
                "\n" +
                "Izgara somon balığı\n" +
                "Fırında patates kızartması (az yağlı)\n" +
                "Yeşil salata (marul, salatalık, domates)\n" +
                "\n" +
                "2.Ara Öğün Seçenekleri:\n" +
                "\n" +
                "-Kuru Meyve ve Kuruyemiş:\n" +
                "\n" +
                "Bir avuç kuru incir veya kuru kayısı\n" +
                "Bir avuç fındık veya badem\n" +
                "\n" +
                "-Meyve ve Yoğurt:\n" +
                "\n" +
                "Bir adet elma veya armut\n" +
                "Bir kase yoğurt\n" +
                "\n" +
                "-Protein Bar ve Meyve:\n" +
                "\n" +
                "Bir adet protein bar\n" +
                "Bir adet portakal veya mandalina\n" +
                "\n" +
                "Akşam Yemeği Seçenekleri:\n" +
                "\n" +
                "-Izgara Tavuk ve Sebzeler:\n" +
                "\n" +
                "Izgara tavuk göğsü\n" +
                "Izgara sebzeler (brokoli, havuç, kabak)\n" +
                "Bir su bardağı esmer pirinç pilavı\n" +
                "\n" +
                "-Kıymalı Makarna:\n" +
                "\n" +
                "Kıymalı makarna\n" +
                "Bir avuç yeşil fasulye veya bezelye\n" +
                "\n" +
                "-Köfte ve Bulgur Pilavı:\n" +
                "\n" +
                "Izgara köfte (kıyma veya tavuk)\n" +
                "Bulgur pilavı\n" +
                "Cacık veya yoğurtlu salata\n" +
                "\n" +
                "Gece Seçenekleri:\n" +
                "\n" +
                "-Süt ve Kuruyemiş:\n" +
                "\n" +
                "Bir bardak süt veya badem sütü\n" +
                "Bir avuç fındık veya ceviz\n" +
                "\n" +
                "-Meyve ve Yoğurt:\n" +
                "\n" +
                "Bir avuç çilek veya muz\n" +
                "Bir kase yoğurt\n" +
                "\n" +
                "-Protein Shake ve Badem:\n" +
                "\n" +
                "Bir ölçek whey protein shake\n" +
                "Bir avuç badem\n"+
                "\n" +
                "Öneriler\n" +
                "\n" +
                "Günlük en az 2-2,5 litre su tüketmeye özen gösterin.\n" +
                "Düzenli fiziksel aktivite yapın (örneğin yürüyüş, yoga, ev egzersizleri).\n" +
                "Yemeklerde tuz tüketimini azaltın, diğer baharatları kullanarak lezzet katın.\n" +
                "Lifli gıdalar tüketmeye özen gösterin (sebzeler, tam tahıllı ürünler).");

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
