package kr.or.ddit.validate.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kr.or.ddit.multipart.MultipartFile;

public class FileMimeValidator implements ConstraintValidator<FileMime, MultipartFile>{
	private FileMime annotation;

	@Override
	public void initialize(FileMime constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if(value==null || value.isEmpty()) return true;
		String mime = annotation.mime();
		String contentType = value.getContentType();
		return contentType!=null && contentType.startsWith(mime);
	}
	
}
