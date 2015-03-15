package e3learning.dao;

import java.util.List;

import e3learning.domain.TrainingVO;

public interface TrainingDao {

	void saveOrUpdate(TrainingVO trainingVO);

	TrainingVO merge(TrainingVO trainingVO);

	TrainingVO findOne(TrainingVO training2vo);

	boolean isAlreadyTakenTraining(int accountId, int courseId);

	TrainingVO findTrainingDetail(int accountId, int courseId);

	List<TrainingVO> getTrainingsbyAccount(int idAccount);



}
