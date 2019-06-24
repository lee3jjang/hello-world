package process;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.hibernate.Session;

import entity.IrCurveHis;
import esg.Matrix;
import esg.StringMatrix;
import esg.StringVector;
import esg.Vector;
import util.HibernateUtil;


public class Main {
	
	private static Session session;

	public static void main(String[] args) {
		
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<IrCurveHis> curveHis = session.createQuery("FROM IrCurveHis").getResultList();
		session.getTransaction().commit();
		session.close();
				
		int n = curveHis.size();
		String[][] data = new String[n][4];
		for(int i=0; i<n; i++) {
			data[i][0] = curveHis.get(i).getBaseDate();
			data[i][1] = curveHis.get(i).getIrCurveId();
			data[i][2] = curveHis.get(i).getMatCd();
			data[i][3] = String.valueOf(curveHis.get(i).getIntRate());
		}
		StringMatrix M = new StringMatrix(data);
		
		int[] index = new int[] {2, 0};
		int[] columns = new int[] {1};
		int values = 3;
		
		Matrix N = M.pivotTableAvg(index, columns, values);
		//N.getColumnVector(Arrays.asList("E110")).print();
		
		//Arrays.sort(N.getColumnVector(Arrays.asList("E110")).getData());
		
		//List<String> k = Arrays.asList("A110", "A100", "E110");
		//Collections.sort(k, (str1, str2) -> str1.compareTo(str2));
		
		//System.out.println(k);
		M.sort(new int[] {3, 1});
		M.print();
		
	}

}
