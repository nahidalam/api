package com.lithouse.api.resource;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.lithouse.api.config.ApiCallerConstants;
import com.lithouse.api.exception.ApiException;
import com.lithouse.api.exception.ApiException.ErrorCode;
import com.lithouse.api.interceptor.BuildResponse;
import com.lithouse.api.response.DataBean;
import com.lithouse.api.util.RequestItem;
import com.lithouse.api.util.RequestLogger;
import com.lithouse.common.dao.DeviceDao;
import com.lithouse.common.model.DeviceItem;
import com.lithouse.common.model.GroupItem;


public class DevicesResource extends BaseResource < DeviceDao > {
			
	@Inject	
	public DevicesResource ( RequestItem requestItem,
					    	 RequestLogger requestLogger,
					    	 Provider < DeviceDao > daoProvider ) {
		super ( requestItem, requestLogger, daoProvider );
	}
	
	private int getRequestedDeviceCount ( String count ) throws ApiException {
		Integer deviceCount = convertNumber ( count, ApiCallerConstants.QueryParameters.count );
		
		if ( deviceCount == null || deviceCount <= 0 ) {
			throw new ApiException ( ErrorCode.InvalidInput, 
									 Arrays.asList ( ApiCallerConstants.QueryParameters.count ) );
		}
		
		
		return deviceCount;
	}
	
	@GET
	@BuildResponse
	public DataBean < DeviceItem > getDevices ( 
								@PathParam ( ApiCallerConstants.PathParameters.groupId ) 
								String groupId ) throws ApiException {
		
		if ( null == daoProvider.get ( ).find ( GroupItem.class, requestItem.getDeveloperId ( ), groupId ) ) {
			throw new ApiException ( ErrorCode.UnAuthenticated, 
									 "You do not have access to the devices in this group" );
		}
		
		logger.info ( "fetching devices from group: " 
				+ groupId + " for [developerId]::" + requestItem.getDeveloperId ( ) );
		
		try {
			return new DataBean < DeviceItem > ( 
	    				daoProvider.get ( ).getAllDevices ( groupId ) );
		} catch ( IllegalArgumentException e ) {
			throw new ApiException ( ErrorCode.InvalidInput, e.getMessage ( ) );
		}
	}
	
	@POST
	@BuildResponse
	public DataBean < DeviceItem > createDevices ( 
								@PathParam ( ApiCallerConstants.PathParameters.groupId ) 
								String groupId,
								@QueryParam ( ApiCallerConstants.QueryParameters.count )
								String count ) throws ApiException {
		int requestedDeviceCount = getRequestedDeviceCount ( count );
		
		logger.info ( "creating " + requestedDeviceCount 
						+ " devices for [developerId]::" + requestItem.getDeveloperId ( ) );
		
		try {
			return new DataBean < DeviceItem > ( 
	    				daoProvider.get ( ).createDevices ( 
	    						requestItem.getDeveloperId ( ), groupId, requestedDeviceCount ) );
		} catch ( IllegalArgumentException e ) {
			throw new ApiException ( ErrorCode.InvalidInput, e.getMessage ( ) );
		}
	}
}
