package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author dragon.cl 2019/9/4 5:19 PM
 */
public class WafUtil {
    private static final byte[] KEY_BYTES
        = {17, (byte)129, 107, 48, 17, 117,81,(byte)177,44,(byte)136,9,1,24,(byte)180,60,18};

    private WafUtil() {}

    /**
     * WAF 下发到端的解密函数
     */
    public static String wafDecrypt(String plainText) {
        if (StringUtils.isEmpty(plainText)) {
            return null;
        }

        try {
            // 初始化Cipher
            Cipher cp = Cipher.getInstance("AES/ECB/Nopadding");
            SecretKeySpec key = new SecretKeySpec(KEY_BYTES, "AES");
            cp.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptBytes = Base64.decodeBase64(plainText);
            byte[] decValue = cp.doFinal(decryptBytes);
            return new String(decValue);
        } catch (Exception e) {
            return null;
        }
    }

}
