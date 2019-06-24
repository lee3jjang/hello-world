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
		//M.print();
		
		int[] index = new int[] {2, 0};
		int[] columns = new int[] {1};
		int values = 3;
		
		M.print();
		Matrix N = M.pivotTableAvg(index, columns, values);
		System.out.println(N.getColumnNames());
		System.out.println(N.getRowNames());
		
		
		/*
		List<List<String>> indexList = Arrays.stream(M.getData())
			.map(x -> {
				List<String> idx = new ArrayList<>();
				for(int i : index)
					idx.add(x[i]);
				return idx;
			}).distinct().collect(Collectors.toList());
		
		List<List<String>> columnList = Arrays.stream(M.getData())
				.map(x -> {
					List<String> col = new ArrayList<>();
					for(int c : columns)
						col.add(x[c]);
					return col;
			}).distinct().collect(Collectors.toList());
		
		//Collections.sort(indexList);
		//Collections.sort(columnList);
		
		List<List<String>> indexColumnList = new ArrayList<>();
		for(List<String> idx : indexList)
			for(List <String> col : columnList) {
				List<String> indexColumn = new ArrayList<>(idx);
				indexColumn.addAll(col);
			}
			

		Map<List<String>, Double> s = Arrays.stream(M.getData()).collect(Collectors.groupingBy(df -> {
			List<String> indexColumn = new ArrayList<>();
			for(Integer i : index)
				indexColumn.add(df[i]);
			for(Integer c : columns)
				indexColumn.add(df[c]);
			return indexColumn;
		}, Collectors.summingDouble(df -> Double.parseDouble(df[values]))));

		Map<List<List<String>>, Double> s = Arrays.stream(M.getData()).collect(Collectors.groupingBy(df -> {
			List<String> idx = new ArrayList<>();
			List<String> col = new ArrayList<>();
			List<List<String>> indexColumn = new ArrayList<>();
			for(Integer i : index)
				idx.add(df[i]);
			for(Integer c : columns)
				col.add(df[c]);
			indexColumn.add(idx);
			indexColumn.add(col);
			return indexColumn;
		}, Collectors.summingDouble(df -> Double.parseDouble(df[values]))));
		int m = indexList.size();
		n = columnList.size();
		double[][] A = new double[m][n];
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				A[i][j] = s.get(Arrays.asList(indexList.get(i), columnList.get(j))); 
			}
		}
		Matrix N = new Matrix(A);
		N.print();

		*/
		/*
		int m = indexList.size();
		n = columnsList.size();
		String[][] A = new String[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				A[i][j] = String.valueOf(s.get(indexList.get(i)).get(columnsList.get(j)));
			
		StringMatrix N = new StringMatrix(A);
		N.setRowNames(indexList.toArray(new String[0]));
		N.setColumnNames(columnsList.toArray(new String[0]));
		(new StringVector(N.getRowNames())).print();
		(new StringVector(N.getColumnNames())).print();
		N.print();
		*/
		/*
		int n = rates.get("A100").size();
		double[] kdbRates = new double[n];
		double[] ktbRates = new double[n];
		List<String> matCd = new ArrayList<String>(rates.get("A100").keySet());
		Collections.sort(matCd);
		for(int i=0; i<n; i++) {
			ktbRates[i] = rates.get("A100").get(matCd.get(i));
			kdbRates[i] = rates.get("E110").get(matCd.get(i));
		}
		
		StringVector v = new StringVector(matCd.toArray(new String[0]));
		v.print();
		*/
	}

}
