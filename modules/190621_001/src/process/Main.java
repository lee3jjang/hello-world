package process;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.hibernate.Session;

import entity.IrCurveHis;
import util.HibernateUtil;

public class Main {
	
	private static Session session;

	public static void main(String[] args) {
		//session = HibernateUtil.getSessionFactory().openSession();
		
		//session.beginTransaction();
		//List<IrCurveHis> curveHis = session.createQuery("FROM IrCurveHis").getResultList();
		Map<Object, Boolean> map = new HashMap<>();
		//System.out.println(curveHis);
		//session.getTransaction().commit();
		Function<String, Integer> f = str -> Integer.parseInt(str);
		System.out.println(f.apply("42"));
	}
}
