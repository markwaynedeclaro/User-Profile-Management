package com.mycompany.usermanagement.util;

public class Common {


	public static enum ADDRESS_TYPE {
		HOME("home"), OFFICE("office");

		private String addressType;

		ADDRESS_TYPE(String addressType) {
			this.addressType = addressType;
		}

		public String getValue() {
			return this.addressType;
		}
	}
	
	
	
}
