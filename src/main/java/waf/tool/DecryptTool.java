package waf.tool;import java.io.File;import java.io.FileInputStream;import util.IoUtils;import util.WafUtil;/** * @author dragon.cl 2019/9/4 5:13 PM */public class DecryptTool {    public static void main(String[] args) {        File wafEncrypt = new File("src/main/resources/encrypt");        try {            String encrypt = IoUtils.read2String(new FileInputStream(wafEncrypt));            String result = WafUtil.wafDecrypt(encrypt);            System.out.println(result);        } catch (Exception e) {            e.printStackTrace();        }    }}