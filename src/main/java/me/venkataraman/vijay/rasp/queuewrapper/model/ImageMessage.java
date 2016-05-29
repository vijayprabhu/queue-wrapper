package me.venkataraman.vijay.rasp.queuewrapper.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ImageMessage implements Serializable{
	
	private String messageID;
	private String messageDateTime;
	private byte[] messageStream;
	private long messageStreamLength;
	
	public ImageMessage() {}
	
	public ImageMessage(String messageID, String messageDateTime, byte[] messageStream, long messageStreamLength) {
		
		this.messageID = messageID;		
		this.messageDateTime = messageDateTime;
		this.messageStream = messageStream;
		this.messageStreamLength = messageStreamLength;
	}
	
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public String getMessageDateTime() {
		return messageDateTime;
	}
	public void setMessageDateTime(String messageDateTime) {
		this.messageDateTime = messageDateTime;
	}
	public byte[] getMessageStream() {
		return messageStream;
	}
	public void setMessageStream(byte[] messageStream) {
		this.messageStream = messageStream;
	}
	public long getMessageStreamLength() {
		return messageStreamLength;
	}
	public void setMessageStreamLength(long messageStreamLength) {
		this.messageStreamLength = messageStreamLength;
	}
	
	
}
