package id.kiosku.tcx.drivers;

import java.security.SecureRandom;

public class UtilityDriver {
    private static final String AN = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String N = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String A = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ANS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_-+={}[]<>,.?/";
    private static SecureRandom rnd = new SecureRandom();

    /**
     * @param length The length of generated String
     * @return Random String Alphabet, Number, and Symbol (Acceptable Symbols : ~!@#$%^&*()_-+={}[]<>,.?/)
     */
    public static String random(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(ANS.charAt(rnd.nextInt(ANS.length())));
        return sb.toString();
    }
    /**
     * @param length The length of generated String
     * @return Random String Alphabet, Number
     */
    public static String randomString(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(AN.charAt(rnd.nextInt(AN.length())));
        return sb.toString();
    }
    /**
     * @param length The length of generated String
     * @return Random String Alphabet
     */
    public static String randomAlphabet(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(A.charAt(rnd.nextInt(A.length())));
        return sb.toString();
    }
    /**
     * @param length The length of generated String
     * @return Random String Number
     */
    public static String randomNumber(int length){
        StringBuilder sb = new StringBuilder( length );
        for( int i = 0; i < length; i++ )
            sb.append(N.charAt(rnd.nextInt(N.length())));
        return sb.toString();
    }
}
