import javafx.stage.FileChooser;
import sun.misc.IOUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SzyfrProjekt {

    private static String alfabet = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWYZŹŻaąbcćdeęfghijklłmnńoóprsśtuwyzźż0123456789!@#$%^&*()<>?/:;{}[]+-|";
    private static String alfabet2 = "ABCDEFGHIJKLMNOPRSTUWYZabcdefghijklmnoprstuwyz0123456789!@#$%^&*()<>?/:;{}[]+-|";
    public static String klucz_glowny = "";

    public static void main(String[] args) {

        String pruba = "Ala ma kota";

        String s1 = stringDoPostac01(pruba);
        System.out.println("Postać 01: " + s1);
        String s1a = szyfrowaniePostaci01(s1);
        System.out.println("Postać 01 2: " + s1a);
        String s2 = szyfrowanie01(s1a);
        System.out.println("Zaszyfrowana Postać 01: " + s2);
        String s3 = stringDoPostac01(s2);
        System.out.println("Postać 01 postać 2: " + s3);
        String s3a = szyfrowaniePostaci01(s3);
        System.out.println("Postać 01 postać 2 wersja 2: " + s3a);
        String s4 = szyfrowanie01(s3a);
        System.out.println("Zaszyfrowana Postać 01 postać 2: " + s4);
        String s11 = zamianaMiejsc(s4);
        System.out.println("Zamiana znaków: " + s11);
        String s13 = zamianaBloki(s11);
        System.out.println("Zamiana bloków: " + s13);
//        System.out.println("Klucz: " + klucz_glowny)
//        String s15 = kluczZaszyfrowanieCezar(s14);
//        System.out.println("Cezar: " + s15);
//        System.out.println("Klucz: " + klucz_glowny);
//        String s16 = kluczOdszyfrowanyCezar(s15);
//        System.out.println("Odszyfrowanie cezar: " + s16);

        System.out.println("-----------------------------------------------------------------");
        String s14 = zamianaBlokiOdszyfrowanie(s13);
        System.out.println("Odszyfowanie zamiany bloków: " + s14);
        String s12 = zamianaMiejscOdszyfrowanie(s14);
        System.out.println("Odszyfowanie zamianay znaków: " + s14);
        String s5 = tekst01Odszyfrowanie2(s12);
        System.out.println("Odszyfrowanie postaci 01 postać 2 wersja 2: " + s5);
        String s6 = odszyfrowaniePostaci01(s5,klucz_glowny);
        System.out.println("Odszyfrowanie postaci 01 postać 2: " + s6);
        String s7 = kluczZaszyfrowanie(s6);
        System.out.println("Odszyfrowanie zaszyfrowanej postaci 01: " + s7);
        String s8 = tekst01Odszyfrowanie(s7);
        System.out.println("Odszyfrowanie postaci 01 2: " + s8);
        String s9 = odszyfrowaniePostaci01Pierwszej(s8,klucz_glowny);
        System.out.println("Odszyfrowanie postaci 01: " + s9);
        System.out.println("Odszyfrowanie tekstu: " + tekstOdszyfrowanie(s9));
    }

    public static String zamianaMiejsc(String znaki)
    {
        String[] slowa = znaki.split(" ");
        String wiadomosc_odszyfrowana = "";
        String liczby = "";

        for (int i = 0; i < slowa.length; i++) {
            char[] tablica_znakow = slowa[i].toCharArray();

            for (int j = 0; j < 2; j++) {
                int losowy_numer = 0;
                int losowy_numer2 = 0;

                while (losowy_numer == losowy_numer2) {
                    losowy_numer = ThreadLocalRandom.current().nextInt(0, 7);
                    losowy_numer2 = ThreadLocalRandom.current().nextInt(0, 7);
                }
                liczby = liczby + losowy_numer + losowy_numer2;

                char znak_tymczasowy = tablica_znakow[losowy_numer];
                tablica_znakow[losowy_numer] = tablica_znakow[losowy_numer2];
                tablica_znakow[losowy_numer2] = znak_tymczasowy;

            }
            klucz_glowny = klucz_glowny + liczby + ",";
            liczby = "";
            for (char litera : tablica_znakow) {
                wiadomosc_odszyfrowana = wiadomosc_odszyfrowana + litera;
            }
            wiadomosc_odszyfrowana = wiadomosc_odszyfrowana + " ";
        }
        return wiadomosc_odszyfrowana;
    }

    public static String zamianaBloki(String znaki)
    {
        String[] slowa = znaki.split(" ");
        String wiadomosc_odszyfrowana = "";
        String liczby = "";

        klucz_glowny = klucz_glowny + " ";
            for (int j = 0; j < 24; j++) {
                int losowy_numer = 0;
                int losowy_numer2 = 0;

                while (losowy_numer == losowy_numer2) {
                    losowy_numer = ThreadLocalRandom.current().nextInt(0, slowa.length);
                    losowy_numer2 = ThreadLocalRandom.current().nextInt(0, slowa.length);
                }
                liczby = liczby + losowy_numer + ";" + losowy_numer2;

                String slowo_tymczasowy = slowa[losowy_numer];
                slowa[losowy_numer] = slowa[losowy_numer2];
                slowa[losowy_numer2] = slowo_tymczasowy;

                klucz_glowny = klucz_glowny + liczby + ",";
                liczby = "";
            }

        for (String slowo: slowa) {
            wiadomosc_odszyfrowana = wiadomosc_odszyfrowana + slowo + " ";
        }

        zapisKlucz(klucz_glowny);
        return wiadomosc_odszyfrowana;
    }

    public static String zamianaMiejscOdszyfrowanie(String znaki)
    {
        String[] bloki = znaki.split(" ");
        String[] bloki_klucza = klucz_glowny.split(" ");
        String wiadomosc_odszyfrowana = "";
        String[] blok_klucza = bloki_klucza[6].split(",");

        for (int i = 0; i < bloki.length; i++) {
            char[] tablica_znakow = bloki[i].toCharArray();

            int x = 0;
            int x2 = 0;
            int x3 = 0;
            int x4 = 0;
            x = Character.getNumericValue(blok_klucza[i].charAt(0));
            x2 = Character.getNumericValue(blok_klucza[i].charAt(1));
            x3 = Character.getNumericValue(blok_klucza[i].charAt(2));
            x4 = Character.getNumericValue(blok_klucza[i].charAt(3));

                char znak_tymczasowy = tablica_znakow[x3];
                tablica_znakow[x3] = tablica_znakow[x4];
                tablica_znakow[x4] = znak_tymczasowy;

            char znak_tymczasowy2 = tablica_znakow[x];
            tablica_znakow[x] = tablica_znakow[x2];
            tablica_znakow[x2] = znak_tymczasowy2;

            for (char litera : tablica_znakow) {
                wiadomosc_odszyfrowana = wiadomosc_odszyfrowana + litera;
            }

            wiadomosc_odszyfrowana = wiadomosc_odszyfrowana + " ";
        }
        return wiadomosc_odszyfrowana;
    }

    public static String zamianaBlokiOdszyfrowanie(String znaki)
    {
        String[] bloki = znaki.split(" ");
        String[] bloki_klucza = klucz_glowny.split(" ");
        String wiadomosc_odszyfrowana = "";
        String[] blok_klucza = bloki_klucza[7].split(",");

        for (int i = blok_klucza.length-1; i >= 0; i--) {
            String[] tablica_znakow = blok_klucza[i].split(";");

            int x = Integer.parseInt(tablica_znakow[0]);
            int x2 = Integer.parseInt(tablica_znakow[1]);

            String slowo_tymczasowy = bloki[x];
            bloki[x] = bloki[x2];
            bloki[x2] = slowo_tymczasowy;
        }

        for (String slowo : bloki) {
                wiadomosc_odszyfrowana = wiadomosc_odszyfrowana + slowo + " ";
            }

        return wiadomosc_odszyfrowana;
    }

    public static String tekstOdszyfrowanie(String tekst01)
    {
        String wiadomosc = tekst01;
        String wiadomosc_odszyfrowana = "";

        wiadomosc = wiadomosc.replace(" ", "");
        for(int i = 0; i <= wiadomosc.length() - 8; i+=8)
        {
            int k = Integer.parseInt(wiadomosc.substring(i, i+8), 2);
            wiadomosc_odszyfrowana += (char) k;
        }
        return wiadomosc_odszyfrowana;
    }

    public static String stringDoPostac01(String tekst){
        byte[] tablica_byte = tekst.getBytes();
        StringBuilder sb = new StringBuilder();
        for (byte bit : tablica_byte)
        {
            int wartosc = bit;
            for (int i = 0; i < 8; i++)
            {
                sb.append((wartosc & 128) == 0 ? 0 : 1);
                wartosc <<= 1;
            }
            sb.append(' ');
        }
        String zero_jeden = sb.toString();
        return zero_jeden;
    }

    public static String szyfrowaniePostaci01(String tekst01){
        String zero_jeden_szyfr = "";
        Random random = new Random();
        String[] znak01 = tekst01.split(" ");
        for (int i = 0; i < znak01.length; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                int losowy_numer = random.nextInt(znak01[i].length() - 0) + 0;
                klucz_glowny = klucz_glowny + losowy_numer;

                StringBuilder sb = new StringBuilder(znak01[i]);
                String z = String.valueOf(znak01[i].charAt(losowy_numer));

                if (z.equals("0")){
                    sb.setCharAt(losowy_numer, '1');
                }else if (z.equals("1")){
                    sb.setCharAt(losowy_numer, '0');
                }

                znak01[i] = znak01[i].replaceAll(znak01[i],sb.toString());
            }
            zero_jeden_szyfr = zero_jeden_szyfr + znak01[i] + " ";
        }
        klucz_glowny = klucz_glowny + " ";
        return zero_jeden_szyfr;
    }

    public static String odszyfrowaniePostaci01(String tekst, String klucz){
        String zero_jeden_szyfr = "";
        String[] klucz01 = klucz.split(" ");
        String[] tekst01 = tekst.split(" ");
        String[] klucz_dwujki = klucz01[3].split("(?<=\\G..)");
        for (int i = 0; i < tekst01.length; i++)
        {
            String d = klucz_dwujki[i];
            for (int j = 0; j < 2; j++)
            {
                int losowy_numer = Character.getNumericValue(d.charAt(j));

                StringBuilder sb = new StringBuilder(tekst01[i]);
                String z = String.valueOf(tekst01[i].charAt(losowy_numer));

                if (z.equals("0")){
                    sb.setCharAt(losowy_numer, '1');
                }else if (z.equals("1")){
                    sb.setCharAt(losowy_numer, '0');
                }

                tekst01[i] = tekst01[i].replaceAll(tekst01[i],sb.toString());
            }
            zero_jeden_szyfr = zero_jeden_szyfr + tekst01[i] + " ";
        }
        return zero_jeden_szyfr;
    }

    public static String odszyfrowaniePostaci01Pierwszej(String tekst, String klucz){
        String zero_jeden_szyfr = "";
        String[] klucz01 = klucz.split(" ");
        String[] tekst01 = tekst.split(" ");
        String[] klucz_dwujki = klucz01[0].split("(?<=\\G..)");
        for (int i = 0; i < tekst01.length; i++)
        {
            String d = klucz_dwujki[i];
            for (int j = 0; j < 2; j++)
            {
                int losowy_numer = Character.getNumericValue(d.charAt(j));

                StringBuilder sb = new StringBuilder(tekst01[i]);
                String z = String.valueOf(tekst01[i].charAt(losowy_numer));

                if (z.equals("0")){
                    sb.setCharAt(losowy_numer, '1');
                }else if (z.equals("1")){
                    sb.setCharAt(losowy_numer, '0');
                }

                tekst01[i] = tekst01[i].replaceAll(tekst01[i],sb.toString());
            }
            zero_jeden_szyfr = zero_jeden_szyfr + tekst01[i] + " ";
        }
        return zero_jeden_szyfr;
    }

    public static String kluczZaszyfrowanie(String tekst01){
        String tekst_zaszyfrowany = "";
        String[] znak01 = tekst01.split(" ");
        for (String znak: znak01) {
            try {
            int parseInt = Integer.parseInt(znak, 2);
                char litera = (char)parseInt;
                tekst_zaszyfrowany = tekst_zaszyfrowany + String.valueOf(litera);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return tekst_zaszyfrowany;
    }

    public static String szyfrowanie01(String tekst01){
        String tekst_zaszyfrowany = "";
        String zera = "";
        String jedynki = "";
        StringBuilder sb = new StringBuilder(tekst01);
        for (int i = 0; i < tekst01.length(); i++) {
            int losowy_numer = 0;

            losowy_numer = ThreadLocalRandom.current().nextInt(0, 78 + 1);

                if (!(tekst01.charAt(i) == ' ')) {
                    if (tekst01.charAt(i) == '0') {
                        zera = zera + i + ",";
                    } else if (tekst01.charAt(i) == '1') {
                        jedynki = jedynki + i + ",";
                    }
                    sb.setCharAt(i, alfabet2.charAt(losowy_numer));
                } else {

                }
        }
        klucz_glowny = klucz_glowny + zera +" " + jedynki + " ";
        tekst_zaszyfrowany = tekst_zaszyfrowany + sb.toString();
        return tekst_zaszyfrowany;
    }

    public static String tekst01Odszyfrowanie(String zaszyfrowany){
        String tekst_odszyfrowany = "";
        String[] klucz_słowo = klucz_glowny.split(" ");
        String[] zera = klucz_słowo[1].split(",");
        String[] jedynki = klucz_słowo[2].split(",");
        StringBuilder sb = new StringBuilder(zaszyfrowany);
        for (int i = 0; i < zaszyfrowany.length(); i++) {
            for (String znak: zera) {
                if (i == Integer.parseInt(znak))
                {
                    sb.setCharAt(i, '0');
                }
            }
            for (String znak: jedynki) {
                if (i == Integer.parseInt(znak))
                {
                    sb.setCharAt(i, '1');
                }
            }
        }
        tekst_odszyfrowany = tekst_odszyfrowany + sb.toString();
        return tekst_odszyfrowany;
    }

    public static String tekst01Odszyfrowanie2(String zaszyfrowany){
        String tekst_odszyfrowany = "";
        String[] klucz_słowo = klucz_glowny.split(" ");
        String[] zera = klucz_słowo[4].split(",");
        String[] jedynki = klucz_słowo[5].split(",");
        StringBuilder sb = new StringBuilder(zaszyfrowany);
        for (int i = 0; i < zaszyfrowany.length(); i++) {
            for (String znak: zera) {
                if (i == Integer.parseInt(znak))
                {
                    sb.setCharAt(i, '0');
                }
            }
            for (String znak: jedynki) {
                if (i == Integer.parseInt(znak))
                {
                    sb.setCharAt(i, '1');
                }
            }
        }
        tekst_odszyfrowany = tekst_odszyfrowany + sb.toString();
        return tekst_odszyfrowany;
    }

    public static void zapisKlucz(String x){
        FileChooser wybieracz = new FileChooser();
        FileChooser.ExtensionFilter rodzaj = new FileChooser.ExtensionFilter("TXT plik (*.txt)", "*.txt");
        wybieracz.getExtensionFilters().add(rodzaj);
        String domowy = System.getProperty("user.home");
        File plik = new File(domowy);
        if (!plik.canRead()) {
            plik = new File("c:/");
        }
        wybieracz.setInitialDirectory(plik);
        wybieracz.setTitle("Zapis raportu");
        File plik2 = wybieracz.showSaveDialog(null);
        if (plik2 != null) {
            try {
                FileWriter fw = new FileWriter(plik2,true);
                fw.write(x+"\r\n");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String odczytKlucz(){
        String droga = "";
        FileChooser wybieracz = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TXT plik (*.txt)", "*.txt");
        wybieracz.getExtensionFilters().add(filter);

        String domowyk = System.getProperty("user.home");
        File plik = new File(domowyk);
        if(!plik.canRead()) {
            plik = new File("c:/");
        }
        wybieracz.setInitialDirectory(plik);

        File plik2 = wybieracz.showOpenDialog(null);
        String drogaplik;
        if(plik2 != null) {
            drogaplik = plik2.getPath();
            droga = drogaplik;
        } else {
            drogaplik = null;
        }
        return droga;
    }
}
