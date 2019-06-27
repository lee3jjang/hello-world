package process;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;

import entity.DiscRateCalcSetting;
import entity.DiscRateHis;
import entity.DiscRateStats;
import entity.IrCurveHis;
import util.FinUtil;
import util.HibernateUtil;

public class Job31_DiscRateStatsInternal {
	
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		String maturity  = "M0060";
		int avgTerm = 24;
		
		//1-1. Select Data (Interest Rates)
		String query1 = "SELECT MAX(baseDate)"
				+ "				FROM IrCurveHis"
				+ "				WHERE baseDate <= :baseYymm"
				+ "					AND irCurveId = 'A100'"
				+ "				GROUP BY SUBSTR(baseDate, 0, 7)";
		List<String> lastDates = session.createQuery(query1, String.class)
				.setParameter("baseYymm", FinUtil.addMonth(bssd, 1))
				.getResultList();
		
		String query2 = "FROM IrCurveHis"
				+ "				WHERE baseDate IN :lastDates"
				+ "				AND irCurveId = 'A100'"
				+ "				AND matCd = :matCd";
		List<IrCurveHis> dataEntity1 = session.createQuery(query2, IrCurveHis.class)
				.setParameter("lastDates", lastDates)
				.setParameter("matCd", maturity)
				.getResultList();
		dataEntity1 = dataEntity1.stream().peek(x -> x.setBaseDate(x.getBaseDate().substring(0,  6))).collect(Collectors.toList());
		
		//1-2. Select Data (Settings)
		String query3 = "FROM DiscRateCalcSetting";
		List<DiscRateCalcSetting> dataEntity2 = session.createQuery(query3, DiscRateCalcSetting.class)
				.getResultList();
		
		//1-3. Select Data (Disclosure Rates)
		String query4 = "FROM DiscRateHis";
		List<DiscRateHis> dataEntity3 = session.createQuery(query4, DiscRateHis.class)
				.getResultList();
		
		//2. Insert Data (Non-Calculable Disclosure Rates)
		List<String> nonCalculableDiscRatesCd = dataEntity2.stream()
				.filter(x -> !x.getDiscRateCalcTyp().equals("01") && !x.getDiscRateCalcTyp().equals("02"))
				.map(x -> x.getIntRateCd())
				.collect(Collectors.toList());
		DiscRateStats resultEntity;
		for(String discRateCd : nonCalculableDiscRatesCd) {
			resultEntity = new DiscRateStats();
			resultEntity.setBaseYymm(bssd);
			resultEntity.setDiscRateCalcTyp("I");
			resultEntity.setIntRateCd(discRateCd);
			resultEntity.setIndepnVariable(maturity);
			resultEntity.setDepnVariable("BASE_DISC");
			resultEntity.setRegrCoef(0.);
			resultEntity.setRegrConstant(dataEntity3.stream()
					.filter(x -> x.getBaseYymm().equals(bssd))
					.filter(x -> x.getIntRateCd().equals(discRateCd))
					.map(x -> x.getApplDiscRate())
					.findFirst().orElse(0.));
			resultEntity.setLastModifiedBy("Job31");
			resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
			session.saveOrUpdate(resultEntity);
		}
		
		
		List<String> calculableDiscRatesCd = dataEntity2.stream()
				.filter(x -> x.getDiscRateCalcTyp().equals("01") || x.getDiscRateCalcTyp().equals("02"))
				.map(x -> x.getIntRateCd())
				.collect(Collectors.toList());
		for(String discRateCd : calculableDiscRatesCd) {
			List<DiscRateHis> discRateHis = dataEntity3.stream()
					.filter(x -> x.getIntRateCd().equals(discRateCd))
					.sorted((x, y) -> x.getBaseYymm().compareTo(y.getBaseYymm()))
					.collect(Collectors.toList());
			
			System.out.println(discRateHis);
			
		}
		
		session.getTransaction().commit();
		
		session.close();
		
	}

}
