/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inregistrare;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * In clasa ICam(InformatiiCamera) stochez suprafata camerei si un ArrayList de
 * lungime 24 de SortedSet-uri de clase Inregistrare creat pentru a modela
 * faptul ca avem 24 de intervale de cate o ora in care stocam in mod ordonat
 * inregistrari facute de senzor.
 */
public class ICam
{

    int suprafata;
    ArrayList<SortedSet<Inregistrare>> ore;
    ArrayList<SortedSet<InregistrareH>> oreH;

    public ICam(int suprafata)
    {
        this.suprafata = suprafata;
        this.ore = new ArrayList<SortedSet<Inregistrare>>(24);
        int i;
        for (i = 0; i <= 23; i++)
        {
            SortedSet<Inregistrare> l = new TreeSet<>();
            this.ore.add(l);
        }
        this.oreH = new ArrayList<SortedSet<InregistrareH>>(24);
        for (i = 0; i <= 23; i++)
        {
            SortedSet<InregistrareH> l = new TreeSet<>();
            this.oreH.add(l);
        }
    }

}
