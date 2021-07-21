package kr.or.ddit.validate;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.internal.metadata.aggregated.ValidatableParametersMetaData;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.DeleteGroup;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.validate.groups.UpdateGroup;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;

public class MemberVOValidateTest {
	private static Validator validator;
	private static final Logger logger = LoggerFactory.getLogger(MemberVOValidateTest.class);
	 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	

//	@Test
	public void test() {
		MemberVO target = MemberVO.builder().build();
		
		Set<ConstraintViolation<MemberVO>> violations = validator.validate(target);
		for(ConstraintViolation<MemberVO> singleViolation : violations) {
			String key = singleViolation.getPropertyPath().toString();
			String message = singleViolation.getMessage();
			logger.info("{} : {}", key, message);
		}
	}
	
//	@Test
	public void testValidatorUtils() {
		MemberVO target = MemberVO.builder()
//						.memId("a001")
//						.memPass("ab")
//						.memMail("asdf@naver.") 
//						.memBir("20010211")
						.memHp("123-4567-4567")
						.memComtel("010-7894-4512")
						.memHometel("02-456-7874")
						.build();
		ValidatorUtils<MemberVO> utils = new ValidatorUtils<>();
		Map<String, List<String>> errors = new HashMap<>();
		boolean valid = utils.validate(target, errors, InsertGroup.class);
		if(!valid) { 
			for(Entry<String, List<String>> entry : errors.entrySet()) {
				logger.info("{} : {} ", entry.getKey(), entry.getValue()); 
			}
		}
	} 

	@Test
	public void testProdValid() {
		ProdVO target = new ProdVO();
		ValidatorUtils<ProdVO> utils = new ValidatorUtils<>();
		Map<String, List<String>> errors = new HashMap<>();
		boolean valid = utils.validate(target, errors, UpdateGroup.class);
		if(!valid) {
			for(Entry<String, List<String>> entry : errors.entrySet()) {
				logger.info("{} : {}" , entry.getKey(), entry.getValue());
			}
		}
	}
	
}
