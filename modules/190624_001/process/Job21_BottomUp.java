package process;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import entity.BottomupDcnt;
import entity.IrCurveHis;
import esg.CoveredBond;
import esg.Matrix;
import esg.SmithWilson;
import esg.StringMatrix;
import esg.Vector;
import util.FinUtil;
import util.HibernateUtil;

public class Job21_BottomUp {
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1. Select Data
		String query = "FROM IrCurveHis"
				+ "		   WHERE BASE_DATE >= :startYymm"
				+ "			   AND BASE_DATE <= :endYymm";
		List<IrCurveHis> dataEntity = session.createQuery(query, IrCurveHis.class)
				.setParameter("startYymm", FinUtil.addMonth(bssd, -35))
				.setParameter("endYymm", FinUtil.addMonth(bssd, 1))
				.getResultList();
		
		//2. Data -> StringMatrix -> Input Data
		int n = dataEntity.size();
		String[][] data = new String[n][4];
		for(int i=0; i<n; i++) {
			data[i][0] = dataEntity.get(i).getBaseDate();
			data[i][1] = dataEntity.get(i).getIrCurveId();
			data[i][2] = dataEntity.get(i).getMatCd();
			data[i][3] = String.valueOf(dataEntity.get(i).getIntRate());
		}
		StringMatrix dataMatrix = new StringMatrix(data);
		dataMatrix.sortRowVector(new int[] {1, 0, 2});
		Matrix ktbRates = dataMatrix.filterRowVector(1, "A100").pivotTableSum(new int[] {0}, new int[] {2}, 3);
		Matrix kdbRates = dataMatrix.filterRowVector(1, "E110").pivotTableSum(new int[] {0}, new int[] {2}, 3);
		Vector ktbRatesCurrent = ktbRates.getRowVector(ktbRates.getRowDimension()-1);
		Vector maturity =  new Vector(ktbRates.getColumnNames().stream()
			.mapToDouble(x -> Double.parseDouble(x.get(0).replaceAll("M",  ""))/12.)
			.toArray());
		
		//3. Risk-Free Term Structure Calculation
		double ufr = 0.045;
		double llp = 20;
		SmithWilson sw = new SmithWilson(maturity.getData(), ktbRatesCurrent.getData(), ufr, llp);
		
		//4. Liquid Premium Calculation
		int termMax = 1200;
		CoveredBond cb = new CoveredBond(maturity.getData(), kdbRates.getData(), ktbRates.getData(), ktbRatesCurrent.getData());
		cb.calcBeta();
		double[] terms = Vector.createRangeVector(termMax+1).scalarMultiply(1./12.).getData();
		double[] liqPrem = new Vector(cb.getLiquidPremium(terms)).getData();
		
		//5. Bottom-Up Discount Rates Calculation
		double[] riskAdjRfSpotRate = new double[termMax+1];
		double[] riskAdjRfFwdRate = new double[termMax];
		for(int i=0; i<termMax+1; i++)
			riskAdjRfSpotRate[i] = sw.spot(i/12.) + liqPrem[i];
		for(int i=0; i<termMax; i++)
			riskAdjRfFwdRate[i] = Math.pow(Math.pow(1+riskAdjRfSpotRate[i+1], (i+1)/12.)/Math.pow(1+riskAdjRfSpotRate[i], i/12.), 12.)-1;
		
		// 6. Insert Data
		String irCurveId = "RF_KRW_BU"; 
		BottomupDcnt resultEntity;
		for(int i=0; i<termMax; i++) {
			resultEntity = new BottomupDcnt();
			resultEntity.setBaseYymm(bssd);
			resultEntity.setIrCurveId(irCurveId);
			resultEntity.setMatCd(String.format("M%04d", i+1));
			resultEntity.setRfRate(sw.spotRates.getData()[i]);
			resultEntity.setLiqPrem(liqPrem[i+1]);
			resultEntity.setRiskAdjSpotRate(riskAdjRfSpotRate[i+1]);
			resultEntity.setRiskAdjFwdRate(riskAdjRfFwdRate[i]);
			resultEntity.setLastModifiedBy("Job21");
			resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
			session.saveOrUpdate(resultEntity);
		}
		session.getTransaction().commit();

		session.close();
		
	}
}
