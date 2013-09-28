package com.lithouse.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.lithouse.api.config.ApiCallerConstants;
import com.lithouse.api.exception.ApiException;
import com.lithouse.api.exception.ApiException.ErrorCode;
import com.lithouse.api.interceptor.Authenticate;
import com.lithouse.api.interceptor.BuildResponse;
import com.lithouse.api.response.DataBean;
import com.lithouse.api.util.RequestItem;
import com.lithouse.api.util.RequestLogger;
import com.lithouse.common.dao.DeviceGroupDao;
import com.lithouse.common.model.DeviceGroupItem;


@Path ( ApiCallerConstants.Path.deviceGroups )
public class DeviceGroupsResource extends BaseResource < DeviceGroupDao > {
			
	//private Provider < DeveloperResource > developerProvider; 
	
	@Inject	
	public DeviceGroupsResource ( RequestItem requestItem,
						    	  RequestLogger requestLogger,
						    	  Provider < DeviceGroupDao > daoProvider ) {
						    	//Provider < DeveloperResource > developerProvider ) {
		super ( requestItem, requestLogger, daoProvider );
		//this.developerProvider = developerProvider;		
	}
	
	@Authenticate
	@GET 
	@BuildResponse
	public DataBean < DeviceGroupItem > getAllDeviceGroupsByDeveloperId ( ) {
		logger.info ( "fetching groups by [developerId]::" + requestItem.getDeveloperId ( ) );
		return new DataBean < DeviceGroupItem > ( 
						daoProvider.get ( ).getAllDeviceGroups ( requestItem.getDeveloperId ( ) ) );
	}  
	
//	@Authenticate
//	@Path ( "/{" + ApiCallerConstants.PathParameters.developerId + "}" )	
//	public DeveloperResource getDeveloperResource ( 
//								@PathParam ( ApiCallerConstants.PathParameters.developerId ) 
//								String developerId ) throws ApiException {
//		
//		verifyAdmin ( );
//		
//		if ( developerId == null || developerId.isEmpty ( ) ) {
//			throw new ApiException ( ErrorCode.InvalidInput,
//				 		Arrays.asList ( ApiCallerConstants.PathParameters.developerId ) );
//		}
//		
//		logger.info ( "[developerId]::" + developerId );
//		requestItem.setDeveloperId ( developerId );
//		
//	    return developerProvider.get ( );
//	}
	
	@Authenticate
	@POST
	@Consumes ( MediaType.APPLICATION_JSON )
	public DeviceGroupItem createDeviceGroup ( DeviceGroupItem deviceGroupItem ) throws ApiException {				
		deviceGroupItem.setDeveloperId ( requestItem.getDeveloperId ( ) );
		logger.info ( "creating group " + deviceGroupItem.getGroupName ( ) 
				+ " for [developerId]::" + requestItem.getDeveloperId ( ) );
		
		try { 
			return daoProvider.get ( ).createDeviceGroup ( deviceGroupItem );
		} catch ( IllegalArgumentException e ) {
			throw new ApiException ( ErrorCode.InvalidInput, e. getMessage ( ) );
		}
	}
	
}
