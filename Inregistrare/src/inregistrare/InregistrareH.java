/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inregistrare;

/**
 *
 * @author Rares
 */
public class InregistrareH implements Comparable
{

    long timp;
    double umiditate;

    public InregistrareH(long timp, double temperatura)
    {
        this.timp = timp;
        this.umiditate = temperatura;
    }

    public int compareTo(Object inregistrareH)
    {
        return Double.compare(this.umiditate, ((InregistrareH) inregistrareH).umiditate);

    }
}
