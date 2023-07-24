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
}
