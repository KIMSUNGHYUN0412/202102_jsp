package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import kr.or.ddit.multipart.MultipartFile;
import kr.or.ddit.validate.constraints.FileMime;
import kr.or.ddit.validate.groups.DeleteGroup;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.validate.groups.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 상품관리 Domain Layer 
 *
 */

@Data
@EqualsAndHashCode(of="prodId")
@ToString(exclude="prodImage") 
public class ProdVO implements Serializable{
	@NotBlank(groups= UpdateGroup.class)
	private String prodId;
	@NotBlank
	private String prodName;
	@NotBlank(groups= InsertGroup.class)
	private String prodLgu;  
	private String lprodNm; 
	@NotBlank(groups= InsertGroup.class)
	private String prodBuyer; 
	//****
	private BuyerVO buyer; // has a 관계 => 1:1
	@NotNull
	@Min(value=0)
	private Integer prodCost;
	@NotNull  
	@Min(value=0) 
	private Integer prodPrice;
	@NotNull
	@Min(value=0)  
	private Integer prodSale;
	@NotBlank 
	private String prodOutline;
	private String prodDetail;
	@NotBlank(groups= InsertGroup.class)
	private String prodImg; // DB communication
	
	@NotNull(groups= InsertGroup.class)
	@FileMime(mime="image/")
	private transient MultipartFile prodImage; // Client communication
	public void setProdImage(MultipartFile prodImage) {
		if(prodImage!=null && !prodImage.isEmpty()) {
			this.prodImage = prodImage;
			this.prodImg = UUID.randomUUID().toString(); 
		}
	}
	 
	@NotNull
	@Min(value=0)  
	private Integer prodTotalstock;  

	private String prodInsdate;
	@NotNull
	@Min(value=0)  
	private Integer prodProperstock;
	private String prodSize;
	private String prodColor;
	private String prodDelivery; 
	private String prodUnit;
	@Min(value=0) 
	private Integer prodQtyin;
	@Min(value=0)  
	private Integer prodQtysale; 
	@Min(value=0)  
	private Integer prodMileage;
	
  	private List<MemberVO> memberList; //has many
}
