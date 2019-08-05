package process;

import org.hibernate.Session;

import util.HibernateUtil;

public class Job54_SegPrepay {
	
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.getTransaction().commit();
		session.close();
		System.out.println("조기상환율 생성... 완료.");
		
	}

}
