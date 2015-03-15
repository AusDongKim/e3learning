package e3learning.service;

import java.util.List;

import e3learning.domain.CourseVO;

public interface CourseService {

	boolean addCourse(CourseVO courseVO) throws Exception;

	List<CourseVO> getCourseList() throws Exception;

}
