package id.kiosku.tcx.drivers;

import android.util.Log;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import id.kiosku.tcx.exceptions.InvalidMacCryptoException;
import id.kiosku.tcx.variables.CryptoData;

public class CryptoDriver {
    public static class AES {
        private static AES anInstance;
        private String key;
        private byte[] keyBytes;

        public static AES getInstance() {
            return anInstance;
        }
        public static void init(){
            anInstance = new AES();
        }
        public static void init(String key){
            anInstance = new AES(key);
        }

        public AES(){
            this(MD5(UtilityDriver.randomNumber(32)));
        }

        public AES(String key){
            this.key = MD5(key);
            try {
                this.keyBytes = this.key.getBytes("UTF-8");
            }catch (Exception e){
                Log.e("CryptoDriver@construct",e.getMessage());
            }
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public byte[] getKeyBytes() {
            return keyBytes;
        }

        public CryptoData encrypt(String message){
            return encrypt(message,UtilityDriver.random(16));
        }
        public CryptoData encrypt(String message, String iv){
            CryptoData result = new CryptoData();
            result.iv = Base64.encode(iv.getBytes());
            try{
                byte[] ivBytes = iv.getBytes("UTF-8");
                IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                SecretKeySpec keySpec = new SecretKeySpec(this.keyBytes, "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));

                result.cipher = Base64.encode(encrypted);
                result.mac = HMAC.generate(result.iv+result.cipher,this.key);
            }catch (Exception e){
                Log.e("CryptoDriver@encrypt",""+e.getMessage());
                result = null;
            }
            return result;
        }

        public String decrypt(String cipher){
            return decrypt(CryptoData.fromString(cipher));
        }
        public String decrypt(CryptoData data){
            return decrypt(data.cipher,data.iv,data.mac);
        }
        public String decrypt(String value,String iv,String mac){
            String result;
            try{
                if(HMAC.generate(iv+value,this.key).equals(mac)) {
                    byte[] ivBytes = Base64.decode(iv);
                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                    SecretKeySpec keySpec = new SecretKeySpec(this.keyBytes, "AES");

                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                    byte[] decrypted = cipher.doFinal(Base64.decode(value));

                    result = new String(decrypted,"UTF-8");
                }else{
                    throw new InvalidMacCryptoException();
                }
            }catch (Exception e){
                Log.e("CryptoDriver@encrypt",""+e.getMessage());
                result = null;
            }
            return result;
        }
    }

    public static class Base64 {
        /**
         * encode
         *
         * coverts a byte array to a string populated with base64 digits. It steps
         * through the byte array calling a helper method for each block of three
         * input bytes
         *
         * @param raw
         *            The byte array to encode
         * @return A string in base64 encoding
         */
        public static String encode(byte[] raw) {
            StringBuffer encoded = new StringBuffer();
            for (int i = 0; i < raw.length; i += 3) {
                encoded.append(encodeBlock(raw, i));
            }
            return encoded.toString();
        }

        /*
         * encodeBlock
         *
         * creates 4 base64 digits from three bytes of input data. we use an
         * integer, block, to hold the 24 bits of input data.
         *
         * @return An array of 4 characters
         */
        protected static char[] encodeBlock(byte[] raw, int offset) {
            int block = 0;
            // how much space left in input byte array
            int slack = raw.length - offset - 1;
            // if there are fewer than 3 bytes in this block, calculate end
            int end = (slack >= 2) ? 2 : slack;
            // convert signed quantities into unsigned
            for (int i = 0; i <= end; i++) {
                byte b = raw[offset + i];
                int neuter = (b < 0) ? b + 256 : b;
                block += neuter << (8 * (2 - i));
            }

            // extract the base64 digits, which are six bit quantities.
            char[] base64 = new char[4];
            for (int i = 0; i < 4; i++) {
                int sixbit = (block >>> (6 * (3 - i))) & 0x3f;
                base64[i] = getChar(sixbit);
            }
            // pad return block if needed
            if (slack < 1)
                base64[2] = '=';
            if (slack < 2)
                base64[3] = '=';
            // always returns an array of 4 characters
            return base64;
        }

        /*
         * getChar
         *
         * encapsulates the translation from six bit quantity to base64 digit
         */
        protected static char getChar(int sixBit) {
            if (sixBit >= 0 && sixBit <= 25)
                return (char) ('A' + sixBit);
            if (sixBit >= 26 && sixBit <= 51)
                return (char) ('a' + (sixBit - 26));
            if (sixBit >= 52 && sixBit <= 61)
                return (char) ('0' + (sixBit - 52));
            if (sixBit == 62)
                return '+';
            if (sixBit == 63)
                return '/';
            return '?';
        }

        /**
         * decode
         *
         * convert a base64 string into an array of bytes.
         *
         * @param base64
         *            A String of base64 digits to decode.
         * @return A byte array containing the decoded value of the base64 input
         *         string
         */
        public static byte[] decode(String base64) {
            // how many padding digits?
            int pad = 0;
            for (int i = base64.length() - 1; base64.charAt(i) == '='; i--)
                pad++;
            // we know know the length of the target byte array.
            int length = base64.length() * 6 / 8 - pad;
            byte[] raw = new byte[length];
            int rawIndex = 0;
            // loop through the base64 value. A correctly formed
            // base64 string always has a multiple of 4 characters.
            for (int i = 0; i < base64.length(); i += 4) {
                int block = (getValue(base64.charAt(i)) << 18)
                        + (getValue(base64.charAt(i + 1)) << 12)
                        + (getValue(base64.charAt(i + 2)) << 6)
                        + (getValue(base64.charAt(i + 3)));
                // based on the block, the byte array is filled with the
                // appropriate 8 bit values
                for (int j = 0; j < 3 && rawIndex + j < raw.length; j++)
                    raw[rawIndex + j] = (byte) ((block >> (8 * (2 - j))) & 0xff);
                rawIndex += 3;
            }
            return raw;
        }

        /*
         * getValue
         *
         * translates from base64 digits to their 6 bit value
         */
        protected static int getValue(char c) {
            if (c >= 'A' && c <= 'Z')
                return c - 'A';
            if (c >= 'a' && c <= 'z')
                return c - 'a' + 26;
            if (c >= '0' && c <= '9')
                return c - '0' + 52;
            if (c == '+')
                return 62;
            if (c == '/')
                return 63;
            if (c == '=')
                return 0;
            return -1;
        }
    }

    public static class HMAC {
        private static String HMAC_SHA256 = "HmacSHA256";
        public static String generate(String msg, String keyString){
            return generate(HMAC_SHA256,msg,keyString);
        }
        public static String generate(String algo, String msg, String keyString) {
            String digest = null;
            try {
                SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
                Mac mac = Mac.getInstance(algo);
                mac.init(key);

                byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

                digest = bytesToHex(bytes);
            } catch (Exception e) {
                Log.e("CryptoDriver.HMAC",""+e.getMessage());
            }
            return digest;
        }
    }

    public static String bytesToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] textBytes = text.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] sha1hash = md.digest();
            return bytesToHex(sha1hash);
        }catch (Exception e){
            return null;
        }
    }
    public static String MD5(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] textBytes = text.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] md5hash = md.digest();
            return bytesToHex(md5hash);
        }catch (Exception e){
            return null;
        }
    }
}
