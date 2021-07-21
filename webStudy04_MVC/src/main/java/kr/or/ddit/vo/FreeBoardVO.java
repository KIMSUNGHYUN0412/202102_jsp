package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import kr.or.ddit.multipart.MultipartFile;
import kr.or.ddit.validate.groups.DeleteGroup;
import kr.or.ddit.validate.groups.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(of="boNo")
@ToString(exclude= {"boPass", "boFiles"})
public class FreeBoardVO implements Serializable{
	@NotNull(groups=UpdateGroup.class)
	private Integer boNo; 
	@NotBlank
	private String boTitle;
	@NotBlank
	private String boWriter;
	@NotBlank
	private String boIp;
	@Email
	private String boMail;
	@NotBlank
	private String boPass; 
	private String boContent;
	private String boDate;
	private Integer boRep;
	private Integer boHit;
	private Integer boRec;
	private Integer boParent;
	private String boDelete;
	
	private int rnum;

	//has many
	private MultipartFile[] boFiles;
	
	public void setBoFiles(MultipartFile[] boFiles) {
		if(boFiles==null) return;
		this.boFiles = boFiles;
		this.attatchList = new ArrayList<>(boFiles.length);
		for(MultipartFile file : boFiles) {
			if(file.isEmpty()) continue; 
			this.attatchList.add(new AttatchVO(file));
		}
	}
	//attatch insert
	private List<AttatchVO> attatchList;
	
	//attatch delete
	private int[] delAttNos;
	
	private List<FreeReplyVO> replyList;
	
	private int startAttNo;
}
