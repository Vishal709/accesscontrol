/*===========================
FileName: QRGenerationUtil.java
Author:Tushar Mahajan
History:
Date:Jul 21, 2020:created
============================*/

/*===========================
FileName: QRGenerationUtil.java
Author:Tushar Mahajan
History:
Date:Jul 21, 2020:created
============================*/

package com.demo.accesscontrol.common.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.accesscontrol.sftpConnect.SftpConnect;

/**
 * @author Tushar mahajan
 *
 */
public class QRGenerationUtil {
//	@Autowired
//	SftpConnect sftpConnect;
//	@Autowired
//	CommonMethods commonMethods;
	private static final Logger logger=LoggerFactory.getLogger(QRGenerationUtil.class); 
	public static String generateQR(Map<String,String> paramMap) {
		SftpConnect sftpConnect= new SftpConnect();
		String qr="";
		String qrFileRoletype="";
		
		if(!paramMap.isEmpty() && null!= paramMap.get("admin"))
		{
			qrFileRoletype="Admin_Qr";
			qr = paramMap.get("admin");
			try {
				QrCodeGenerator.generateQRCodeImage(paramMap.get("admin"),qrFileRoletype);
				try {
					sftpConnect.whenUploadFileUsingJsch_thenSuccess(CommonMethods.getfileName());
					logger.info("Qr file uploaded to server.");

				} catch (Exception e) {
					logger.error("Something went wrong while connecting to server.");
					e.printStackTrace();
				}
			} catch (Exception e) {
				logger.error("Qr code generation failed.");
				//e.printStackTrace();
			}
		}
		if(!paramMap.isEmpty() && null!= paramMap.get("emp")) {
			qr=paramMap.get("emp");
			qrFileRoletype="Emp_Qr";
			try {
				QrCodeGenerator.generateQRCodeImage(paramMap.get("emp"), qrFileRoletype);
				try {
					sftpConnect.whenUploadFileUsingJsch_thenSuccess(CommonMethods.getfileName());
					logger.info("Qr file uploaded to server.");
				
				} catch (Exception e) {
					logger.error("Something went wrong while connecting to server.");
					//e.printStackTrace();
				}
			} catch (Exception e) {
				logger.error("Qr code generation failed.");
				//e.printStackTrace();
			}
			
		}
			
		if(!paramMap.isEmpty() && null!= paramMap.get("visitor")) {
			qr=paramMap.get("visitor");
			qrFileRoletype="Visitor_Qr";
			try {
				QrCodeGenerator.generateQRCodeImage(paramMap.get("visitor"),qrFileRoletype);
				try {
					sftpConnect.whenUploadFileUsingJsch_thenSuccess(CommonMethods.getfileName());
					logger.info("Qr file uploaded to server.");
				} catch (Exception e) {
					logger.error("Something went wrong while connecting to server.");
					//e.printStackTrace();
				}
			} catch (Exception e) {
				logger.error("Qr code generation failed.");
				//e.printStackTrace();
			}
		}
		return qr;
	}
	
}
