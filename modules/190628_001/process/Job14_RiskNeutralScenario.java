package process;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;

import entity.IrCurveHis;
import entity.RiskNeutralSce;
import entity.SwaptionVol;
import esgcore.HullWhite;
import esgcore.SmithWilson;
import esgcore.StringMatrix;
import esgcore.Vector;
import util.FinUtil;
import util.HibernateUtil;

public class Job14_RiskNeutralScenario {
	public static void run(String bssd, boolean calibration) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1-1. Select Data (Interest Rates)
		String query1 = "SELECT MAX(baseDate)"
				+ "				FROM IrCurveHis"
				+ "				WHERE baseDate <= :baseYymm"
				+ "					AND irCurveId = 'A100'";
		String lastDate = (String) session.createQuery(query1)
				.setParameter("baseYymm", FinUtil.addMonth(bssd, 1))
				.getSingleResult();
		
		String query2 = "FROM IrCurveHis"
				+ "				WHERE baseDate = :lastDate"
				+ "					AND irCurveId = 'A100'";
		List<IrCurveHis> dataEntity1 = session.createQuery(query2, IrCurveHis.class)
				.setParameter("lastDate", lastDate)
				.getResultList();
		
		
		//1-2. Select Data (Swaption Volatilities)
		String query3 = "FROM SwaptionVol"
				+ "				WHERE baseYymm = :baseYymm";
		List<SwaptionVol> dataEntity2 = session.createQuery(query3, SwaptionVol.class)
				.setParameter("baseYymm", bssd)
				.getResultList();
		
		
		//2-1. (Interest Rates) Data -> StringMatrix -> InputData
		int n = dataEntity1.size();
		String[][] data1 = new String[n][2];
		for(int i=0; i<n; i++) {
			data1[i][0] = dataEntity1.get(i).getMatCd();
			data1[i][1] = String.valueOf(dataEntity1.get(i).getIntRate());
		}
		StringMatrix dataMatrix1 = new StringMatrix(data1);
		dataMatrix1.sortRowVector(new int[] {0});
		Vector maturity = dataMatrix1.getColumnVector(0).map(x -> x.replaceAll("M", "")).toVector();
		Vector ktbRatesCurrent = dataMatrix1.getColumnVector(1).toVector();
		
		
		//2-2. (Black Volatility) Data -> StringMatrix -> InputData
		int m = dataEntity2.size();
		String[][] data2 = new String[m][3];
		for(int i=0; i<m; i++) {
			data2[i][0] = String.valueOf(dataEntity2.get(i).getSwaptionMaturity());
			data2[i][1] = String.valueOf(dataEntity2.get(i).getSwapTenor());
			data2[i][2] = String.valueOf(dataEntity2.get(i).getVol());
		}
		StringMatrix dataMatrix2 = new StringMatrix(data2);
		Comparator<String> comp = (v, w) -> {
			int k = Integer.parseInt(v)-Integer.parseInt(w);
			if(k > 0) return 1;
			else if (k==0) return 0;
			else return -1;
		};
		dataMatrix2.sortRowVector(new int[] {0, 1}, comp);
		double[] swaptionMaturity = Arrays.stream(dataMatrix2.getColumnVector(0).getData()).distinct().mapToDouble(Double::parseDouble).toArray();
		double[] swapTenor = Arrays.stream(dataMatrix2.getColumnVector(1).getData()).distinct().mapToDouble(Double::parseDouble).toArray();
		double[][] blackVol = dataMatrix2.pivotTableAvg(new int[] {0}, new int[] {1}, 2).getData();
		
		
		//3. Calibration
		double ufr = 0.045;
		double llp = 20;
		SmithWilson sw = new SmithWilson(maturity.getData(), ktbRatesCurrent.getData(), ufr, llp);
		sw.setBlackVol(swaptionMaturity, swapTenor, blackVol);
		double alpha = 0.01;
		double sigma = 0.005;
		HullWhite hw = new HullWhite(alpha, sigma, sw);
		if(calibration) {
			System.out.println("Calibration을 수행합니다.");
			double[] param = hw.calibrate();
			hw.setParams(param[0], param[1]);			
		}
		
		
		//4. Simulation
		int numYear = 100;
		int numSce = 200;
		double[][] hwScenarios = hw.simulation(numYear, numSce);
		
		
		//5. Insert Data
		String irModelId = "HullWhite1_TEST";
		String irCurveId = "A100";
		RiskNeutralSce resultEntity;
		for(int i=0; i<numSce; i++) {
			for(int j=0; j<12*numYear; j++) {
				resultEntity = new RiskNeutralSce();
				resultEntity.setBaseYymm(bssd);
				resultEntity.setIrModelId(irModelId);
				resultEntity.setIrCurveId(irCurveId);
				resultEntity.setSceNo(i+1);
				resultEntity.setTime(String.format("M%04d", j+1));
				resultEntity.setRnShortRate(hwScenarios[i][j]);
				resultEntity.setLastModifiedBy("Job14");
				resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
				session.saveOrUpdate(resultEntity);
			}
		}
		session.getTransaction().commit();
		
		session.close();
		System.out.println("위험중립 시나리오 생성... 완료.");
	}
}
