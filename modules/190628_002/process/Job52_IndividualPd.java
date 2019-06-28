package process;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import entity.IndiCrdGrdPdUd;
import entity.IndiCumPd;
import esgcore.Matrix;
import esgcore.StringMatrix;
import esgcore.StringVector;
import esgcore.Vector;
import util.HibernateUtil;

public class Job52_IndividualPd {
	public static void run(String bssd) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//1. Select Data
		String query = "FROM IndiCrdGrdPdUd"
				+ "			WHERE baseYymm = :baseYymm";
		List<IndiCrdGrdPdUd> dataEntity = session.createQuery(query, IndiCrdGrdPdUd.class)
				.setParameter("baseYymm", bssd)
				.getResultList();
		
		//2. Data -> StringMatrix -> Input Data
		int n = dataEntity.size();
		String[][] data1 = new String[n][3];
		String[][] data2 = new String[n][31];
		for(int i=0; i<n; i++) {
			data1[i][0] = dataEntity.get(i).getCbGrdCd();
			data1[i][1] = String.valueOf(dataEntity.get(i).getKcbPd());
			data1[i][2] = String.valueOf(dataEntity.get(i).getNicePd());
			
			data2[i][0] = dataEntity.get(i).getCbGrdCd();
			data2[i][1] = String.valueOf(dataEntity.get(i).getY1());
			data2[i][2] = String.valueOf(dataEntity.get(i).getY2());
			data2[i][3] = String.valueOf(dataEntity.get(i).getY3());
			data2[i][4] = String.valueOf(dataEntity.get(i).getY4());
			data2[i][5] = String.valueOf(dataEntity.get(i).getY5());
			data2[i][6] = String.valueOf(dataEntity.get(i).getY6());
			data2[i][7] = String.valueOf(dataEntity.get(i).getY7());
			data2[i][8] = String.valueOf(dataEntity.get(i).getY8());
			data2[i][9] = String.valueOf(dataEntity.get(i).getY9());
			data2[i][10] = String.valueOf(dataEntity.get(i).getY10());
			data2[i][11] = String.valueOf(dataEntity.get(i).getY11());
			data2[i][12] = String.valueOf(dataEntity.get(i).getY12());
			data2[i][13] = String.valueOf(dataEntity.get(i).getY13());
			data2[i][14] = String.valueOf(dataEntity.get(i).getY14());
			data2[i][15] = String.valueOf(dataEntity.get(i).getY15());
			data2[i][16] = String.valueOf(dataEntity.get(i).getY16());
			data2[i][17] = String.valueOf(dataEntity.get(i).getY17());
			data2[i][18] = String.valueOf(dataEntity.get(i).getY18());
			data2[i][19] = String.valueOf(dataEntity.get(i).getY19());
			data2[i][20] = String.valueOf(dataEntity.get(i).getY20());
			data2[i][21] = String.valueOf(dataEntity.get(i).getY21());
			data2[i][22] = String.valueOf(dataEntity.get(i).getY22());
			data2[i][23] = String.valueOf(dataEntity.get(i).getY23());
			data2[i][24] = String.valueOf(dataEntity.get(i).getY24());
			data2[i][25] = String.valueOf(dataEntity.get(i).getY25());
			data2[i][26] = String.valueOf(dataEntity.get(i).getY26());
			data2[i][27] = String.valueOf(dataEntity.get(i).getY27());
			data2[i][28] = String.valueOf(dataEntity.get(i).getY28());
			data2[i][29] = String.valueOf(dataEntity.get(i).getY29());
			data2[i][30] = String.valueOf(dataEntity.get(i).getY30());
		}
		StringMatrix dataMatrix1 = new StringMatrix(data1);
		StringMatrix dataMatrix2 = new StringMatrix(data2);
		dataMatrix1.sortRowVector(0);
		dataMatrix2.sortRowVector(0);
		StringVector cbCrdCd = dataMatrix1.getColumnVector(0);
		dataMatrix1.deleteColumnVector(0);
		dataMatrix2.deleteColumnVector(0);
		Matrix pd = dataMatrix1.toMatrix();
		Matrix pdInc = dataMatrix2.toMatrix();
		Vector kcbPd1 = pd.getColumnVector(0);
		Vector nicePd1 = pd.getColumnVector(1);
		
		//3. Cumulative Probability of Default Calculation
		Matrix kcbCumPd = pdInc.scalarMultiply(kcbPd1).map(x -> Math.min(x, 1));
		Matrix niceCumPd = pdInc.scalarMultiply(nicePd1).map(x -> Math.min(x, 1));
		Matrix meanCumPd = pdInc.scalarMultiply(pd.meanRowVector()).map(x -> Math.min(x, 1));
		
		//4. Forward Probability of Default Calculation
		Matrix kcbFwdPd = kcbCumPd.incrementByColumn(); 
		Matrix niceFwdPd = niceCumPd.incrementByColumn();
		Matrix meanFwdPd = meanCumPd.incrementByColumn();

		//5-1. Insert Data (KCB)
		IndiCumPd resultEntity;
		int r = kcbCumPd.getRowDimension();
		int s = kcbCumPd.getColumnDimension();
		for(int j=0; j<s; j++) {
			for(int i=0; i<r; i++) {
				resultEntity = new IndiCumPd();
				resultEntity.setBaseYymm(bssd);
				resultEntity.setCrdEvalAgency("KCB");
				resultEntity.setCbGradeCode(cbCrdCd.getEntry(i));
				resultEntity.setMatCd(String.format("M%04d", (j+1)*12));
				resultEntity.setCumPd(kcbCumPd.getEntry(i, j));
				resultEntity.setFwdPd(kcbFwdPd.getEntry(i, j));
				resultEntity.setLastModifiedBy("Job52");
				resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
				session.saveOrUpdate(resultEntity);
			}
		}
		
		//5-2. Insert Data (NICE)
		for(int j=0; j<s; j++) {
			for(int i=0; i<r; i++) {
				resultEntity = new IndiCumPd();
				resultEntity.setBaseYymm(bssd);
				resultEntity.setCrdEvalAgency("NICE");
				resultEntity.setCbGradeCode(cbCrdCd.getEntry(i));
				resultEntity.setMatCd(String.format("M%04d", (j+1)*12));
				resultEntity.setCumPd(niceCumPd.getEntry(i, j));
				resultEntity.setFwdPd(niceFwdPd.getEntry(i, j));
				resultEntity.setLastModifiedBy("Job52");
				resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
				session.saveOrUpdate(resultEntity);
			}
		}
		
		//5-3. Insert Data (MEAN)
		for(int j=0; j<s; j++) {
			for(int i=0; i<r; i++) {
				resultEntity = new IndiCumPd();
				resultEntity.setBaseYymm(bssd);
				resultEntity.setCrdEvalAgency("MEAN");
				resultEntity.setCbGradeCode(cbCrdCd.getEntry(i));
				resultEntity.setMatCd(String.format("M%04d", (j+1)*12));
				resultEntity.setCumPd(meanCumPd.getEntry(i, j));
				resultEntity.setFwdPd(meanFwdPd.getEntry(i, j));
				resultEntity.setLastModifiedBy("Job52");
				resultEntity.setLastUpdateDate(LocalDateTime.now().toString());
				session.saveOrUpdate(resultEntity);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		System.out.println("개인 부도율 생성... 완료.");
		
	}

}
