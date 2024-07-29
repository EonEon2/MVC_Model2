package org.example.w2.common;

public class StringUtil {

    public static int getInt(String str, int defaultValue){

        if(str == null || str.length() == 0) { //URL에서 가져온 pageStr의 값은 문자열 '21' 이므로 정수로 변경해야함.
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        }catch (Exception e) {
            return defaultValue;
        }
    }
}
