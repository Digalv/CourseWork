package coursework;

public class Utilities {
    public static int positiveNumber(int num){
        return (num > 0) ? num : -1;
    }
    public static double positiveNumber(double num){
        return (num > 0) ? num : -1;
    }
    public static boolean correctWordCase(String string){
        char[] chars = string.toCharArray();
        String temp = "";
        for (int i = 0; i < chars.length; i++) {
            if(i==0){
                temp += chars[i];
                if(temp.equals(temp.toUpperCase())){
                    temp = "";
                }
                else{
                    return false;
                }
            }
            else{
                temp += chars[i];
                if(temp.equals(temp.toLowerCase())){
                    temp = "";
                }
                else{
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean onlyLetterInString(String string){
        return string.matches("[\\p{L}| ]+");
    }
    public static String removeWhitespace(String string){
        return string.trim().replaceAll(" +", " ");
    }
}
