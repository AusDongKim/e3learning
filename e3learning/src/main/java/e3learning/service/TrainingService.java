package e3learning.service;

import java.util.List;

import e3learning.domain.TrainingVO;

public interface TrainingService {

	boolean isCourseAlreadyTaken(int idAccount, int idCourse);

	boolean enrolTraining(TrainingVO training2vo);

	void updateTrainingInfo(TrainingVO training2vo);

	List<TrainingVO> getTrainingsByIdAccount(int idAccount);

}
