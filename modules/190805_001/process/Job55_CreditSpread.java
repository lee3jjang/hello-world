package process;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;

import entity.CreditSpread;
import entity.IrCurve;
import entity.IrCurveHis;
import esgcore.linalg.Matrix;
import esgcore.linalg.StringMatrix;
import esgcore.linalg.Vector;
import util.HibernateUtil;

public class Job55_CreditSpread {
	
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1-1. Select Data (Interest Rates By Credit Rating)
		String query1 = "FROM IrCurve"
				+ "			WHERE applMethDvcd = '3'"
				+ "				AND useYn = 'Y'"
				+ "				AND crdGrdCd IS NOT NULL"
				+ "				AND crdGrdCd != '000'";
		List<IrCurve> dataEntity1 = session.createQuery(query1, IrCurve.class)
				.getResultList();
		List<String> applCurveId = dataEntity1.stream().map(x -> x.getIrCurveId()).collect(Collectors.toList());
		Map<String, String> creditRatingMap = new HashMap<>();
		dataEntity1.stream().forEach(x -> creditRatingMap.put(x.getIrCurveId(), x.getCrdGrdCd()));
		String query2 = "FROM IrCurveHis"
				+ "			WHERE SUBSTR(baseDate, 0, 7) = :baseYymm"
				+ "				AND irCurveId IN :applCurveId";
		List<IrCurveHis> dataEntity2 = session.createQuery(query2, IrCurveHis.class)
				.setParameter("baseYymm", bssd)
				.setParameter("applCurveId", applCurveId)
				.getResultList();
		
		//1-2. Select Data (Risk-Free Interest Rates)
		String query3 = "FROM IrCurveHis"
				+ "			WHERE SUBSTR(baseDate, 0, 7) = :baseYymm"
				+ "				AND irCurveId = 'A100'";
		List<IrCurveHis> dataEntity3 = session.createQuery(query3, IrCurveHis.class)
				.setParameter("baseYymm", bssd)
				.getResultList();
		
		//2-1. Data -> StringMatrix -> Input Data (Risk-Free Interest Rates)
		int n2 = dataEntity3.size();
		String[][] data2 = new String[n2][3];
		for(int i=0; i<n2; i++) {
			data2[i][0] = dataEntity3.get(i).getIrCurveId();
			data2[i][1] = dataEntity3.get(i).getMatCd();
			data2[i][2] = String.valueOf(dataEntity3.get(i).getIntRate());
		}
		StringMatrix dataMatrix2 = new StringMatrix(data2);
		dataMatrix2.sortRowVector(new int[] {1, 0});
		Vector rf = dataMatrix2.pivotTableAvg(1, 0, 2).getColumnVector(0);
		
		//2-2. Data -> StringMatrix -> Input Data (Interest Rates By Credit Rating)
		int n1 = dataEntity2.size();
		String[][] data1 = new String[n1][3];
		for(int i=0; i<n1; i++) {
			data1[i][0] = dataEntity2.get(i).getIrCurveId();
			data1[i][1] = dataEntity2.get(i).getMatCd();
			data1[i][2] = String.valueOf(dataEntity2.get(i).getIntRate());
		}
		StringMatrix dataMatrix1 = new StringMatrix(data1);
		dataMatrix1.sortRowVector(new int[] {1, 0});
		Matrix avgIntRate = dataMatrix1.pivotTableAvg(1, 0, 2);
		Matrix creditSpreadHis = avgIntRate.scalarAdd(rf.scalarMultiply(-1.));
		creditSpreadHis.setColumnNames(avgIntRate.getColumnNames());
		creditSpreadHis.setRowNames(avgIntRate.getRowNames());
		
		//3. Credit Spread Calculation
		StringMatrix creditSpreadByCurveId = creditSpreadHis.unpivotTable();
		creditSpreadByCurveId.setColumnVector(1, creditSpreadByCurveId.getColumnVector(1).map(x -> creditRatingMap.get(x)));
		Matrix creditSpread = creditSpreadByCurveId.pivotTableAvg(1, 0, 2);
		
		//4. Insert Data
		CreditSpread resultEntity;
		int m = creditSpread.getRowDimension();
		int n = creditSpread.getColumnDimension();
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				resultEntity = new CreditSpread();
				resultEntity.setBaseYymm(bssd);
				resultEntity.setCrdGrdCd(creditSpread.getRowNames().get(i));		// 신용등급 코드 일원화 시킬 필요 있음
				resultEntity.setMatCd(creditSpread.getColumnNames().get(j));
				resultEntity.setCrdSpread(Math.round(creditSpread.getEntry(i, j)*1e6)/1e6);
				resultEntity.setLastModifiedBy("Job55");
				resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
				session.saveOrUpdate(resultEntity);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		System.out.println("신용 스프레드 생성... 완료.");
		
	}

}
