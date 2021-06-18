package kr.or.ddit.enumtype;

public enum MimeType {
	JSON("application/json;charset=UTF-8"), 
	SCRIPT("text/javacript"), 
	PLAIN("text/plain;charset=UTF-8"), 
	HTML("text/html;charset=UTF-8"); 
	private String mimeText;
	
	private MimeType(String mimeText) {
		this.mimeText = mimeText;
	}

	public String getMimeText() {
		return this.mimeText;
	}
	
	public static MimeType findMimeType(String accept) {
		MimeType finded = MimeType.HTML; 
		if(accept!=null) { 
			accept = accept.toUpperCase(); 
			for(MimeType tmp : values()) { 
				if(accept.indexOf(tmp.name()) >= 0) {
					finded = tmp; 
					break;
				} 
			}   
		}
		return finded;
		 
		
	}
	public static String findMimeText(String accept) {
		return findMimeType(accept).getMimeText();
	}
}
