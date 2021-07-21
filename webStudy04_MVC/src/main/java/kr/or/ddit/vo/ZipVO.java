package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data 
@EqualsAndHashCode(of="seq")
public class ZipVO implements Serializable{
	private String zipcode;
	private String sido;
	private String gugun;
	private String dong;
	private String ri;
	private String bldg;
	private String bunji; 
	private Integer seq;
	
}
