package com.lithouse.api.resource;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;
import com.lithouse.api.util.RequestItem;
import com.lithouse.api.util.RequestLogger;
import com.lithouse.common.dao.GenericDao;


@RequestScoped
@Produces ( MediaType.APPLICATION_JSON )
public abstract class BaseResource {
	protected final RequestItem requestItem;
	protected final RequestLogger logger;
	protected final Provider < GenericDao > daoProvider;

	public BaseResource ( RequestItem requestItem,
						  RequestLogger logger,
						  Provider < GenericDao > daoProvider ) {
		this.requestItem = requestItem;
		this.logger = logger;
		this.daoProvider = daoProvider;
	}
}
