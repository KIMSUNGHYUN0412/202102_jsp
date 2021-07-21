package kr.or.ddit.member.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider.Service;
import java.util.Base64;
import java.util.List;
 
import kr.or.ddit.commons.UserNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.utils.EncryptUtils;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

public class MemberServiceImpl implements MemberService {
	private MemberDAO dao = MemberDAOImpl.getInstance();
	private AuthenticateService authService = new AuthenticateServiceImpl();
	//singleton
	private MemberServiceImpl() {}
	private static MemberServiceImpl self;
	public static MemberServiceImpl getInstance() {
		if(self==null) 
			self = new MemberServiceImpl();
		return self;
	} 
	
	
	@Override
	public ServiceResult createMember(MemberVO member) {
		MemberVO savedMember = dao.selectMemberById(member.getMemId());
		ServiceResult result = null;  
		if(savedMember==null) { 
			String plain = member.getMemPass();
			String encoded = EncryptUtils.encryptSha512Base64(plain);
			member.setMemPass(encoded); 
			int rowcnt = dao.insertMember(member);
			if(rowcnt > 0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
		}else {
			result = ServiceResult.PKDUPLICATED;
			 
		}
		return result;
	}
	
	@Override
	public int retrieveMemberCount(PagingVO<MemberVO> pagingVO) {
		return dao.selectTotalRecord(pagingVO); 
	}

	@Override
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO) {
		List<MemberVO> memberList = dao.selectMemberList(pagingVO);
		return memberList;
	} 

	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO member = dao.selectMemberDetail(mem_id);
		if(member==null) {
			throw new UserNotFoundException(String.format("%s 회원 없음", mem_id));
		}
		return member;
	}
 
	@Override
	public ServiceResult modifyMember(MemberVO member) {
		Object authResult =authService.authenticate(member);
		ServiceResult result = null;
		
		if(authResult instanceof MemberVO) {
			int rowcnt = dao.updateMember(member);
			if(rowcnt > 0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
		}else {
			result = (ServiceResult) authResult;
		} 
		return result; 
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		Object authResult = authService.authenticate(member);
		ServiceResult result = null;		
	
		if(authResult instanceof MemberVO) {
		 	int rowcnt = dao.deleteMember(member.getMemId());
		 	if(rowcnt > 0) {
		 		result = ServiceResult.OK;
		 	}else {
		 		result = ServiceResult.FAIL;
		 	}
		}else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		return result;
	}


	@Override
	public List<ZipVO> retrieveZipList() {
		return dao.searhZipList(); 
	}

}
