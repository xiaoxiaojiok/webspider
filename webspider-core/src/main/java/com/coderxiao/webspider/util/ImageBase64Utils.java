package com.coderxiao.webspider.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageBase64Utils {
	/**
	 * 
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @param data
	 * @return
	 */
	public static String GetImageStr(byte[] data) {
		 
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
 
	/**
	 * 
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param path 目录
	 * @param filename 文件
	 * @param imgStr 图片字符串
	 * @return
	 */
    public static boolean GenerateImage(String path, String filename, String imgStr) {
        if (imgStr == null) //图像数据为空
        {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成图片
            String imgFilePath = path + filename;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println("e:" + e.getMessage());
            return false;
        }
    }
}
