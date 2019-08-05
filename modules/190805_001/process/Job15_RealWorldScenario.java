package process;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import entity.IrCurveHis;
import entity.RealWorldSce;
import esgcore.linalg.Matrix;
import esgcore.linalg.StringMatrix;
import esgcore.linalg.Vector;
import esgcore.realworld.ArbitrageFreeNelsonSiegel;
import esgcore.realworld.DynamicNelsonSiegel;
import util.FinUtil;
import util.HibernateUtil;

public class Job15_RealWorldScenario {
	
	public static void run(String bssd, boolean estimation, String model) {
		
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
		Matrix ktbRates = dataMatrix.pivotTableAvg(0, 1, 2);
		Vector maturity = new Vector(ktbRates.getColumnNames().stream().mapToDouble(x -> Double.parseDouble(x.replaceAll("M", ""))/12.).toArray());
		
		double dt = 1./52.;
		int numSce = 100;
		int numWeek = (int)(1/dt)*1;
		int numMat = 1200;
		Matrix scenarios;
		String irModelId;
		if(model == "DNS") {
			
			//3. Calibration
			irModelId = "DynamicNelsonSiegel_TEST";
			Vector initParam = new Vector(new double[] {0.60731, 1e-3, 0.12844, 0.34156, 0.70498, 0.04029, -0.02018, -0.009, 0.00653, -0.0061, 0.0042, 0.00009, -0.00468, 0.01036});
			Vector initState = new Vector(new double[] {0.0258, -0.0104, 0.0032});
			Matrix initError = Matrix.createIdentityMatrix(3);
			DynamicNelsonSiegel dns = new DynamicNelsonSiegel(initParam, dt);
			
			dns.setState(initState, initError);
			if(estimation) {
				System.out.println("Estimation을 수행합니다.");
				dns.setMeasurements(ktbRates);
				Vector param = dns.estimate();
				dns.setParam(param, dt);
			}
			
			//4. Simulation

			dns.setMaturity(maturity);
			scenarios = dns.generateSpotRate(numSce, numWeek);
			
		} else if (model == "AFNS") {
			
			//3. Calibration
			irModelId = "ArbitrageFreeNelsonSiegel_TEST";
			Vector initParam = new Vector(new double[] {0.39959, 1e-3, 0.12741, 0.33988, 0.51562, 0.04366, -0.02319, -0.00634, 0.00623, -0.00564, 0.00386, 0.00149, -0.00316, 0.01108});
			Vector initState = new Vector(new double[] {0.0250, -0.0102, 0.0078});
			Matrix initError = Matrix.createIdentityMatrix(3);
			ArbitrageFreeNelsonSiegel afns = new ArbitrageFreeNelsonSiegel(initParam, dt);
			
			afns.setState(initState, initError);
			if(estimation) {
				System.out.println("Estimation을 수행합니다.");
				afns.setMeasurements(ktbRates);
				Vector param = afns.estimate();
				afns.setParam(param, dt);
			}
			
			//4. Simulation
			afns.setMaturity(maturity);
			scenarios = afns.generateSpotRate(numSce, numWeek);
			
		} else {
			throw new RuntimeException("모델명이 유효하지 않습니다.");
		}
		
		//5. Insert Data
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
				resultEntity.setRwSpotRate(Math.round(scenarios.getEntry(i, k)*1e4)/1e4);
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
		System.out.println("현실세계 시나리오 생성... 완료.");
		
	}

}
