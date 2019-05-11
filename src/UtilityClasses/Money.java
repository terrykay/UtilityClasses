/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityClasses;

/**
 *
 * @author tezk
 */
public class Money {
    public static String penceToString(int p) {
        /**
         * @args p - amount in pence
         * returns String pound representation of the amount p in pence
         */
        StringBuilder aString = new StringBuilder();
        aString.append(p/100);
        aString.append(".");
        int pLeft = p-((int)(p/100) * 100);
        if (pLeft<10)
            aString.append("0");
        aString.append(pLeft);
        return aString.toString();
    }
    
    public static int stringToPence(String pounds) throws NumberFormatException {
        /**
         * @args pounds
         * Converts String pound representation to integer pence
         */
        if (pounds==null || "".equals(pounds))
            return 0;
        pounds.replace("£", "");
        int pence = (int)(Float.parseFloat(pounds)*100);
        return pence;
    }
}
