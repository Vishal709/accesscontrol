package com.demo.accesscontrol.common.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Address;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.ReplaceIfPresentFlag;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.SubmitMultiResult;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;

public class SmsSender {
		private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();
	 
	    private final String smppIp = "127.0.0.1";
	 
	    private int port = 8086;
	 
	    private final String username = "localhost";
	 
	    private final String password = "#j709ghost";
	 
	    private final String address = "AX-DEV";
	 
	    private static final String SERVICE_TYPE = "CMT";
	 
	    public void broadcastMessage(String message, List<String> numbers) {
	        System.out.println("Broadcasting sms");
	        SubmitMultiResult result = null;
	        Address[] addresses = prepareAddress(numbers);
	        SMPPSession session = initSession();
	        if(session != null) {
	            try {
	                result = session.submitMultiple(SERVICE_TYPE, TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN, address,
	                        addresses, new ESMClass(), (byte) 0, (byte) 1, TIME_FORMATTER.format(new Date()), null,
	                        new RegisteredDelivery(SMSCDeliveryReceipt.FAILURE), ReplaceIfPresentFlag.REPLACE,
	                        new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0,
	                        message.getBytes());
	 
	                System.out.println("Messages submitted, result is {} "+ result);
	                Thread.sleep(1000);
	            } catch (PDUException e) {
	            	System.out.println("Invalid PDU parameter"+ e);
	            } catch (ResponseTimeoutException e) {
	            	System.out.println("Response timeout "+ e);
	            } catch (InvalidResponseException e) {
	            	System.out.println("Receive invalid response "+ e);
	            } catch (NegativeResponseException e) {
	            	System.out.println("Receive negative response "+ e);
	            } catch (IOException e) {
	            	System.out.println("I/O error occured "+ e);
	            } catch (Exception e) {
	            	System.out.println("Exception occured submitting SMPP request "+ e);
	            }
	        }else {
	        	System.out.println("Session creation failed with SMPP broker.");
	        }
	        if(result != null && result.getUnsuccessDeliveries() != null && result.getUnsuccessDeliveries().length > 0) {
	        	System.out.println(DeliveryReceiptState.getDescription(result.getUnsuccessDeliveries()[0].getErrorStatusCode()).description() + " - " +result.getMessageId());
	        }else {
	        	System.out.println("Pushed message to broker successfully");
	        }
	        if(session != null) {
	            session.unbindAndClose();
	        }
	    }
	 
	    private Address[] prepareAddress(List<String> numbers) {
	        Address[] addresses = new Address[numbers.size()];
	        for(int i = 0; i< numbers.size(); i++){
	            addresses[i] = new Address(TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN, numbers.get(i));
	        }
	        return addresses;
	    }
	 
	    private SMPPSession initSession() {
	        SMPPSession session = new SMPPSession();
	        try {
	            session.setMessageReceiverListener(new MessageReceiverListenerImpl());
	            String systemId = session.connectAndBind(smppIp, Integer.valueOf(port), new BindParameter(BindType.BIND_TX, username, password, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
	            System.out.println("Connected with SMPP with system id {} "+ systemId);
	        } catch (IOException e) {
	        	System.out.println("I/O error occured "+ e);
	            session = null;
	        }
	        return session;
	    }
	 
	    public static void main(String[] args) {
	    	SmsSender multiSubmit = new SmsSender();
	        multiSubmit.broadcastMessage("Test message from devglan", Arrays.asList("8840083736"));
	    }
	
}
