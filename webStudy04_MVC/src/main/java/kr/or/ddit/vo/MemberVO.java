package kr.or.ddit.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.apache.commons.codec.binary.Base64;

import kr.or.ddit.multipart.MultipartFile;
import kr.or.ddit.validate.constraints.FileMime;
import kr.or.ddit.validate.constraints.TelNumber;
import kr.or.ddit.validate.groups.DeleteGroup;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.validate.groups.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 회원관리를 위한 Domain Layer 
 * MyBatis (DataMapper, ORM) 을 이용한 테이블 조인 방법
 * ex) 회원 정보 상세 조회시 구매 상품 목록 동시 조회
 * 1. 메인 테이블 (MEMBER) 를 기준으로 조인할 대상이 되는 테이블과의 관계 파악.
 * 	 MEMBER(1) : PROD(N)
 * 2. 각 테이블의  스키마 구조에 따라 VO 설계
 * 	 MemberVO, ProdVO
 * 3. VO 사이에 테이블간의 관계성 반영
 * 	 1 : N - has many 관계
 * 		ex) MemberVO has many ProdVO
 * 	 1 : 1 - has a 관계 
 * 		ex) ProdVO has a BuyerVO
 * 4. resultType 대신 resultMap 으로 수동 바인딩 설정
 * 	 1 : N - has many -> collection
 * 	 1 : 1 - has a -> association
 *
 */ 
@Data
@EqualsAndHashCode(of="memId") 
@ToString(exclude= {"memPass", "memImg", "memImage", "memRegno1", "memRegno2"})
@AllArgsConstructor
@Builder 
public class MemberVO implements Serializable, HttpSessionBindingListener{
	 
	public MemberVO() {
		super();
	}  

	public MemberVO(String memId, String memPass) {
		super();
		this.memId = memId;
		this.memPass = memPass;
	} 

	@NotBlank(groups= {Default.class, DeleteGroup.class})
	private String memId;
	@NotBlank(groups= {Default.class, DeleteGroup.class})
	@Size(min=4, max=12, groups= {Default.class, DeleteGroup.class})
	private String memPass;
	@NotBlank
	private String memName;
	@NotBlank(groups=InsertGroup.class)
	private String memRegno1;
	@NotBlank(groups=InsertGroup.class)
	private String memRegno2; 
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}")
	private String memBir; 
	private String memZip;
	private String memAdd1; 
	private String memAdd2;
	@TelNumber
	private String memHometel;
	@TelNumber
	private String memComtel;
	@NotBlank
	@TelNumber
	private String memHp;
	@NotBlank
	@Email 
	private String memMail;
	private String memJob;
	private String memLike;
	private String memMemorial;
	private String memMemorialday;
	private Integer memMileage;
	private Boolean memDelete;
	
	private Set<ProdVO> prodList; //has many 관계 => 1:N
	
	public String getMemTest() {
		return "테스트 데이터";
	}
	
	private String memRole;
	
	private byte[] memImg;
	
	public String getBase64Img() {
		if(memImg==null) return null;
		return Base64.encodeBase64String(memImg);
	}
	  
	@FileMime(mime="image/")
	private transient MultipartFile memImage;
	
	public void setMemImage(MultipartFile memImage) throws IOException {
		if(memImage==null || memImage.isEmpty()) return;
		this.memImage = memImage; 
		this.memImg = memImage.getBytes();
	}
	
	
	
	
	
	
	
	
	
	
//	HttpSessionBindingListener
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		String attrName = event.getName();
		if("authMember".equals(attrName)) {
			ServletContext application = event.getSession().getServletContext();
			Map<String, MemberVO> currentUserList = (Map)application.getAttribute("currentUserList");
			currentUserList.put(memId, this);
		}
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		String attrName = event.getName();
		if("authMember".equals(attrName)) {
			ServletContext application = event.getSession().getServletContext();
			Map<String, MemberVO> currentUserList = (Map)application.getAttribute("currentUserList");
			currentUserList.remove(memId);
		}
	}
}
