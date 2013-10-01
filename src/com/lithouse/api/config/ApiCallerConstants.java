package com.lithouse.api.config;

public class ApiCallerConstants {
	public class QueryParameters {
		public static final String apiKey = "apiKey";
		public static final String count = "count";
		public static final String groupId = "groupId";
	}
	
	public class FormParameters {
		public static final String messageText = "messageText";
	}
	
	public class Path {
		public static final String root = "/v1/*";
		public static final String developers = "/developers";
		public static final String devices = "devices";
		public static final String groups = "/groups";
		public static final String apps = "/apps";
		public static final String ping = "/ping";
		public static final String permessions = "permessions";
	}
	
	public class PathParameters {
		public static final String developerId = "developerId";
		public static final String appId = "appId";
		public static final String groupId = "groupId";
	}
}
