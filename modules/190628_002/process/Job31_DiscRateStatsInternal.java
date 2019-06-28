package process;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;

import entity.DiscRateCalcSetting;
import entity.DiscRateHis;
import entity.DiscRateStats;
import entity.IrCurveHis;
import esgcore.Matrix;
import esgcore.SimpleLinearRegression;
import esgcore.StringMatrix;
import esgcore.Vector;
import util.FinUtil;
import util.HibernateUtil;

public class Job31_DiscRateStatsInternal {
	
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1-1. Select Data (Interest Rates)
		String query1 = "SELECT MAX(baseDate)"
				+ "				FROM IrCurveHis"
				+ "				WHERE baseDate >= :startYymm"
				+ "					AND baseDate <= :endYymm"
				+ "					AND irCurveId = 'A100'"
				+ "				GROUP BY SUBSTR(baseDate, 0, 7)";
		List<String> lastDates = session.createQuery(query1, String.class)
				.setParameter("startYymm", FinUtil.addMonth(bssd, -58))
				.setParameter("endYymm", FinUtil.addMonth(bssd, 1))
				.getResultList();
		String query2 = "FROM IrCurveHis"
				+ "				WHERE baseDate IN :lastDates"
				+ "				AND irCurveId = 'A100'"
				+ "				AND matCd = :matCd";
		List<IrCurveHis> dataEntity1 = session.createQuery(query2, IrCurveHis.class)
				.setParameter("lastDates", lastDates)
				.setParameter("matCd", "M0060")
				.getResultList();
		dataEntity1 = dataEntity1.stream()
				.peek(x -> x.setBaseDate(x.getBaseDate().substring(0,  6)))
				.sorted((x, y) -> x.getBaseDate().compareTo(y.getBaseDate()))
				.collect(Collectors.toList());
		
		//1-2. Select Data (Settings)
		String query3 = "FROM DiscRateCalcSetting";
		List<DiscRateCalcSetting> dataEntity2 = session.createQuery(query3, DiscRateCalcSetting.class)
				.getResultList();
		
		//1-3. Select Data (Disclosure Rates)
		String query4 = "FROM DiscRateHis"
				+ "				WHERE baseYymm >= :startYymm"
				+ "					AND baseYymm <= :endYymm";
		List<DiscRateHis> dataEntity3 = session.createQuery(query4, DiscRateHis.class)
				.setParameter("startYymm", FinUtil.addMonth(bssd, -35))
				.setParameter("endYymm", bssd)
				.getResultList();
		
		//2-1. Data -> StringMatrix -> Input Data (MA24)
		int n1 = dataEntity1.size();
		String[][] data1 = new String[n1][2];
		for(int i=0; i<n1; i++) {
			data1[i][0] = dataEntity1.get(i).getBaseDate();
			data1[i][1] = String.valueOf(dataEntity1.get(i).getIntRate());
		}
		StringMatrix dataMatrix1 = new StringMatrix(data1);
		dataMatrix1.sortRowVector(new int[] {0});
		Vector ma24 = Vector.createZeroVector(36);
		for(int i=0; i<36; i++)
			ma24.setEntry(i, dataMatrix1.getColumnVector(1).getSubvector(i, i+24).toVector().mean());
		
		//2-2. Data -> StringMatrix -> Input Data (Disclosure Rate)
		List<String> calculableDiscRatesCd = dataEntity2.stream()
				.filter(x -> x.getDiscRateCalcTyp().equals("01") || x.getDiscRateCalcTyp().equals("02"))
				.map(x -> x.getIntRateCd())
				.collect(Collectors.toList());
		List<DiscRateHis> calculableDiscRates = dataEntity3.stream()
				.filter(x -> calculableDiscRatesCd.contains(x.getIntRateCd()))
				.collect(Collectors.toList());
		int n3 = calculableDiscRates.size();
		String[][] data3 = new String[n3][3];
		for(int i=0; i<n3; i++) {
			data3[i][0] = calculableDiscRates.get(i).getBaseYymm();
			data3[i][1] = calculableDiscRates.get(i).getIntRateCd();
			data3[i][2] = String.valueOf(calculableDiscRates.get(i).getBaseDiscRate());
		}
		StringMatrix dataMatrix3 = new StringMatrix(data3);
		dataMatrix3.sortRowVector(new int[] {0, 1});
		Matrix y = dataMatrix3.pivotTableAvg(new int[] {0}, new int[] {1}, 2);
		
		//3. Beta Calculation
		int k = y.getColumnDimension();
		Vector[] beta = new Vector[k];
		for(int j=0; j<k; j++) {
			SimpleLinearRegression sl = new SimpleLinearRegression(ma24.getData(), y.getColumnVector(j).getData());
			beta[j] = new Vector(sl.getBeta());
		}
		Matrix betaMatrix = Matrix.concatenateRowVector(beta);
		
		//4-1. Insert Data (Calculable Disclosure Rates)
		DiscRateStats resultEntity;
		for(int i=0; i<k; i++) {
			resultEntity = new DiscRateStats();
			resultEntity.setBaseYymm(bssd);
			resultEntity.setDiscRateCalcTyp("I");
			resultEntity.setIntRateCd(y.getColumnNames().get(i).get(0));
			resultEntity.setIndepnVariable("M0060");
			resultEntity.setDepnVariable("BASE_DISC");
			resultEntity.setRegrCoef(betaMatrix.getEntry(i, 0));
			resultEntity.setRegrConstant(betaMatrix.getEntry(i, 1));
			resultEntity.setLastModifiedBy("Job31");
			resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
			session.saveOrUpdate(resultEntity);
		}
		
		//4-2. Insert Data (Non-Calculable Disclosure Rates)
		List<String> nonCalculableDiscRatesCd = dataEntity2.stream()
				.filter(x -> !x.getDiscRateCalcTyp().equals("01") && !x.getDiscRateCalcTyp().equals("02"))
				.map(x -> x.getIntRateCd())
				.collect(Collectors.toList());
		for(String discRateCd : nonCalculableDiscRatesCd) {
			resultEntity = new DiscRateStats();
			resultEntity.setBaseYymm(bssd);
			resultEntity.setDiscRateCalcTyp("I");
			resultEntity.setIntRateCd(discRateCd);
			resultEntity.setIndepnVariable("M0060");
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
		
		session.getTransaction().commit();
		session.close();
		System.out.println("공시이율 베타 생성... 완료.");
	}

}
