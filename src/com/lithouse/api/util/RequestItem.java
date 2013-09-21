package com.lithouse.api.util;




import com.google.inject.servlet.RequestScoped;
import com.lithouse.common.util.Global;

@RequestScoped
public class RequestItem {
	
	private long startTime;
	private String requestName;
	private String requestId;
	public String developerId;
	
	public RequestItem ( ) {
		requestId = Global.generateUniqueName ( );		
		this.requestName = "[" + requestId + "] " + "::";
		startTime = System.currentTimeMillis();	
	}

	public long getStartTime ( ) {
		return startTime;
	}

	public String getRequestName ( ) {
		return requestName;
	}

	public String getRequestId ( ) {
		return requestId;
	}

	public void setDeveloperId ( String developerId ) {
		this.developerId = developerId;
	}
	
	public String getDeveloperId ( ) {
		return developerId;
	}
}
