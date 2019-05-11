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
public class Name {

    public static String capitaliseName(String name) {
        char seperator = ' ';
        String names[] = name.split(" ");
        if (names.length == 1) {
            names = name.split("-");
            if (names.length > 1) {
                seperator = '-';
            }
        }
        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            newName.append(Character.toUpperCase(names[i].charAt(0)));
            newName.append(names[i].substring(1).toLowerCase());
            if (i < names.length-1)
                newName.append(seperator);
        }

        return newName.toString();
    }
}
