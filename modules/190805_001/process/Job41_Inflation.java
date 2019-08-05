package process;

import java.time.LocalDateTime;

import org.hibernate.Session;

import entity.Inflation;
import util.FinUtil;
import util.HibernateUtil;

public class Job41_Inflation {
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1. Select Data
		String query = "SELECT inflationIndex"
				+ "				FROM InflationUd"
				+ "				WHERE baseYymm = :baseYymm"
				+ "					AND indexId = 'CPI'";
		double index1 = (double) session.createQuery(query)
				.setParameter("baseYymm", bssd)
				.getSingleResult();
		double index0 = (double) session.createQuery(query)
				.setParameter("baseYymm", FinUtil.addMonth(bssd, -12))
				.getSingleResult();
		
		//2. Calculate Inflation
		double inflation = index1/index0-1;
		
		//3. Insert Data
		String indexId = "CPI";
		Inflation resultEntity;
		resultEntity = new Inflation();
		resultEntity.setBaseYymm(bssd);
		resultEntity.setIndexId(indexId);
		resultEntity.setInflation(inflation);
		resultEntity.setLastModifiedBy("Job52");
		resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
		session.saveOrUpdate(resultEntity);
		
		session.getTransaction().commit();
		session.close();
		System.out.println("인플레이션율 생성... 완료.");
		
	}
}
