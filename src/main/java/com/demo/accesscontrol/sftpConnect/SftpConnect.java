package com.demo.accesscontrol.sftpConnect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

import com.demo.accesscontrol.common.util.AccessCtrlConstant;
import com.demo.accesscontrol.common.util.CommonMethods;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
@ComponentScan("com.demo.accesscontrol.sftpConnect")
public class SftpConnect {
	private static final Logger logger=LoggerFactory.getLogger(SftpConnect.class); 

	String remote_user_name=AccessCtrlConstant.remote_user_name;
	String remote_user_passwd=AccessCtrlConstant.remote_user_passwd;
	String remote_host_url=AccessCtrlConstant.remote_host_url;
	String qrFileUploadPath=AccessCtrlConstant.qrFileUploadPath;
	String qr_temp_file_path=AccessCtrlConstant.qr_temp_file_path;
	String aws_known_hosts_path=AccessCtrlConstant.aws_known_hosts_path;
	private SftpATTRS attrs = null;

	private ChannelSftp setupJsch(String remote_host_url, String remote_user_name,
			String remote_user_passwd,String aws_known_hosts_path) throws JSchException {
		JSch jsch = new JSch();
		jsch.setKnownHosts(aws_known_hosts_path);
		//jsch.addIdentity(aws_identity_pem_path);

		//Session jschSession = jsch.getSession(remote_user_name, remote_host_url);
		 Session jschSession = jsch.getSession(remote_user_name, remote_host_url);
		jschSession.setPassword(remote_user_passwd);
		jschSession.setConfig("StrictHostKeyChecking", "no");
		try {
			jschSession.connect();
		} catch (JSchException e) {
			
			//e.printStackTrace();
			logger.error("connection to AWS server failed");
		}
		return (ChannelSftp) jschSession.openChannel("sftp");
	}
	
	public void whenUploadFileUsingJsch_thenSuccess(String qrfilename) throws JSchException, SftpException {
		
		boolean isSrcFileExists = CommonMethods.sourceFileExistence(qr_temp_file_path);
		if (isSrcFileExists) {
			ChannelSftp channelSftp = null;
			try {
				channelSftp = setupJsch(remote_host_url, remote_user_name, remote_user_passwd,aws_known_hosts_path);
				channelSftp.connect();
				try {
					attrs = channelSftp.lstat(qrFileUploadPath);
				} catch (Exception e) {
					logger.info(qrFileUploadPath + " not found");
				}

				if (attrs != null) {
					logger.info("Directory " + (qrFileUploadPath) + "exists IsDir=" + attrs.isDir());
				} else {
					logger.info("Creating dir " + qrFileUploadPath);
					channelSftp.mkdir(qrFileUploadPath);
				}
				channelSftp.put(qr_temp_file_path + qrfilename, qrFileUploadPath);
				CommonMethods.deleteBackupStreamedFile(qr_temp_file_path,qrfilename);
			}
			catch (JSchException e) {
				logger.error("something went wrong while connection setup to remote server.");
				logger.error("connection to AWS server failed");
			}
			try {
				channelSftp.disconnect();
				channelSftp.exit();
				channelSftp.quit();

			} catch (Exception e) {
				//e.printStackTrace();
				logger.error("connection not found");
			}
		}
		else {
			logger.info("Source file is missing.");
		}
		
	//-------------------------------------------/
	}
}
