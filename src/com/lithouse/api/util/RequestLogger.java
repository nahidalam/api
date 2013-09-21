package com.lithouse.api.util;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class RequestLogger {	
	private final Logger logger = Logger.getLogger( "RequestLogger" );
	private final Provider < RequestItem > requestItemProvider;
	
	@Inject
	public RequestLogger ( Provider < RequestItem > requestItemProvider ) {
		this.requestItemProvider = requestItemProvider;  
	}
	
	public void logRequestComplete () {
		RequestItem currentRequest = requestItemProvider.get();
		logger.info( currentRequest.getRequestName() 
				+ "[end]::Time = " 
				+ ( System.currentTimeMillis() - currentRequest.getStartTime() ) 
				+ " ms" );		
	}
		
	public void info ( String message ) {		
		RequestItem currentRequest = requestItemProvider.get();
		logger.info( currentRequest.getRequestName() + message );
	}
	
	public void info ( String message,  Throwable t ) {
		RequestItem currentRequest = requestItemProvider.get();
		logger.info( currentRequest.getRequestName() + message, t );
	}
	
	public void debug ( String message ) {
		RequestItem currentRequest = requestItemProvider.get();
		logger.debug( currentRequest.getRequestName() + message );
	}
	
	public void error ( String message,  Throwable t ) {
		RequestItem currentRequest = requestItemProvider.get();
		logger.error( currentRequest.getRequestName() + message, t );
	}
	
	public void error ( String message ) {
		RequestItem currentRequest = requestItemProvider.get();
		logger.error( currentRequest.getRequestName() + message );
	}
	
}
