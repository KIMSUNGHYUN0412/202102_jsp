package kr.or.ddit.enumtype;

public enum OSType {
	DOS("도스"), WINDOWS("윈도우"),  MAC("맥 OS"), LINUX("리눅스"), UNIX("유닉스");
	private String osText;
	 
	private OSType(String osText) {
		this.osText = osText; 
	} 


	public String getOsText() {
		return this.osText;
	}
	 
	public static String findOSType(String userAgent) {
		OSType findedOs = OSType.UNIX;
		if(userAgent!=null) {
			userAgent = userAgent.toUpperCase();
			for(OSType tmp : values()) {
				if(userAgent.indexOf(tmp.name()) >= 0) {
					findedOs = tmp;
					break;
				}
			}
		}
		return findedOs.getOsText(); 
	}
	
}
