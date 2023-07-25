package space.xorex.jmhdemo.UDFFunc;

public class UDF {
    public static String unprefix(String str) {
        if(str.startsWith("prefix")) return str.substring(6);
        else return str;
    }

    public static String prefix(String str) {
        if(str.startsWith("prefix")) return str;
        else return "prefix" + str;
    }

    public static void main(String[] args) {
        int i = 0;
        long startTime = System.currentTimeMillis();
        while (true){
            i++;
            if (i==100000000) {
                i = 0;
                if (System.currentTimeMillis() - startTime >= 10000){
                    return;
                }
            }
        }
    }
}
