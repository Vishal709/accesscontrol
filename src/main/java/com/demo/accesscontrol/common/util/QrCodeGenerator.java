package com.demo.accesscontrol.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import net.glxn.qrgen.javase.QRCode;

public class QrCodeGenerator {
	public static boolean generateQRCodeImage(String barcodeText,String qrFileRoletype) throws Exception {
	    ByteArrayOutputStream stream = QRCode
	      .from(barcodeText)
	      .withSize(250, 250)
	      .stream();
	    ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
	    BufferedImage image=ImageIO.read(bis);
	    return ImageIO.write(image,"png",new File(AccessCtrlConstant.qr_temp_file_path+qrFileRoletype+".png"));
	     

	}
}
