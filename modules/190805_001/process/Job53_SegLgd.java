package process;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import entity.SegLgd;
import entity.SegLgdUd;
import util.HibernateUtil;

public class Job53_SegLgd {
	
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1. Select Data
		String query = "FROM SegLgdUd"
				+ "			WHERE baseYymm = :baseYymm";
		List<SegLgdUd> dataEntity = session.createQuery(query, SegLgdUd.class)
				.setParameter("baseYymm", bssd)
				.getResultList();
		
		//2. Insert Data
		int n = dataEntity.size();
		SegLgd resultEntity;
		for(int i=0; i<n; i++) {
			resultEntity = new SegLgd();
			resultEntity.setBaseYymm(dataEntity.get(i).getBaseYymm());
			resultEntity.setSegId(dataEntity.get(i).getSegId());
			resultEntity.setLgd(Math.round(dataEntity.get(i).getLgd()*1e6)/1e6);
			resultEntity.setLastModifiedBy("Job53");
			resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
			session.saveOrUpdate(resultEntity);
		}

		session.getTransaction().commit();
		session.close();
		System.out.println("개인 부도시 손실율 생성... 완료.");
		
	}

}
