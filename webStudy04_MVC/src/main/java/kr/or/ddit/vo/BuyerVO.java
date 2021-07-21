package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import kr.or.ddit.validate.constraints.TelNumber;
import kr.or.ddit.validate.groups.UpdateGroup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *	거래처 정보 Domain Layer 
 *
 */

@Data
@EqualsAndHashCode(of="buyerId")
public class BuyerVO implements Serializable{
	@NotBlank(groups= UpdateGroup.class)
	private String buyerId;
	@NotBlank
	private String buyerName;
	@NotBlank
	private String buyerLgu;
	
	private String lprodNm; 
	private String lprodname;
	
	private String buyerBank;
	private String buyerBankno;
	private String buyerBankname;
	private String buyerZip;
	private String buyerAdd1;
	private String buyerAdd2;
	@NotBlank
	@TelNumber
	private String buyerComtel;
	@TelNumber
	@NotBlank
	private String buyerFax; 
	@NotBlank
	@Email 
	private String buyerMail; 
	private String buyerCharger;
	@Size(max=2)  
	private String buyerTelext;
	
	// has many 1:N
	private List<ProdVO> prodList; 
}
