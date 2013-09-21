package com.lithouse.api.resource;

import java.util.Arrays;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.lithouse.api.config.ApiCallerConstants;
import com.lithouse.api.exception.ApiException;
import com.lithouse.api.exception.ApiException.ErrorCode;
import com.lithouse.api.interceptor.Authenticate;
import com.lithouse.api.util.RequestItem;
import com.lithouse.api.util.RequestLogger;
import com.lithouse.common.dao.GenericDao;
import com.lithouse.common.util.Global;


@Path ( ApiCallerConstants.Path.developers )
public class DevelopersResource extends BaseResource {
			
	private Provider < DeveloperResource > developerProvider; 
	
	@Inject	
	public DevelopersResource ( RequestItem requestItem,
						    	RequestLogger requestLogger,
						    	Provider < GenericDao > daoProvider,
						    	Provider < DeveloperResource > developerProvider ) {
		super ( requestItem, requestLogger, daoProvider );
		this.developerProvider = developerProvider;		
	}
	
	
	@Authenticate
	@Path ( "/{" + ApiCallerConstants.PathParameters.developerId + "}" )	
	public DeveloperResource getDeveloperResource ( 
								@PathParam ( ApiCallerConstants.PathParameters.developerId ) 
								String developerId ) throws ApiException {
		
		if ( !Global.getAdminId ( ).equals ( requestItem.getDeveloperId ( ) ) ) {
			throw new ApiException ( ErrorCode.UnAuthorized,
	 				 		Arrays.asList ( ApiCallerConstants.QueryParameters.apiKey ) );
		} 
		logger.info ( "[developerId]::" + developerId );
		
		if ( developerId == null || developerId.isEmpty ( ) ) {
			throw new ApiException ( ErrorCode.InvalidInput,
				 		Arrays.asList ( ApiCallerConstants.PathParameters.developerId ) );
		}
		requestItem.setDeveloperId ( developerId );
		
	    return developerProvider.get ( );
	 }

}
