package process;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import entity.BottomupDcnt;
import entity.IrCurveHis;
import esgcore.linalg.Matrix;
import esgcore.linalg.StringMatrix;
import esgcore.linalg.Vector;
import esgcore.termstructure.BottomUpDiscountRate;
import esgcore.termstructure.CoveredBond;
import esgcore.termstructure.LiquidPremium;
import esgcore.termstructure.SmithWilson;
import esgcore.termstructure.TermStructure;
import util.FinUtil;
import util.HibernateUtil;

public class Job21_BottomUp {
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1. Select Data
		String query = "FROM IrCurveHis"
				+ "			WHERE baseDate >= :startYymm"
				+ "				AND baseDate <= :endYymm"
				+ "				AND irCurveId IN ('A100', 'E110')";
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
		Matrix ktbRates = dataMatrix.filterRowVector(1, "A100").pivotTableSum(0, 2, 3);
		Matrix kdbRates = dataMatrix.filterRowVector(1, "E110").pivotTableSum(0, 2, 3);
		Vector ktbRatesCurrent = ktbRates.getRowVector(ktbRates.getRowDimension()-1);
		Vector maturity =  new Vector(ktbRates.getColumnNames().stream()
			.mapToDouble(x -> Double.parseDouble(x.replaceAll("M",  ""))/12.)
			.toArray());
		
		//3. Risk-Free Term Structure Calculation
		double ufr = 0.045;
		double llp = 20;
		TermStructure sw = new SmithWilson(maturity, ktbRatesCurrent, Math.log(1+ufr), llp);
		
		//4. Liquid Premium Calculation
		int termMax = 1200;
		LiquidPremium cb = new CoveredBond(maturity, kdbRates, ktbRates, ktbRatesCurrent);
		BottomUpDiscountRate bu = new BottomUpDiscountRate(sw, cb);
		

		// 4. Insert Data
		String irCurveId = "RF_KRW_BU"; 
		BottomupDcnt resultEntity;
		double t;
		for(int i=0; i<termMax; i++) {
			t = (i+1)/12.;
			resultEntity = new BottomupDcnt();
			resultEntity.setBaseYymm(bssd);
			resultEntity.setIrCurveId(irCurveId);
			resultEntity.setMatCd(String.format("M%04d", i+1));
			/*
			resultEntity.setRiskFreeSpotRate(Math.round(sw.spot(t)*1e4)/1e4);
			resultEntity.setRiskFreeFwdRate(Math.round(sw.forward1M(t-1./12.)*1e4)/1e4);
			resultEntity.setLiqPrem(Math.round(cb.getLiquidPremium(t)*1e6)/1e6);
			resultEntity.setRiskAdjSpotRate(Math.round(bu.spot(t)*1e4)/1e4);
			resultEntity.setRiskAdjFwdRate(Math.round(bu.forward1M(t-1./12.)*1e4)/1e4);
			*/
			
			resultEntity.setRiskFreeSpotRate(sw.spot(t));
			resultEntity.setRiskFreeFwdRate(sw.forward1M(t-1./12.));
			resultEntity.setLiqPrem(cb.getLiquidPremium(t));
			resultEntity.setRiskAdjSpotRate(bu.spot(t));
			resultEntity.setRiskAdjFwdRate(bu.forward1M(t-1./12.));
			
			resultEntity.setLastModifiedBy("Job21");
			resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
			session.saveOrUpdate(resultEntity);
		}
		
		session.getTransaction().commit();
		session.close();
		System.out.println("Bottom-Up 할인율 생성... 완료.");
		
	}
}
