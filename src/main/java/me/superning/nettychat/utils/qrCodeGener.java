package me.superning.nettychat.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;
import org.apache.commons.io.output.ByteArrayOutputStream;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

@Data
public class qrCodeGener {
    private static int wigth = 300;
    private static int height = 300;
    private static String content = "https://superopenai.github.io/";
    private static String format = "jpg";


    public ByteArrayOutputStream qdcodeGengerator(String username) throws IOException, WriterException {


        content = username;


        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, wigth, height, hints);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,format,byteArrayOutputStream);
            System.out.println(byteArrayOutputStream.size());


              return  byteArrayOutputStream;

    }

    public static void main(String[] args) {
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, wigth, height, hints);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,format,byteArrayOutputStream);
            System.out.println(byteArrayOutputStream.size());
            byte[] bytes = byteArrayOutputStream.toByteArray();
            File image = new File("F:\\qrcode.jpg");

            FileOutputStream fileOutputStream = new FileOutputStream(image);

            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();





        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }




        //最后使用imageIO类的write方法绘制二维码
            //            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            //            Graphics2D graphics = bufferedImage.createGraphics();
            //            ImageIO.write(bufferedImage,"png",new File("E:\\qrcode.png"));


            //使用zxing的MatrixToImageWriter.writeToPath方法来向指定位置绘制一张二维码
            //            Path path = new File("D:\\qrcode.jpg").toPath();
            //            MatrixToImageWriter.writeToPath(bitMatrix,format,path);




    }


}
