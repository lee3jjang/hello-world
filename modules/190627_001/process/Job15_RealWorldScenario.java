package process;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import entity.IrCurveHis;
import entity.RealWorldSce;
import esg.DynamicNelsonSiegel;
import esg.Matrix;
import esg.StringMatrix;
import util.FinUtil;
import util.HibernateUtil;

public class Job15_RealWorldScenario {
	
	public static void run(String bssd, boolean estimation) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1. Select Data
		String query = "FROM IrCurveHis"
				+ "			WHERE baseDate >= :startYymm"
				+ "				AND baseDate <= :endYymm"
				+ "				AND irCurveId = 'A100'";
		List<IrCurveHis> dataEntity = session.createQuery(query, IrCurveHis.class)
				.setParameter("startYymm", FinUtil.addMonth(bssd, -83))
				.setParameter("endYymm", FinUtil.addMonth(bssd, 1))
				.getResultList();
		
		//2. Data -> StringMatrix -> Input Data
		int n = dataEntity.size();
		String[][] data = new String[n][3];
		for(int i=0; i<n; i++) {
			data[i][0] = dataEntity.get(i).getBaseDate();
			data[i][1] = dataEntity.get(i).getMatCd();
			data[i][2] = String.valueOf(dataEntity.get(i).getIntRate());
		}
		StringMatrix dataMatrix = new StringMatrix(data);
		dataMatrix.sortRowVector(new int[] {0, 1});
		Matrix ktbRates = dataMatrix.pivotTableAvg(new int[] {0}, new int[] {1}, 2);
		double[] maturity = ktbRates.getColumnNames().stream().mapToDouble(x -> Double.parseDouble(x.get(0).replaceAll("M", ""))/12.).toArray();
		
		//3. Calibration
		double dt = 1./52.;
		double[] initParam = new double[] {0.60731, 1e-3, 0.12844, 0.34156, 0.70498, 0.04029, -0.02018, -0.009, 0.00653, -0.0061, 0.0042, 0.00009, -0.00468, 0.01036};
		double[] initState = new double[] {0.0258, -0.0104, 0.0032};
		double[][] initError = Matrix.createIdentityMatrix(3).getData();
		DynamicNelsonSiegel dns = new DynamicNelsonSiegel(initParam, dt);
		
		dns.setState(initState, initError);
		if(estimation) {
			System.out.println("Estimation을 수행합니다.");
			dns.setMeasurements(ktbRates.getData());
			double[] param = dns.estimate();
			dns.setParam(param, dt);
		}
		
		//4. Simulation
		int numSce = 10;
		int numWeek = (int)(1/dt)*1;
		int numMat = 1200;
		dns.setMaturity(maturity);
		double[][][] dnsScenarios = dns.generateSpotRate(numSce, numWeek);
		
		//5. Insert Data
		String irModelId = "DynamicNelsonSiegel_TEST";
		String irCurveId = "A100";
		RealWorldSce resultEntity;
		for(int i=0; i<numSce; i++) {
			for(int k=0; k<numMat; k++) {
				resultEntity = new RealWorldSce();
				resultEntity.setBaseYymm(bssd);
				resultEntity.setIrModelId(irModelId);
				resultEntity.setIrCurveId(irCurveId);
				resultEntity.setSceNo(i+1);
				resultEntity.setTime(String.format("M%04d", 12));
				resultEntity.setMatCd(String.format("M%04d", k+1));
				resultEntity.setRwSpotRate(dnsScenarios[i][numWeek-1][k]);
				resultEntity.setLastModifiedBy("Job15");
				resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
				session.saveOrUpdate(resultEntity);
			}
			if((i+1)%5 == 0) {
//				System.out.println("Flush and Clear");
				session.flush();
				session.clear();
			}
		}
		session.getTransaction().commit();
		
		session.close();
		
	}

}
