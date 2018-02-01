import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static SzyfrProjekt log = new SzyfrProjekt();
    private static String s1;
    private static String s1a;
    private static String s2;
    private static String s3;
    private static String s3a;
    private static String s4;
    private static String s5;
    private static String s6;
    private static String s7;
    private static String s8;
    private static String s9;
    private static String klucz_glowny;
    String wiadomosc_zaszyfrowana;

    @FXML
    private Button odszyfruj;
    @FXML
    private Button szyfruj;
    @FXML
    private TextArea tekst;
    @FXML
    private TextArea wiadomosc;
    @FXML
    private TextArea szyfr;
    @FXML
    private ImageView obraz;

    public void initialize(URL location, ResourceBundle resources) {

        szyfruj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String s1 = log.stringDoPostac01(tekst.getText());
                System.out.println("Postać 01: " + s1);
                String s1a = log.szyfrowaniePostaci01(s1);
                System.out.println("Postać 01 2: " + s1a);
                String s2 = log.szyfrowanie01(s1a);
                System.out.println("Zaszyfrowana Postać 01: " + s2);
                String s3 = log.stringDoPostac01(s2);
                System.out.println("Postać 01 postać 2: " + s3);
                String s3a = log.szyfrowaniePostaci01(s3);
                System.out.println("Postać 01 postać 2 wersja 2: " + s3a);
                String s4 = log.szyfrowanie01(s3a);
                System.out.println("Zaszyfrowana Postać 01 postać 2: " + s4);
                String s11 = log.zamianaMiejsc(s4);
                System.out.println("Zamiana znaków: " + s11);
                String s13 = log.zamianaBloki(s11);
                System.out.println("Zamiana bloków: " + s13);

                szyfr.setText(s13);
            }
        });

        odszyfruj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    klucz_glowny = new String ( Files.readAllBytes( Paths.get(log.odczytKlucz()) ) );
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                System.out.println(klucz_glowny);
//                System.out.println(log.klucz_glowny);
                System.out.println("-----------------------------------------------------------------");
                String s14 = log.zamianaBlokiOdszyfrowanie(szyfr.getText());
                System.out.println("Odszyfowanie zamiany bloków: " + s14);
                String s12 = log.zamianaMiejscOdszyfrowanie(s14);
                System.out.println("Odszyfowanie zamianay znaków: " + s12);
                String s5 = log.tekst01Odszyfrowanie2(s12);
                System.out.println("Odszyfrowanie postaci 01 postać 2 wersja 2: " + s5);
                String s6 = log.odszyfrowaniePostaci01(s5,klucz_glowny);
                System.out.println("Odszyfrowanie postaci 01 postać 2: " + s6);
                String s7 = log.kluczZaszyfrowanie(s6);
                System.out.println("Odszyfrowanie zaszyfrowanej postaci 01: " + s7);
                String s8 = log.tekst01Odszyfrowanie(s7);
                System.out.println("Odszyfrowanie postaci 01 2: " + s8);
                String s9 = log.odszyfrowaniePostaci01Pierwszej(s8,klucz_glowny);
                System.out.println("Odszyfrowanie postaci 01: " + s9);
                String s10 = log.tekstOdszyfrowanie(s9);
                System.out.println("Odszyfrowanie tekstu: " + s10);
                wiadomosc.setText(s10);
            }
        });
    }
}
