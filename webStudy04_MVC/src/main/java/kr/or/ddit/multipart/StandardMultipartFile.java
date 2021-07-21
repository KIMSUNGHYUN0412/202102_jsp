package kr.or.ddit.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

public class StandardMultipartFile implements MultipartFile{
	private static final String DISPOSITION = "Content-Disposition";
	private final Part adaptee;

	public StandardMultipartFile(Part adaptee) {
		super();
		this.adaptee = adaptee;
	}
	
	private String extractFileName(String disposition) {
		// form-data; name="filePart", filename=""; asdf
		if(disposition==null || disposition.isEmpty()) 
			return null;
		int first = disposition.indexOf("filename=");
		String filename = null;
		if(first != -1) {
			int end = disposition.indexOf(";", first);
			if(end == -1) {
				filename = disposition.substring(first + "filename=".length()); 
			}else {
				filename = disposition.substring(first + "filename=".length(), end); 
			}
			filename = filename.replace("\"", "");
		}  
		return filename;
	}
	
	@Override
	public String getOriginalFilename() {
		String disposition = this.adaptee.getHeader(DISPOSITION);
		return extractFileName(disposition);
	}

	@Override
	public boolean isEmpty() {
		return this.adaptee.getSize()==0;
	}

	@Override
	public String getName() {
		return this.adaptee.getName();
	}

	@Override
	public String getContentType() {
		return this.adaptee.getContentType();
	}

	@Override
	public long getSize() {
		return this.adaptee.getSize();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.adaptee.getInputStream();
	}

	@Override
	public byte[] getBytes() throws IOException {
		return IOUtils.toByteArray(getInputStream());
	}

	@Override
	public void transferTo(File saveFile) throws IOException {
		this.adaptee.write(saveFile.getPath()); 
	}
	
	
}
