package kr.or.ddit.emp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.emp.dao.EmployeeDAO;
import kr.or.ddit.emp.dao.EmployeeDAOImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.EmployeeWrapper;

public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeServiceImpl() {}
	private static EmployeeServiceImpl self;
	public static EmployeeServiceImpl getInstance() {
		if(self==null)
			self = new EmployeeServiceImpl();
		return self;
	}
	
	private EmployeeDAO empDAO = EmployeeDAOImpl.getInstance();
	
	@Override
	public ServiceResult createEmployee(EmployeeVO employee) {
		int cnt = empDAO.updateEmployee(employee);
		return cnt > 0 ?  ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public List<EmployeeWrapper> retrieveEmployeeList(Map<String, Object> pMap) {
		List<EmployeeVO> empList = empDAO.selectEmployeeList(pMap);
		List<EmployeeWrapper> wrapperList = new ArrayList<>();
		empList.forEach((employee)->{ wrapperList.add(new EmployeeWrapper(employee)); });
		return wrapperList;
	}

	@Override
	public EmployeeVO retrieveEmployee(int empno) {
		EmployeeVO vo =  empDAO.selectEmployee(empno);
		if(vo == null) throw new DataNotFoundException(empno+"");
		return vo;
	}

	@Override
	public ServiceResult modifyEmployee(EmployeeVO employee) {
		if(empDAO.selectEmployee(employee.getEmpno())==null)
			throw new DataNotFoundException(employee.getEmpno()+"");
		int cnt = empDAO.updateEmployee(employee);
		return cnt > 0 ?  ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult removeEmployee(int empno) {
		if(empDAO.selectEmployee(empno)==null)
			throw new DataNotFoundException(empno+"");
		int cnt = empDAO.deleteEmployee(empno);
		return cnt > 0 ?  ServiceResult.OK : ServiceResult.FAIL;
	}

}
