package ru.kubsu.lab.stand.utils;

public class StringUtilss {
    public static boolean hasDigits(String word){
        boolean has = false;
        for(int i = 0; i < word.length() && !has; i++) {
            if(Character.isDigit(word.charAt(i))) {
                has = true;
            }
        }
        return has;
    }
}
