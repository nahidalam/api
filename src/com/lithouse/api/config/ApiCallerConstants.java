package com.lithouse.api.config;

public class ApiCallerConstants {
	public class QueryParameters {
		public static final String apiKey = "apiKey";
	}
	
	public class FormParameters {
		public static final String messageText = "messageText";
	}
	
	public class Path {
		public static final String root = "/v1/*";
		public static final String developers = "/developers";
		public static final String ping = "/ping";
	}
	
	public class PathParameters {
		public static final String developerId = "developerId";
	}
}
