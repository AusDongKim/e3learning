package e3learning.dao;

import java.util.List;

import e3learning.domain.CourseVO;

public interface CourseDao {

	void saveOrUpdate(CourseVO courseVO);

	List<CourseVO> findAllCourse();

	Long findByCourseTitle(String string);

}
