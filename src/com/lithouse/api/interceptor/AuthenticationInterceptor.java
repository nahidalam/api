package com.lithouse.api.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;


import org.aopalliance.intercept.MethodInvocation;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.lithouse.api.config.ApiCallerConstants;
import com.lithouse.api.exception.ApiException;
import com.lithouse.api.exception.ApiException.ErrorCode;
import com.lithouse.api.util.RequestItem;
import com.lithouse.api.util.RequestLogger;
import com.lithouse.common.dao.GenericDao;
import com.lithouse.common.model.ApiKeyItem;

public class AuthenticationInterceptor extends BaseInterceptor {
	private final Provider < HttpServletRequest > servletRequestProvider;
	private final Provider < GenericDao > daoProvider;

	@Inject
	public AuthenticationInterceptor ( Provider < RequestItem > requestItemProvider,
	 								   Provider < RequestLogger > loggerProvider,
	 								   Provider < HttpServletRequest > servletRequestProvider,
	 								   Provider < GenericDao > daoProvider ) {	
		super ( requestItemProvider, loggerProvider);
		
		this.servletRequestProvider = servletRequestProvider;
		this.daoProvider = daoProvider;
	}
	
	@Override
	public Object invoke ( MethodInvocation invocation, 
						   String methodName,
						   RequestItem requestItem, 
						   RequestLogger logger ) throws Throwable {
		
		logger.info ( "@Authenticate::" + methodName );
		requestItem.setDeveloperId ( getDeveloperId ( servletRequestProvider.get ( ) ) );
		logger.info ( "[callerId]::" + requestItem.getDeveloperId ( ) );
		
		return invocation.proceed();
	}
	
	private String getDeveloperId ( HttpServletRequest request ) throws ApiException {		
		String apiKey = request.getParameter ( ApiCallerConstants.QueryParameters.apiKey );
		
		if ( apiKey == null ) {
			throw new ApiException ( ErrorCode.UnAuthenticated,
									 Arrays.asList( ApiCallerConstants.QueryParameters.apiKey ) );					 
		}

		ApiKeyItem apiKeyItem = daoProvider.get ( ).find ( ApiKeyItem.class, apiKey );
		
		if ( apiKeyItem == null ) {
			throw new ApiException ( ErrorCode.UnAuthenticated,
					 				 Arrays.asList( ApiCallerConstants.QueryParameters.apiKey ) );
		}
		
		return apiKeyItem.getDeveloperId ( );
	}
}
