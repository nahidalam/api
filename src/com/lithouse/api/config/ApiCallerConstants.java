package com.lithouse.api.config;

public class ApiCallerConstants {
	public class QueryParameters {
		public static final String apiKey = "apiKey";
		public static final String count = "count";
	}
	
	public class FormParameters {
		public static final String messageText = "messageText";
	}
	
	public class Path {
		public static final String root = "/v1/*";
		public static final String developers = "/developers";
		public static final String devices = "/devices";
		public static final String deviceGroups = "/devicegroups";
		public static final String ping = "/ping";
	}
	
	public class PathParameters {
		public static final String developerId = "developerId";
		public static final String deviceGroupId = "deviceGroupId";
	}
}
