import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class Test {
  public static void main (String[] args) throws Exception{
    Scanner input = new Scanner(System.in);                                         //Initiation Scanner
    ArrayList<Widerstand> grabbel = new ArrayList<Widerstand>();                    //Initiation Grabbelkiste als ArrayList von Widerständen
    int eingabe;                                                                    //Initiation der Hilfsvariable für Einlesevorgänge
    
    System.out.println("--- Datei 'widerstaende.txt' wird eingelesen ---");
    FileReader fr = new FileReader("widerstaende.txt");                             //Initiation Einlesevorgang aus Datei 'widerstaende.txt'
    BufferedReader br = new BufferedReader(fr);
    while (br.ready()) {                                                            //solange nicht eingelesene Widerstandswerte in der Datei vorhanden sind
      eingabe = (int) Float.parseFloat(br.readLine());                              //nächster Widerstandswert wird eingelesen und auf ganze Zahl gerundet
      if (eingabe>0) {                                                              //Herausfilterung negativer Widerstände
        grabbel.add(new Widerstand(eingabe));                                       //Hinzufügen des Widerstandes zur Grabbelkiste
      } else {
        System.out.println("      --> UNZULÄSSIGER WIDERSTANDSWERT " + eingabe + " AUSGELASSEN ---");
      }                                         
    }
    br.close();
    System.out.println("--- Datei 'widerstaende.txt' erfolgreich eingelesen ---");
    
    System.out.println("Zu erzeugender Widerstand: Bitte Widerstandswert (natürliche Zahl) in Ohm eingeben (ohne Einheit)!");          
    System.out.print("Widerstandswert = ");
    eingabe = input.nextInt();                                                      //Einlesen des benötigten Widerstands als int eingabe
    //R: 500, 140, 314, 315, 1620, 2719, 4242
    
    for (int k=1;k<=4;k++) {
      berechnung(k,(float) eingabe,grabbel);                                        //nacheinander: Durchführung der nötigen Berechnungen für k=1,2,3,4
    }
  }
  
  public static void berechnung(int k, float eingabe, ArrayList<Widerstand> grabbel) {
    System.out.println("");
    System.out.println("k = " + k);
    System.out.println("");
    
    String schaltung="";                           //String für die Ausgabe des Schaltungsschemas
    float laufergebnis=Float.MAX_VALUE;            //jeweils berechnete Widerstandswerte der Schaltungen (Gleitkommazahl)           
    float ergebnis=Float.MAX_VALUE;                //bisher bestes gefundenes Ergebnis
    float diff=Float.MAX_VALUE;                    //Differenz des bisher besten gefundenen Ergebnisses zum benötigten Widerstand
    
    if (k>=1) {
      for (Widerstand r:grabbel) { 
        if (Math.abs((float) r.wert-eingabe)<diff) {                                //wenn diese Möglichkeit besser ist als die bisher beste
          schaltung=" " + r.wert + " ";                                             //speichere den Plan dieser Schaltung als beste Schaltung
          ergebnis=(float) r.wert;                                                  //speichere den Widerstandswert dieser Schaltung als beste Schaltung
          diff=Math.abs((float) r.wert-eingabe);                                    //speichere die Differenz zwischen ergebnis und eingabe als neues Optimum
        }
      }
    } if (k>=2) {
      for (Widerstand r1:grabbel) { 
        int indexr1 = grabbel.indexOf(r1);
        for (int indexr2=indexr1+1;indexr2<grabbel.size();indexr2++) {              //für jede mögliche Auswahl zweier Widerstände aus der Grabbelkiste
          Widerstand r2 = grabbel.get(indexr2);
          
          laufergebnis=(float) r1.wert+(float) r2.wert;                             //(R1 R2)
          if (Math.abs(laufergebnis-eingabe)<diff) {
            schaltung="(" + r1.wert + " " + r2.wert + ")";
            ergebnis=laufergebnis;
            diff=Math.abs(laufergebnis-eingabe);
          }
          
          laufergebnis=1/(1/(float) r1.wert+1/(float) r2.wert);                     //[R1 R2]
          if (Math.abs(laufergebnis-eingabe)<diff) {
            schaltung="[" + r1.wert + " " + r2.wert + "]";
            ergebnis=laufergebnis;
            diff=Math.abs(laufergebnis-eingabe);
          }
        }
      }
    } if (k>=3) {
      for (Widerstand r1:grabbel) { 
        int indexr1 = grabbel.indexOf(r1);
        for (int indexr2=indexr1+1;indexr2<grabbel.size();indexr2++) {                  
          Widerstand r2 = grabbel.get(indexr2);
          for (int indexr3=indexr2+1;indexr3<grabbel.size();indexr3++) {            //für jede mögliche Auswahl dreier Widerstände aus der Grabbelkiste
            Widerstand r3 = grabbel.get(indexr3);
          
          laufergebnis=(float) r1.wert+(float) r2.wert+(float) r3.wert;                                           //(R1 R2 R3)
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="(" + r1.wert + " " + r2.wert + " " + r3.wert + ")";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            }
          
          laufergebnis=1/((1/(float) r1.wert)+(1/(float) r2.wert)+(1/(float) r3.wert));                           //[R1 R2 R3]
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="[" + r1.wert + " " + r2.wert + " " + r3.wert + "]";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            }
            
          laufergebnis=1/((1/(float) r1.wert)+(1/((float) r2.wert+(float) r3.wert)));                             //[R1 (R2 R3)]
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="[" + r1.wert + " (" + r2.wert + " " + r3.wert + ")]";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            }
            
          laufergebnis=1/((1/(float) r2.wert)+(1/((float) r1.wert+(float) r3.wert)));                             //[R2 (R1 R3)]
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="[" + r2.wert + " (" + r1.wert + " " + r3.wert + ")]";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            }
            
          laufergebnis=1/((1/(float) r3.wert)+(1/((float) r1.wert+(float) r2.wert)));                             //[R3 (R1 R2)]
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="[" + r3.wert + " (" + r1.wert + " " + r2.wert + ")]";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            } 
            
          laufergebnis=1/((1/(float) r1.wert)+(1/(float) r2.wert))+(float) r3.wert;                               //([R1 R2] R3)]
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="([" + r1.wert + " " + r2.wert + "] " + r3.wert + ")";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            }
            
          laufergebnis=1/((1/(float) r2.wert)+(1/(float) r3.wert))+(float) r1.wert;                               //([R2 R3] R1)]
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="([" + r2.wert + " " + r3.wert + "] " + r1.wert + ")";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            }
            
          laufergebnis=1/((1/(float) r1.wert)+(1/(float) r3.wert))+(float) r2.wert;                               //([R1 R3] R2)]
            if (Math.abs(laufergebnis-eingabe)<diff) {
              schaltung="([" + r1.wert + " " + r3.wert + "] " + r2.wert + ")";
              ergebnis=laufergebnis;
              diff=Math.abs(laufergebnis-eingabe);
            }
          }
        }
      }
    } if (k>=4) {
      for (Widerstand r1:grabbel) { 
        int indexr1 = grabbel.indexOf(r1);
        for (int indexr2=indexr1+1;indexr2<grabbel.size();indexr2++) {                  
          Widerstand r2 = grabbel.get(indexr2);
          for (int indexr3=indexr2+1;indexr3<grabbel.size();indexr3++) {               
            Widerstand r3 = grabbel.get(indexr3);
            for (int indexr4=indexr3+1;indexr4<grabbel.size();indexr4++) {           //für jede mögliche Auswahl von 4 Widerständen aus der Grabbelkiste
              Widerstand r4 = grabbel.get(indexr4);
          
              laufergebnis=(float) r1.wert+(float) r2.wert+(float) r3.wert+(float) r4.wert;                             //(R1 R2 R3 R4)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r1.wert + " " + r2.wert + " " + r3.wert + " " + r4.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
            
              laufergebnis=1/((1/(float) r1.wert)+(1/(float) r2.wert)+(1/(float) r3.wert)+(1/(float) r4.wert));         //[R1 R2 R3 R4]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " " + r2.wert + " " + r3.wert + " " + r4.wert + "]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r1.wert)+(1/((float) r2.wert+(float) r3.wert+(float) r4.wert)));               //[R1 (R2 R3 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " (" + r2.wert + " " + r3.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r2.wert)+(1/((float) r1.wert+(float) r3.wert+(float) r4.wert)));               //[R2 (R1 R3 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r2.wert + " (" + r1.wert + " " + r3.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r3.wert)+(1/((float) r1.wert+(float) r2.wert+(float) r4.wert)));               //[R3 (R1 R2 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r3.wert + " (" + r1.wert + " " + r2.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r4.wert)+(1/((float) r1.wert+(float) r2.wert+(float) r3.wert)));               //[R4 (R1 R2 R3)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r4.wert + " (" + r1.wert + " " + r2.wert + " " + r3.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r1.wert)+(1/(float) r2.wert)+(1/(float) r3.wert))+(float) r4.wert;             //([R1 R2 R3] R4)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r2.wert + " " + r3.wert + "] " + r4.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r1.wert)+(1/(float) r2.wert)+(1/(float) r4.wert))+(float) r3.wert;             //([R1 R2 R4] R3)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r2.wert + " " + r4.wert + "] " + r3.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r1.wert)+(1/(float) r3.wert)+(1/(float) r4.wert))+(float) r2.wert;             //([R1 R3 R4] R2)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r3.wert + " " + r4.wert + "] " + r2.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/((1/(float) r2.wert)+(1/(float) r3.wert)+(1/(float) r4.wert))+(float) r1.wert;             //([R2 R3 R4] R1)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r2.wert + " " + r3.wert + " " + r4.wert + "] " + r1.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/((float) r1.wert+(float) r2.wert)+1/((float) r3.wert+(float) r4.wert));                 //[(R1 R2) (R3 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[(" + r1.wert + " " + r2.wert + ") (" + r3.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/((float) r1.wert+(float) r3.wert)+1/((float) r2.wert+(float) r4.wert));                 //[(R1 R3) (R2 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[(" + r1.wert + " " + r3.wert + ") (" + r2.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/((float) r1.wert+(float) r4.wert)+1/((float) r2.wert+(float) r3.wert));                 //[(R1 R4) (R2 R3)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[(" + r1.wert + " " + r4.wert + ") (" + r2.wert + " " + r3.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r2.wert)+1/(1/(float) r3.wert+1/(float) r4.wert);             //([R1 R2] [R3 R4])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r2.wert + "] [" + r3.wert + " " + r4.wert + "])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r3.wert)+1/(1/(float) r2.wert+1/(float) r4.wert);             //([R1 R3] [R2 R4])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r3.wert + "] [" + r2.wert + " " + r4.wert + "])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r4.wert)+1/(1/(float) r2.wert+1/(float) r3.wert);             //([R1 R4] [R2 R3])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r4.wert + "] [" + r2.wert + " " + r3.wert + "])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r2.wert)+(float) r3.wert+(float) r4.wert;                     //([R1 R2] R3 R4)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r2.wert + "] " + r3.wert + " " + r4.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r3.wert)+(float) r2.wert+(float) r4.wert;                     //([R1 R3] R2 R4)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r3.wert + "] " + r2.wert + " " + r4.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r4.wert)+(float) r2.wert+(float) r3.wert;                     //([R1 R4] R2 R3)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r1.wert + " " + r4.wert + "] " + r2.wert + " " + r3.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r2.wert+1/(float) r3.wert)+(float) r1.wert+(float) r4.wert;                     //([R2 R3] R1 R4)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r2.wert + " " + r3.wert + "] " + r1.wert + " " + r4.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r2.wert+1/(float) r4.wert)+(float) r1.wert+(float) r3.wert;                     //([R2 R4] R1 R3)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r2.wert + " " + r4.wert + "] " + r1.wert + " " + r3.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r3.wert+1/(float) r4.wert)+(float) r1.wert+(float) r2.wert;                     //([R3 R4] R1 R2)
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="([" + r3.wert + " " + r4.wert + "] " + r1.wert + " " + r2.wert + ")";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r1.wert+1/(1/(float) r2.wert+1/((float) r3.wert+(float) r4.wert));                   //(R1 [R2 (R3 R4)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r1.wert + " [" + r2.wert + " (" + r3.wert + " " + r4.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r1.wert+1/(1/(float) r3.wert+1/((float) r2.wert+(float) r4.wert));                   //(R1 [R3 (R2 R4)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r1.wert + " [" + r3.wert + " (" + r2.wert + " " + r4.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r1.wert+1/(1/(float) r4.wert+1/((float) r2.wert+(float) r3.wert));                   //(R1 [R4 (R2 R3)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r1.wert + " [" + r4.wert + " (" + r2.wert + " " + r3.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r2.wert+1/(1/(float) r1.wert+1/((float) r3.wert+(float) r4.wert));                   //(R2 [R1 (R3 R4)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r2.wert + " [" + r1.wert + " (" + r3.wert + " " + r4.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r2.wert+1/(1/(float) r3.wert+1/((float) r1.wert+(float) r4.wert));                   //(R2 [R3 (R1 R4)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r2.wert + " [" + r3.wert + " (" + r1.wert + " " + r4.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r2.wert+1/(1/(float) r4.wert+1/((float) r1.wert+(float) r3.wert));                   //(R2 [R4 (R1 R3)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r2.wert + " [" + r4.wert + " (" + r1.wert + " " + r3.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r3.wert+1/(1/(float) r1.wert+1/((float) r2.wert+(float) r4.wert));                   //(R3 [R1 (R2 R4)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r3.wert + " [" + r1.wert + " (" + r2.wert + " " + r4.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r3.wert+1/(1/(float) r2.wert+1/((float) r1.wert+(float) r4.wert));                   //(R3 [R2 (R1 R4)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r3.wert + " [" + r2.wert + " (" + r1.wert + " " + r4.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r3.wert+1/(1/(float) r4.wert+1/((float) r1.wert+(float) r2.wert));                   //(R3 [R4 (R1 R2)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r3.wert + " [" + r4.wert + " (" + r1.wert + " " + r2.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r4.wert+1/(1/(float) r1.wert+1/((float) r2.wert+(float) r3.wert));                   //(R4 [R1 (R2 R3)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r4.wert + " [" + r1.wert + " (" + r2.wert + " " + r3.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r4.wert+1/(1/(float) r2.wert+1/((float) r1.wert+(float) r3.wert));                   //(R4 [R2 (R1 R3)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r4.wert + " [" + r2.wert + " (" + r1.wert + " " + r3.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=(float) r4.wert+1/(1/(float) r3.wert+1/((float) r1.wert+(float) r2.wert));                   //(R4 [R3 (R1 R2)])
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="(" + r4.wert + " [" + r3.wert + " (" + r1.wert + " " + r2.wert + ")])";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/((float) r2.wert+1/(1/(float) r3.wert+1/(float) r4.wert)));           //[R1 (R2 [R3 R4])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " (" + r2.wert + " [" + r3.wert + " " + r4.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/((float) r3.wert+1/(1/(float) r2.wert+1/(float) r4.wert)));           //[R1 (R3 [R2 R4])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " (" + r3.wert + " [" + r2.wert + " " + r4.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/((float) r4.wert+1/(1/(float) r2.wert+1/(float) r3.wert)));           //[R1 (R4 [R2 R3])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " (" + r4.wert + " [" + r2.wert + " " + r3.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r2.wert+1/((float) r1.wert+1/(1/(float) r3.wert+1/(float) r4.wert)));           //[R2 (R1 [R3 R4])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r2.wert + " (" + r1.wert + " [" + r3.wert + " " + r4.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r2.wert+1/((float) r3.wert+1/(1/(float) r1.wert+1/(float) r4.wert)));           //[R2 (R3 [R1 R4])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r2.wert + " (" + r3.wert + " [" + r1.wert + " " + r4.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r2.wert+1/((float) r4.wert+1/(1/(float) r1.wert+1/(float) r3.wert)));           //[R2 (R4 [R1 R3])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r2.wert + " (" + r4.wert + " [" + r1.wert + " " + r3.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r3.wert+1/((float) r1.wert+1/(1/(float) r2.wert+1/(float) r4.wert)));           //[R3 (R1 [R2 R4])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r3.wert + " (" + r1.wert + " [" + r2.wert + " " + r4.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r3.wert+1/((float) r2.wert+1/(1/(float) r1.wert+1/(float) r4.wert)));           //[R3 (R2 [R1 R4])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r3.wert + " (" + r2.wert + " [" + r1.wert + " " + r4.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r3.wert+1/((float) r4.wert+1/(1/(float) r1.wert+1/(float) r2.wert)));           //[R3 (R4 [R1 R2])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r3.wert + " (" + r4.wert + " [" + r1.wert + " " + r2.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r4.wert+1/((float) r1.wert+1/(1/(float) r2.wert+1/(float) r3.wert)));           //[R4 (R1 [R2 R3])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r4.wert + " (" + r1.wert + " [" + r2.wert + " " + r3.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r4.wert+1/((float) r2.wert+1/(1/(float) r1.wert+1/(float) r3.wert)));           //[R4 (R2 [R1 R3])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r4.wert + " (" + r2.wert + " [" + r1.wert + " " + r3.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r4.wert+1/((float) r3.wert+1/(1/(float) r1.wert+1/(float) r2.wert)));           //[R4 (R3 [R1 R2])]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r4.wert + " (" + r3.wert + " [" + r1.wert + " " + r2.wert + "])]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r2.wert+1/((float) r3.wert+(float) r4.wert));                 //[R1 R2 (R3 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " " + r2.wert + " (" + r3.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r3.wert+1/((float) r2.wert+(float) r4.wert));                 //[R1 R3 (R2 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " " + r3.wert + " (" + r2.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r1.wert+1/(float) r4.wert+1/((float) r2.wert+(float) r3.wert));                 //[R1 R4 (R2 R3)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r1.wert + " " + r4.wert + " (" + r2.wert + " " + r3.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r2.wert+1/(float) r3.wert+1/((float) r1.wert+(float) r4.wert));                 //[R2 R3 (R1 R4)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r2.wert + " " + r3.wert + " (" + r1.wert + " " + r4.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r2.wert+1/(float) r4.wert+1/((float) r1.wert+(float) r3.wert));                 //[R2 R4 (R1 R3)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r2.wert + " " + r4.wert + " (" + r1.wert + " " + r3.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
              
              laufergebnis=1/(1/(float) r3.wert+1/(float) r4.wert+1/((float) r1.wert+(float) r2.wert));                 //[R3 R4 (R1 R2)]
              if (Math.abs(laufergebnis-eingabe)<diff) {
                schaltung="[" + r3.wert + " " + r4.wert + " (" + r1.wert + " " + r2.wert + ")]";
                ergebnis=laufergebnis;
                diff=Math.abs(laufergebnis-eingabe);
              }
            }
          }
        }
      }
    } 
    
    System.out.println("Schaltung aus Widerstaenden (Widerstände in runden Klammern sind in Serie, Widerstände in eckigen Klammern parallel geschaltet): " + schaltung);
    System.out.println("Widerstandswert der Schaltung in Ohm: " + ergebnis);
    System.out.println("Abweichung zum gewünschten Ergebnis in Ohm: " + diff);                             //Ausgabe der besten Schaltung
  }
}
