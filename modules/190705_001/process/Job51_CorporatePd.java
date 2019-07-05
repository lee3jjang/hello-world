package process;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;

import entity.CorpCumPd;
import entity.TransitionMatrix;
import esgcore.linalg.Matrix;
import esgcore.linalg.StringMatrix;
import esgcore.linalg.StringVector;
import esgcore.linalg.Vector;
import util.ECreditGrade;
import util.HibernateUtil;

public class Job51_CorporatePd {
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1. Select Data
		String query =  "FROM TransitionMatrix"
				+ "			WHERE baseYyyy = :baseYyyy"
				+ "				AND TM_TYPE = 'STM1'";
		List<TransitionMatrix> dataEntity = session.createQuery(query, TransitionMatrix.class)
				.setParameter("baseYyyy", bssd.substring(0, 4))
				.getResultList();
		
		//2. Data -> StringMatrix -> Input Data
		int n = dataEntity.size();
		String[][] data = new String[n][3];
		for(int i=0; i<n; i++) {
			data[i][0] = dataEntity.get(i).getFromCrdGrdCd();
			data[i][1] = dataEntity.get(i).getToCrdGrdCd();
			data[i][2] = String.valueOf(dataEntity.get(i).getProbRate());
		}
		StringMatrix dataMatrix = new StringMatrix(data);
		Comparator<String> comp = (v, w) -> {
			int k = ECreditGrade.getECreditGrade(v).getOrder()-ECreditGrade.getECreditGrade(w).getOrder();
			if(k > 0) return 1;
			else if (k == 0) return 0;
			else return -1;
		};
		dataMatrix.sortRowVector(new int[] {0,  1}, comp);
		Matrix tm = dataMatrix.pivotTableAvg(0, 1, 2);
		StringVector grades = new StringVector(tm.getRowNames().toArray(new String[0]));
		
		//3. Cumulative Probability of Default Calculation
		int m = tm.getRowDimension();
		Matrix tmAddDefault = tm.copy();
		tmAddDefault.addRowVector(m, Vector.createUnitVector(m, m+1));
		Matrix tmCumPower = tmAddDefault.copy();
		Vector[] cumPd = new Vector[100];
		Vector v;
		v = tmCumPower.getColumnVector(m);
		v.deleteElement(m);
		cumPd[0] = v;
		for(int i=1; i<100; i++) {
			tmCumPower = tmCumPower.multiply(tmAddDefault);
			v = tmCumPower.getColumnVector(m);
			v.deleteElement(m);
			cumPd[i] = v;
		}
		Matrix cumPdMatrix = Matrix.concatenateRowVector(cumPd);
		
		//4. Forward Probability of Default Calculation
		Matrix tmDeleteDefault = tm.copy();
		tmDeleteDefault.deleteColumnVector(m);
		Matrix tmFwdPower = Matrix.createIdentityMatrix(m);
		Vector[] fwdPd = new Vector[100];
		v = tm.getColumnVector(m);
		fwdPd[0] = v;
		for(int i=1; i<100; i++) {
			tmFwdPower = tmFwdPower.multiply(tmDeleteDefault);
			fwdPd[i] = tmFwdPower.operate(v);
		}
		Matrix fwdPdMatrix = Matrix.concatenateRowVector(fwdPd);
		
		//5. Insert Data
		CorpCumPd resultEntity;
		int r = cumPdMatrix.getRowDimension();
		int s = cumPdMatrix.getColumnDimension();
		for(int i=0; i<r; i++) {
			for(int j=0; j<s; j++) {
				resultEntity = new CorpCumPd();
				resultEntity.setBaseYymm(bssd);
				resultEntity.setTmType("STM1");
				resultEntity.setGradeCode(ECreditGrade.getECreditGrade(grades.getEntry(j)).getLegacyCode());
				resultEntity.setMatCd(String.format("M%04d", (i+1)*12));
				resultEntity.setCumPd(Math.round(cumPdMatrix.getEntry(i, j)*1e4)/1e4);
				resultEntity.setFwdPd(Math.round(fwdPdMatrix.getEntry(i, j)*1e4)/1e4);
				resultEntity.setLastModifiedBy("Job51");
				resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
				session.saveOrUpdate(resultEntity);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		System.out.println("기업 부도율 생성... 완료.");
	}	
	
}
