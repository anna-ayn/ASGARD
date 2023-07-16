public class StringUtils {
    public static String stringTab(String s, int tab, boolean sub) {
        if (tab == 0 || sub) {
            return s;
        } else {
            return String.format("%" + (tab * 2) + "s%s", "", s);
        }
    }
}