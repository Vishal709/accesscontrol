package com.demo.accesscontrol.common.util;

import java.io.File;

public class CommonMethods {
	static boolean isFileExists = true; 
	public static boolean sourceFileExistence(String srcPath) {
	File directory = new File(srcPath);
    if (directory.isDirectory()) {
    	if(directory.list().length>0){
    		System.out.println("Source file exists");
		}
    	else {
    		System.out.println("Source directory is not empty!");
        	isFileExists = false;
        }    
    }
	return isFileExists;
	}
	
public static int deleteBackupStreamedFile(String backup_previous_file_path, String filename) {
	int isDeleted = 0;
	File file = new File(backup_previous_file_path+filename); 
    
    if(file.delete()) 
    { 
    	isDeleted=1;
        System.out.println("Deleted successfully backup streamed file."); 
    } 
    else
    { 
        System.out.println("Backup streamed file not deleted"); 
    }
	return isDeleted; 
}
public static  String getfileName() {
	String filename="";
	File dir = new File(AccessCtrlConstant.qr_temp_file_path);
	File[] files = dir.listFiles((dir1, name) -> name.endsWith(".png"));
	for(File file : files) {
		filename= file.getName();
	}
	return filename;
}
}
