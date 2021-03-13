/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inregistrare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * In clasa Inregistrare grupez caracteristicile ce definesc o inregistrare
 * facuta de senzor: timpul la care este realizata inregistrarea si temperatura
 * inregistrata.
 */
public class Inregistrare implements Comparable
{

    long timp;
    double temperatura;

    public Inregistrare(long timp, double temperatura)
    {
        this.timp = timp;
        this.temperatura = temperatura;
    }

    @Override
    /**
     *
     *
     * specifica setului sa sorteze elementele dupa temperatura in mod crescator
     */
    public int compareTo(Object inregistrare)
    {
        return Double.compare(this.temperatura, ((Inregistrare) inregistrare).temperatura);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        // TODO code application logic here
        PrintStream o = new PrintStream(new File("./therm.out"));
        System.setOut(o);
        Casa casa = new Casa();
        casa.citesteCasa();
        casa.citesteComenzi();
    }

}
