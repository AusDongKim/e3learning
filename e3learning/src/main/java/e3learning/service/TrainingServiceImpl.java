package e3learning.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e3learning.dao.TrainingDao;
import e3learning.domain.TrainingVO;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	TrainingDao trainingDao;
	
	@Override
	public boolean isCourseAlreadyTaken(int idAccount, int idCourse) {
		return trainingDao.isAlreadyTakenTraining(idAccount, idCourse);
	}

	@Override
	@Transactional
	public boolean enrolTraining(TrainingVO trainingVO) {
		trainingDao.saveOrUpdate(trainingVO);
		if(trainingVO.getIdTraining()>0) {
			return true;
		}
		return false;
	}

	@Override
	public void updateTrainingInfo(TrainingVO trainingVO) {
		trainingDao.merge(trainingVO);
	}

	@Override
	public List<TrainingVO> getTrainingsByIdAccount(int idAccount) {
		return trainingDao.getTrainingsbyAccount(idAccount);
		
	}
}
