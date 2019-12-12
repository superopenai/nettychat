package me.superning.nettychat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

import javax.xml.parsers.SAXParser;

public class SHAUtils {

	/**
	 * @Description: 对字符串进行md5加密 
	 */
	public static String getSHA512(String strValue) throws Exception {
		String salt ="dqqovnnbqutpwn";
		String sha512Hex = DigestUtils.sha512Hex(strValue+ salt);
		System.out.println(sha512Hex.toString());

//		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return Base64.encodeBase64String((sha512Hex+salt).getBytes());
	}

	public static void main(String[] args) {
		try {
			String sha512 = getSHA512("liuxining13455673843");
			System.out.println(sha512);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
