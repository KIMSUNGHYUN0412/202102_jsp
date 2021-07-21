package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import kr.or.ddit.board.service.FreeBoardService;
import kr.or.ddit.board.service.FreeBoardServiceImpl;
import kr.or.ddit.enumtype.BrowserType;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.AttatchVO;

@Controller
public class FreeBoardDownloadController {

	private FreeBoardService service = FreeBoardServiceImpl.getInstance();
	private File saveFolder = new File("d:/saveFiles");
	private static final String DISPOSITION = "Content-Disposition";
	
	@RequestMapping("/board/download.do")
	public String fileDownload(
			@RequestParam("what") int attNo
			, HttpServletRequest req
			, HttpServletResponse resp
			) throws IOException  {
		
		AttatchVO attatch = service.download(attNo);
		File saveFile = new File(saveFolder, attatch.getAttSavename());
		if(!saveFile.exists()) {
			resp.sendError(404);
		}else { 
			String userAgent = req.getHeader("User-Agent");
			BrowserType browserType = BrowserType.findBrowserType(userAgent);
			String filename = attatch.getAttFilename(); 
			
			switch (browserType) {
			case MSIE:
			case TRIDENT:
				filename = URLEncoder.encode(filename, "UTF-8");
				filename = filename.replace("+", " ");  
				break;

			default:
				byte[] bytes = filename.getBytes();
				filename = new String(bytes, "ISO-8859-1");
				break;
			} 
			
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-length", attatch.getAttFilesize() + "");
		
			resp.setHeader(DISPOSITION, "attachment;filename=\""+ filename + "\""); 
			try(
				OutputStream os = resp.getOutputStream(); 
			){
				FileUtils.copyFile(saveFile, os);
			}
		}
		return null;
	}
}
