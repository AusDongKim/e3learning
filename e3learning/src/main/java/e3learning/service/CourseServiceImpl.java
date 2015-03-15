package e3learning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import e3learning.dao.CourseDao;
import e3learning.domain.CourseVO;

@Service
@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseDao courseDao;
	
	@Override
	public boolean addCourse(CourseVO courseVO) throws Exception{
		courseDao.saveOrUpdate(courseVO);
		if(courseVO.getIdCourse()>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<CourseVO> getCourseList() throws Exception {
		return courseDao.findAllCourse();
	}
}
