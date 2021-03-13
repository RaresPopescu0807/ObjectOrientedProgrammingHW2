/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inregistrare;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * In clasa casa stochez temperatura dorita, timpul de referinta, numarul de
 * camere, un HashMap format din perechi string, ICam care face legatura intre
 * id-ul senzorului si informatiile despre camera in care se afla acest senzor
 * si un HashMap care face legatura intre numele unei camere si id-ul senzorului
 * din ea pe care il pot folosi pentru a accesa informatiile unei camere si
 * folosind numele ei, nu numai id-ul senzorului.
 */
public class Casa
{

    double temperaturaDorita;
    long ref;
    int nr;
    double umiditateaDorita;
    HashMap<String, ICam> camere;
    HashMap<String, String> numeId;

    public Casa()
    {
        this.camere = new HashMap<>();
        this.numeId = new HashMap<>();
        this.umiditateaDorita = -1;
    }

    /**
     *
     * Metoda adaugaCamere este folosita in metoda citesteCasa care citeste din
     * fisier primii cativa parametri si initializeaza casa cu caracteristicile
     * necesare ficarui test.
     */
    public void adaugaCamera(String nume, String id, int suprafata)
    {
        this.camere.put(id, new ICam(suprafata));
        this.numeId.put(nume, id);

    }

    /**
     * Metoda adaugaInregistrare primeste ca parametrii id-ul senzorului care
     * face inregistrarea pentru a stii ince camera sa o adauge, timpul pentru a
     * putea calcula in ce interval sa adauge si temperatura pentru a stabili
     * valoarea inregistrarii adaugate.
     */
    public void adaugaInregistrare(String id, long timp, double temperatura)
    {
        double i = ((this.ref - timp) / 3600.0);
        //System.out.println(i);
        if ((i >= 0) && (i <= 24))
        {
            this.camere.get(id).ore.get((int) (24.0 - i)).add(new Inregistrare(timp, temperatura));
        }
    }

    public void adaugaInregistrareH(String id, long timp, double umiditate)
    {
        double i = ((this.ref - timp) / 3600.0);
        //System.out.println(i);
        if ((i >= 0) && (i <= 24))
        {
            this.camere.get(id).oreH.get((int) (24.0 - i)).add(new InregistrareH(timp, umiditate));
        }
    }

    public boolean TriggerH()
    {
        if (this.umiditateaDorita == -1)
        {
            return true;
        }
        double medie = 0;
        int suprafataCasei = 0;
        for (Map.Entry<String, ICam> entry : this.camere.entrySet())
        {
            ICam value = entry.getValue();
            int i = 23;
            while ((value.oreH.get(i).isEmpty()) && (i >= 0))
            {
                i--;
            }
            if (i < 0)
            {

            } else
            {
                suprafataCasei += (double) value.suprafata;
                medie += value.oreH.get(i).first().umiditate * (double) value.suprafata;
            }
        }
        medie = medie / suprafataCasei;
        if (medie > this.umiditateaDorita)
        {
            System.out.println(medie);
            return true;
        } else
        {
            System.out.println("NO");
            return false;
        }
    }

    /**
     *
     * Metoda citesteCasa care citeste din fisier primii cativa parametri si
     * initializeaza casa cu caracteristicile necesare ficarui test.
     */

    public void citesteCasa() throws FileNotFoundException
    {
        File f = new File("therm.in");
        Scanner s = new Scanner(f);
        s.next();
        s.next();
        File file = new File("therm.in");
        Scanner sc = new Scanner(file);
        if (s.nextDouble() < 1000)
        {
            this.nr = sc.nextInt();
            this.temperaturaDorita = sc.nextDouble();
            this.umiditateaDorita = sc.nextDouble();
        } else
        {
            this.nr = sc.nextInt();
            this.temperaturaDorita = sc.nextDouble();
        }

        this.ref = sc.nextLong();
        int i;
        for (i = 1; i <= this.nr; i++)
        {
            adaugaCamera(sc.next(), sc.next(), sc.nextInt());
        }
    }

    /**
     *
     * Metoda citesteComenzi continua sa citeasca fisierul de unde a ramas
     * metoda citesteCasa si in functie de primul cuvant de pe fiecare rand
     * decide ce comanda trebuie sa execute. Fiecare comanda este executata
     * conform cerintei. Singura comanda putin mai complicata este TRIGGER HEAT
     * pentru care trebuie sa alegem cea mai mica temperatura din ultima ora in
     * care se afla inregistrari, iar daca intr-o camera nu s-au facut
     * inregistrari sa fim atenti sa nu adaugam suprafata ei la media pe care o
     * vom calcula.
     */
    public void citesteComenzi() throws FileNotFoundException
    {
        File file = new File("therm.in");
        Scanner sc = new Scanner(file);
        while (sc.hasNext())
        {
            String comanda = sc.next();

            if ("OBSERVE".equals(comanda))
            {
                adaugaInregistrare(sc.next(), sc.nextLong(), sc.nextDouble());
            }
            if ("OBSERVEH".equals(comanda))
            {
                adaugaInregistrareH(sc.next(), sc.nextLong(), sc.nextDouble());
            }
            if ("TEMPERATURE".equals(comanda))
            {
                this.temperaturaDorita = sc.nextDouble();
            }
            if ("LIST".equals(comanda))
            {
                String camera = sc.next();
                System.out.print(camera);
                long start = sc.nextLong();
                long stop = sc.nextLong();
                int i;
                for (i = (int) (24.0 - ((this.ref - stop) / 3600.0)); i >= (int) 24.0 - ((this.ref - start) / 3600.0); i--)
                {
                    Iterator iterator = this.camere.get(numeId.get(camera)).ore.get(i).iterator();
                    while (iterator.hasNext())
                    {
                        Inregistrare inregistrare = (Inregistrare) iterator.next();
                        if ((inregistrare.timp < stop) && (inregistrare.timp > start))
                        {
                            System.out.print(" " + String.format("%.2f", inregistrare.temperatura));
                        }
                    }
                }
                System.out.println();
            }
            if ("TRIGGER".equals(comanda))
            {
                sc.next();
                if (TriggerH())
                {
                    double medie = 0;
                    int suprafataCasei = 0;
                    for (Map.Entry<String, ICam> entry : this.camere.entrySet())
                    {
                        ICam value = entry.getValue();
                        int i = 23;
                        while ((value.ore.get(i).isEmpty()) && (i >= 0))
                        {
                            i--;
                        }
                        if (i < 0)
                        {

                        } else
                        {
                            suprafataCasei += (double) value.suprafata;
                            medie += value.ore.get(i).first().temperatura * (double) value.suprafata;
                        }
                    }
                    medie = medie / suprafataCasei;
                    if (medie < this.temperaturaDorita)
                    {
                        System.out.println("YES");

                    } else
                    {
                        System.out.println("NO");
                    }
                }
            }
        }
    }

}
