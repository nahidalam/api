package com.lithouse.api.resource;

import java.util.Arrays;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.lithouse.api.config.ApiCallerConstants;
import com.lithouse.api.exception.ApiException;
import com.lithouse.api.exception.ApiException.ErrorCode;
import com.lithouse.api.interceptor.Authenticate;
import com.lithouse.api.response.DataBean;
import com.lithouse.api.util.RequestItem;
import com.lithouse.api.util.RequestLogger;
import com.lithouse.common.dao.DeviceDao;
import com.lithouse.common.model.DeviceItem;


@Path ( ApiCallerConstants.Path.devices 
		+ "/{" + ApiCallerConstants.PathParameters.deviceGroupId + "}" )
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
	
	@Authenticate
	@POST
	public DataBean < DeviceItem > createDevices ( 
								@PathParam ( ApiCallerConstants.PathParameters.deviceGroupId ) 
								String deviceGroupId,
								@QueryParam ( ApiCallerConstants.QueryParameters.count )
								String count ) throws ApiException {
		int requestedDeviceCount = getRequestedDeviceCount ( count );
		
		logger.info ( "creating " + requestedDeviceCount 
						+ " devices for [developerId]::" + requestItem.getDeveloperId ( ) );
		
		try {
			return new DataBean < DeviceItem > ( 
	    				daoProvider.get ( ).createDevices ( 
	    						requestItem.getDeveloperId ( ), deviceGroupId, requestedDeviceCount ) );
		} catch ( IllegalArgumentException e ) {
			throw new ApiException ( ErrorCode.InvalidInput, e.getMessage ( ) );
		}
	}
}
