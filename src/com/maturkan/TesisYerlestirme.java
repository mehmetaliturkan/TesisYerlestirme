package com.maturkan;

/**
 * @author Mehmetali TURKAN
 *
 */
class TesisYerlestirme {

    //secilim caprazlama mutasyon evrenelerının kac nesıl devam edecegını soyler
    final static int NESILSAYISI =200;

    //Bu sabit tesis sayisini belirtir
    final static int POPULASYONGENISLIK =30;

    //kromozomların tutuldugu matris
    private static int[][] tesisPopulasyon;

    //tesislerin bırbirilerıne olan uzaklıkları
    private static int[][] tesisAraUzaklik;

    //tesislerin bırbirilerıne olan baglilıkları
    private static int[][] tesisIsBagliliklari;

    //Bulunan uygunluk kromozomu
    private static int[] enUygunYapiliKromozom;

    //her bir nesilin her bır kromozom yapısını tutuyor
    private static int[] kromozomPuan = new int[POPULASYONGENISLIK];
    
    //her bir nesilin her bır kromozom puan oranı tutuyor
    private static int[] kromozomPuanOran = new int[POPULASYONGENISLIK];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        init();

    }

    private static void init() {

        //Tesis Populasyonu olusturuluyor
        tesisPopulasyon = TesisPopulasyonOlusturma();
        TesisPopulasyonGoster();

        //Tesislerin Birbirilerine olan uzaklıklıgı
        tesisAraUzaklik = TesisAraUzakliklar();
        TesisAraUzaklikGoster();

        //Tesislerin Birbirilerine olan uzaklıklıgı
        tesisIsBagliliklari = TesisIsBaglilik();
        TesisIsBaglilikGoster();

        //Genetik algoritma baslasin ve sonucunda en uygunu bulunsun
        enUygunYapiliKromozom = GenetikAlgoritmaBasla();
        EnUygunKromozomGoster();

    }

    //populasyon Kromozom İcerigını doldur
    private static int[][] TesisPopulasyonOlusturma() {
        //Geriye dondurulecek populasyon
        int[][] populasyon = new int[POPULASYONGENISLIK][POPULASYONGENISLIK];

        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            int j = 0;
            int geciciDeger;
            int durumKontrol = 0;
            while (j < POPULASYONGENISLIK) {

                geciciDeger = (int) (Math.random() * POPULASYONGENISLIK + 1);
                durumKontrol = 0;
                for (int k = 0; k < POPULASYONGENISLIK; k++) {
                    if (populasyon[i][k] == geciciDeger) {
                        durumKontrol = 1;
                        break;
                    }
                }

                if (durumKontrol != 1) {
                    populasyon[i][j] = geciciDeger;
                    j++;
                }

            }
        }
        return populasyon;
    }

    //populasyon kromozom icerigini goster
    private static void TesisPopulasyonGoster() {
        System.out.println("\nPopulasyon kromozomları");
        for (int i = 0; i < POPULASYONGENISLIK; i++) {
            for (int j = 0; j < POPULASYONGENISLIK; j++) {
                System.out.print(tesisPopulasyon[i][j] + " +");
            }
            System.out.println("");
        }

    }

    //Tesislerin Aralarında kı uzaklıkları raskele atama
    private static int[][] TesisAraUzakliklar() {

        //GEcici tesis uzaklıklarını tutmak icin
        int[][] geciciArauzaklik = new int[POPULASYONGENISLIK][POPULASYONGENISLIK];
        //uzaklık degerını raskele almak ıcın
        int gecici;
        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            for (int j = 0; j < POPULASYONGENISLIK; j++) {

                //gecici degeri raskele al
                gecici = (int) (Math.random() * POPULASYONGENISLIK + 1);

                //tesislerin kendılerıne olan uzaklıkları
                if (i == j) {

                    geciciArauzaklik[i][j] = 0;
                } else {

                    //birbirilerine olan uzaklıkları
                    geciciArauzaklik[i][j] = gecici;
                    geciciArauzaklik[j][i] = gecici;

                }

            }
        }
        //tesis uzaklıkları geri gonder
        return geciciArauzaklik;
    }

    //Tesislerin ara uzaklıklarını gosterme
    private static void TesisAraUzaklikGoster() {

        System.out.println("\nTesislerin birbirilerine olan uzaklıkları");
        for (int i = 0; i < POPULASYONGENISLIK; i++) {
            for (int j = 0; j < POPULASYONGENISLIK; j++) {

                System.out.print(tesisAraUzaklik[i][j] + " +");
            }
            System.out.println("");
        }

    }

    //Tesislerin bırbırı ıle ıs baglılıkları
    private static int[][] TesisIsBaglilik() {

        //gecıcı tesislerin birbirileri ıle baglılıklarını tutacak
        int[][] tesisIsBaglilik = new int[POPULASYONGENISLIK][POPULASYONGENISLIK];

        int raskele;

        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            for (int j = 0; j < POPULASYONGENISLIK; j++) {

                //her iki tesisin baglilikları degerlerini atıyorum
                raskele = (int) (Math.random() * POPULASYONGENISLIK + 1);

                if (i == j) {
                    tesisIsBaglilik[i][j] = 0;
                } else {

                    tesisIsBaglilik[i][j] = raskele;
                    tesisIsBaglilik[j][i] = raskele;
                }
            }
        }

        return tesisIsBaglilik;
    }

    //tesislerin bırbbırı ıle olan ıs baglılıgını goster
    private static void TesisIsBaglilikGoster() {

        System.out.println("\nTesislerin iş bagımlılıkları");

        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            for (int j = 0; j < POPULASYONGENISLIK; j++) {

                System.out.print(tesisIsBagliliklari[i][j] + " +");
            }
            System.out.println("");
        }
    }

    //En uygun kromozom yapısının ekrana bastırılması
    private static void EnUygunKromozomGoster() {

        System.out.println("\nEn iyi kromozom yapısı:");

        for (int j = 0; j < NESILSAYISI; j++) {

            System.out.print(enUygunYapiliKromozom[j] + "\n");
        }

    }

    //olusturulmus verılerle artık genetık algorıtma baslangıcı
    private static int[] GenetikAlgoritmaBasla() {

        //sonucmatrıslerınden ıyı ıyı olanı
        int[] uygunlukKromozomu = new int[NESILSAYISI];

        for (int i = 0; i < NESILSAYISI; i++) {

            System.out.println((i + 1) + ". Nesil için puanlar");

            //Populasyonda bulunan herbır kromozomun puanını hesaplar
            UygunlukHesapla();

            uygunlukKromozomu[i] = EnDusukPuanAl();
            kromozomPuanOran=RulletTekerlegi();
            KromozomPuanOranGoster();

            KromozomPuanGoster();

            //Rullet ıle her yenı nesılde en ıyı olan kromozomun secılıp gerıye gonderılmesı
            //uygunlukKromozomu = RulletTekerlegi();
            //Rsakele 2 lı kromozomdan ıyı sonuclu olanı diger ıkılıden ıyı sonuclu olanı caprazlanması
            SecilimYapCaprazlaMutasyonUgra();

        }

        //
        return uygunlukKromozomu;

    }

    //uygunluk hesaplanması
    private static void UygunlukHesapla() {

        //uygunluk degerının hesaplanması
        for (int j = 0; j < tesisPopulasyon.length; j++) {

            int uygunlukDegeri = 0;

            for (int i = 0; i < tesisPopulasyon.length - 1; i++) {

                uygunlukDegeri += tesisIsBagliliklari[tesisPopulasyon[i][j] - 1][tesisPopulasyon[j][i + 1] - 1]
                        * tesisAraUzaklik[tesisPopulasyon[j][i] - 1][tesisPopulasyon[j][i + 1] - 1];
            }
            kromozomPuan[j] = uygunlukDegeri;

        }
    }

    //suan kromozomların sahıp oldukları puanları goster
    private static void KromozomPuanGoster() {

        System.out.println("Kromozom puan Gösterımı:");
        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            System.out.println((i + 1) + ". Kromozom puanı:" + kromozomPuan[i] + " ");
        }
    }
    
    //suan kromozomların sahıp oldukları puan oranları goster
    private static void KromozomPuanOranGoster() {

        System.out.println("Kromozom puan Oran Gösterımı:");
        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            System.out.println((i + 1) + ". Kromozom puanı:" + kromozomPuanOran[i] + " ");
        }
    }

    //rulet ile puanlara gore oranların hesaplanıp en ıyı kromozom dondurulmesı
    private static int[] RulletTekerlegi() {


        //oranların tutuldugu dizi
        int kromozomOran[] = new int[POPULASYONGENISLIK];

        int toplampuan = 0;
        //toplam puan hesaplama
        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            toplampuan += kromozomPuan[i];

        }

        //HEr bır kromozom oranın hesaplanması
        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            kromozomOran[i] = (int) (360 * kromozomPuan[i]) / toplampuan;

            //en dusuk oran sahıbı olanınn ındıs degerının alınısı
            
        }

        //en ıyı orana sahıp kromozomun indisi
        //System.out.println(indis+1);
        //her bir kromozoma oranlar verılmesı ve en dusuk orana sahıp olanın gerıye gonderılmesı
        return kromozomOran;

    }

    //Raskele olarak baslangıc populasyonunda bulunan kromzom sayısına kadar secilim yapıp caprazlama yapma
    private static void SecilimYapCaprazlaMutasyonUgra() {

        int[][] p = new int[POPULASYONGENISLIK][POPULASYONGENISLIK];
        //Caprazlanmaya gidecek kromozomlar
        int[] caprazlanmaGidecekKromozomBir = new int[POPULASYONGENISLIK];
        int[] caprazlanmaGidecekKromozomIki = new int[POPULASYONGENISLIK];

        //raskele uretılen indis degeri
        int geciciKromozomIndisDeger;

        //puan ve indis degerlerının tutulacagı degıskenler
        int gecici1;
        int gecici1Indis;
        int gecici2;
        int gecici2Indis;

        int sayac = 0;
        int say = 0;

        //burada baslangıc puopulasyonuna kadar ulasııncaya devam eder secılım
        for (int i = 0; i < POPULASYONGENISLIK * 2; i++) {

            //raskele alınan indis degeri
            geciciKromozomIndisDeger = (int) (Math.random() * POPULASYONGENISLIK);

            gecici1Indis = geciciKromozomIndisDeger;
            gecici1 = kromozomPuan[geciciKromozomIndisDeger];

            i++;

            geciciKromozomIndisDeger = (int) (Math.random() * POPULASYONGENISLIK);

            gecici2 = kromozomPuan[geciciKromozomIndisDeger];
            gecici2Indis = geciciKromozomIndisDeger;

            if (sayac == 0) {
                if (gecici1 < gecici2) {

                    caprazlanmaGidecekKromozomBir = tesisPopulasyon[gecici1Indis];

                    sayac++;
                } else {

                    caprazlanmaGidecekKromozomBir = tesisPopulasyon[gecici2Indis];

                    sayac++;
                }
            } else {
                if (gecici1 < gecici2) {

                    caprazlanmaGidecekKromozomIki = tesisPopulasyon[gecici1Indis];

                    sayac--;
                } else {

                    caprazlanmaGidecekKromozomIki = tesisPopulasyon[gecici2Indis];
                    sayac--;
                }

                //IkıKromozomuCaprazla(caprazlanmaGidecekKromozomBir, caprazlanmaGidecekKromozomIkı);
                //Artık gelen ıkı kromozomu caprazlanmasını yapabılırız
                //caprazlama hangı noktadan olacagını raskele belırtıyorum
                //caprazlamadan onceki kromozom yapıları
                //KromozomYapilariniGoster("once", caprazlanmaGidecekKromozomBir, caprazlanmaGidecekKromozomIki);
                //caprazlamadan onceki kromozom yapıları sonu
                //Caprazlamanın yaılacagı nokta secımı
                int raskeleCaprazlamaYeri = (int) (Math.random() * POPULASYONGENISLIK + 1);

                //caprazlamanın yapılsı
                int[] gecicianne = new int[POPULASYONGENISLIK];

                for (int j = raskeleCaprazlamaYeri; j < POPULASYONGENISLIK; j++) {

                    gecicianne[j] = caprazlanmaGidecekKromozomBir[j];
                    caprazlanmaGidecekKromozomBir[j] = caprazlanmaGidecekKromozomIki[j];
                    caprazlanmaGidecekKromozomIki[j] = gecicianne[j];
                }
                //caprazlamanın yapılsının sonu

                //caprazlamadan sonra kı gosterım
                //KromozomYapilariniGoster("sonra", caprazlanmaGidecekKromozomBir, caprazlanmaGidecekKromozomIki);
                //caprazlamadan sonrakı gosterım sonu
                //caprazlama sonunda bozukluk olan yerlerın tamırı
                p[say++] = MutasyonUgrat(CaprazlamaSonuOnarim(caprazlanmaGidecekKromozomBir));

                p[say++] = MutasyonUgrat(CaprazlamaSonuOnarim(caprazlanmaGidecekKromozomIki));

                //tamır  ve mutasyon sonu
                
            }

        }

        tesisPopulasyon=p;
        System.out.println("\n\n");
     
    }
    
    //caprazlama durumşarında once ve sonrasında kromozom yapılarının gosterılımı
    private static void KromozomYapilariniGoster(String once, int[] caprazlanmaGidecekKromozomBir, int[] caprazlanmaGidecekKromozomIkı) {

        System.out.println("\n" + once + " 1");
        for (int j = 0; j < POPULASYONGENISLIK; j++) {

            System.out.print(caprazlanmaGidecekKromozomBir[j] + " ");

        }

        System.out.println("\n" + once + " 2");
        for (int j = 0; j < POPULASYONGENISLIK; j++) {

            System.out.print(caprazlanmaGidecekKromozomIkı[j] + " ");

        }

    }

    //caprazlanan kromozomların onarımı
    private static int[] CaprazlamaSonuOnarim(int[] onarim) {

        //her bir kromozom genını arama yontemı için alıyorum
        int tut;
        //alınan her gen ın ındısını tutuyorum
        int indis;

        //kromozom her bir elemanı ıcın bak
        for (int i = 0; i < POPULASYONGENISLIK; i++) {

            for (int j = 0; j < POPULASYONGENISLIK; j++) {

                //aynı gedere sahıp durumlar var
                if (i != j) {
                    if (onarim[i] == onarim[j]) {

                        int say = 0;
                        int geciciDeger;
                        int durumKontrol;
                        while (say < POPULASYONGENISLIK) {

                            //raskele degerler uretıp gelen kromozom genlerınde var olup 
                            //olmamasını kontrol edıp olmaması durumunda tekrar olan kısma ekleme yaparak ıslemı tamamlıyoruz 
                            geciciDeger = (int) (Math.random() * POPULASYONGENISLIK + 1);
                            durumKontrol = 0;
                            for (int k = 0; k < POPULASYONGENISLIK; k++) {
                                if (onarim[k] == geciciDeger) {

                                    //degerı kontrol edılen saf dısı et
                                    if (i != k) {
                                        durumKontrol = 1;
                                        break;
                                    }
                                }
                            }
                            //eger kı kontrol durumunda tekrar ıcıngenlerde bulunmayan ıse bunu degiştir
                            if (durumKontrol != 1) {
                                onarim[i] = geciciDeger;
                                say++;
                            }
                        }
                    }
                }
            }
        }
        return onarim;
    }

    private static int[] MutasyonUgrat(int[] mutasyon) {

        int mutasyonRaskeleDeger = (int) (Math.random() * POPULASYONGENISLIK);
        int mutasyonRaskeleDeger2 = (int) (Math.random() * POPULASYONGENISLIK);

        int gecici = mutasyon[mutasyonRaskeleDeger];
        mutasyon[mutasyonRaskeleDeger] = mutasyon[mutasyonRaskeleDeger2];
        mutasyon[mutasyonRaskeleDeger2] = gecici;

        //System.out.println("\n" + mutasyonRaskeleDeger + "    " + mutasyonRaskeleDeger2);
        return mutasyon;

    }

    private static int EnDusukPuanAl() {

        int enKucuk = kromozomPuan[0];

        for (int i = 1; i < POPULASYONGENISLIK; i++) {

            if (enKucuk > kromozomPuan[i]) {
                enKucuk = kromozomPuan[i];
            }
        }

        return enKucuk;

    }
}
