package com.tnnowu.android.generalmethod.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Tnno Wu on 2017/04/28.
 */

public class FileSaveUtil {

    public static final String SDCARD_PATH =
            Environment.getExternalStorageDirectory().toString() + "/WTH/";

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(File file) {
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 获取文件夹下的所有文件名
     */
    public static List<String> getFileName(String fileName) {
        List<String> fileList = new ArrayList<String>();
        String path = fileName; // 路径
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return null;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (!fs.isDirectory()) {
                fileList.add(fs.getName());
            }
        }
        return fileList;
    }

    /**
     * 在SD卡上创建文件
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!isFileExists(file))
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.createNewFile();
            }
        return file;
    }

    /**
     * 在SD卡上创建文件夹
     */
    public static File createSDDirectory(String fileName) throws IOException {
        File file = new File(fileName);
        if (!isFileExists(file))
            file.mkdirs();
        return file;
    }

    /**
     *
     */
    public synchronized static boolean writeBytes(String filePath, byte[] data, boolean isAppend) {
        try {
            FileOutputStream fos;
            if (isAppend)
                fos = new FileOutputStream(filePath, true);
            else
                fos = new FileOutputStream(filePath);
            fos.write(data);
            fos.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 读取SD卡中文本文件
     */
    public synchronized static String readSDFile(String fileName) {
        StringBuffer sb = new StringBuffer();
        File f1 = new File(fileName);
        String str = null;
        try {
            InputStream is = new FileInputStream(f1);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(input);
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        // 如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        // 遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // 删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } else {
                // 删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前空目录
        return dirFile.delete();
    }

    /**
     * Save Bitmap
     */
    public static boolean saveBitmap(Bitmap bm, String picName) {
        try {
            File f = new File(picName);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 把文件转换成base64
     */
    public static String encodeBase64File(String path) throws Exception {
        byte[] videoBytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        @SuppressWarnings("resource")
        FileInputStream fis = new FileInputStream(new File(path));
        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = fis.read(buf)))
            baos.write(buf, 0, n);
        videoBytes = baos.toByteArray();
        return Base64.encodeToString(videoBytes, Base64.NO_WRAP);
    }

    /**
     * 根据相册媒体库路径转换成sd卡路径
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isOverKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isOverKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /***********************************************************************************************
     *
     *                                            MD5
     *
     * *********************************************************************************************
     */

    /**
     * 十六进制
     */
    public static String getMessageDigest(byte[] buffer) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(Algorithm.MD5.getType());
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getRawDigest(byte[] buffer) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(Algorithm.MD5.getType());
            mdTemp.update(buffer);
            return mdTemp.digest();

        } catch (Exception e) {
            return null;
        }
    }


    private static String getMD5(final InputStream is, final int bufLen) {
        if (is == null || bufLen <= 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(Algorithm.MD5.getType());
            StringBuilder md5Str = new StringBuilder(32);

            byte[] buf = new byte[bufLen];
            int readCount = 0;
            while ((readCount = is.read(buf)) != -1) {
                md.update(buf, 0, readCount);
            }

            byte[] hashValue = md.digest();

            for (int i = 0; i < hashValue.length; i++) {
                md5Str.append(Integer.toString((hashValue[i] & 0xff) + 0x100, 16).substring(1));
            }
            return md5Str.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对文件进行md5
     */
    public static String getMD5(final String filePath) {
        if (filePath == null) {
            return null;
        }

        File f = new File(filePath);
        if (f.exists()) {
            return getMD5(f, 1024 * 100);
        }
        return null;
    }

    /**
     * 文件md5
     */
    public static String getMD5(final File file) {
        return getMD5(file, 1024 * 100);
    }


    private static String getMD5(final File file, final int bufLen) {
        if (file == null || bufLen <= 0 || !file.exists()) {
            return null;
        }

        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            String md5 = getMD5(fin, (int) (bufLen <= file.length() ? bufLen : file.length()));
            fin.close();
            return md5;

        } catch (Exception e) {
            return null;

        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /***********************************************************************************************
     *
     *                                  SHA
     *
     ***********************************************************************************************
     */
    public static byte[] encrypt(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(Algorithm.SHA.getType());
        sha.update(data);
        return sha.digest();
    }

    /***********************************************************************************************
     *
     *                                  MAC
     *
     * *********************************************************************************************
     */

    /**
     * 初始化HMAC密钥
     *
     * @param algorithm 算法，可为空。默认为：Algorithm.Hmac_MD5
     * @return
     * @throws Exception
     */
    public static String initMacKey(Algorithm algorithm) throws Exception {
        if (algorithm == null) algorithm = Algorithm.Hmac_MD5;
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getType());
        SecretKey secretKey = keyGenerator.generateKey();

        return encodeToString(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param plain     明文
     * @param key       key
     * @param algorithm 算法，可为空。默认为：Algorithm.Hmac_MD5
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plain, String key, Algorithm algorithm) throws Exception {
        if (algorithm == null) algorithm = Algorithm.Hmac_MD5;
        SecretKey secretKey = new SecretKeySpec(decode(key), algorithm.getType());
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(plain);
    }


    /**********************************  DES  start   *********************************************/
    /**
     * 转换秘钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm.DES.getType());
        return keyFactory.generateSecret(dks);
    }

    /**
     * 解密
     *
     * @param plain
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] plain, String key) throws Exception {
        Key k = toKey(decode(key));

        Cipher cipher = Cipher.getInstance(Algorithm.DES.getType());
        cipher.init(Cipher.DECRYPT_MODE, k);

        return cipher.doFinal(plain);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decode(key));
        Cipher cipher = Cipher.getInstance(Algorithm.DES.getType());
        cipher.init(Cipher.ENCRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     */
    public static String initKey() throws Exception {
        return initKey(null);
    }

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;

        if (seed != null) {
            secureRandom = new SecureRandom(decode(seed));
        } else {
            secureRandom = new SecureRandom();
        }

        KeyGenerator kg = KeyGenerator.getInstance(Algorithm.DES.getType());
        kg.init(secureRandom);

        SecretKey secretKey = kg.generateKey();

        return encodeToString(secretKey.getEncoded());
    }
    /**********************************  DES  end  ************************************************/


    /**********************************  RSA  start ***********************************************/
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";


    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = decode(privateKey);        // 解密由base64编码的私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   // 构造PKCS8EncodedKeySpec对象
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());    // KEY_ALGORITHM 指定的加密算法
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);        // 取私钥匙对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);   // 用私钥对信息生成数字签名
        signature.initSign(priKey);
        signature.update(data);

        return encodeToString(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {

        byte[] keyBytes = decode(publicKey); // 解密由base64编码的公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  // 构造X509EncodedKeySpec对象
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());  // KEY_ALGORITHM 指定的加密算法
        PublicKey pubKey = keyFactory.generatePublic(keySpec);   // 取公钥对象

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(decode(sign));
    }

    /**
     * 用私钥解密
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = decode(key);   // 对密钥解密

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   // 取得私钥
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = decode(key);       // 对密钥解密

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = decode(key);   // 对公钥解密

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥加密
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decode(key);   // 对密钥解密

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encodeToString(key.getEncoded());
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encodeToString(key.getEncoded());
    }

    /**
     * 初始化密钥
     */
    public static Map<String, Object> initKeys() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(Algorithm.RSA.getType());
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();    // 公钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();     // 私钥
        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**********************************  RSA  end *************************************************/


    @SuppressLint("NewApi")
    public static String getDataColumn(
            Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public enum Algorithm {
        SHA("SHA"),
        MD5("MD5"),
        Hmac_MD5("HmacMD5"),
        Hmac_SHA1("HmacSHA1"),
        Hmac_SHA256("HmacSHA256"),
        Hmac_SHA384("HmacSHA384"),
        Hmac_SHA512("HmacSHA512"),
        DES("DES"),
        RSA("RSA");

        private String type;

        Algorithm(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public static byte[] encode(byte[] plain) {
        return Base64.encode(plain, Base64.DEFAULT);
    }

    public static String encodeToString(byte[] plain) {
        return Base64.encodeToString(plain, Base64.DEFAULT);
    }

    public static byte[] decode(String text) {
        return Base64.decode(text, Base64.DEFAULT);
    }

    public static byte[] decode(byte[] text) {
        return Base64.decode(text, Base64.DEFAULT);
    }

}
