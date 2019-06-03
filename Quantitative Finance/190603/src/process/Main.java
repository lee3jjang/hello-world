package process;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.analysis.UnivariateFunction;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import model.CoveredBond;
import model.DynamicNelsonSiegel;
import model.SmithWilson;
import optim.NelderMead;
import optim.Newton;

public class Main {

	public static void main(String[] args) {
		Test1();
			
	}
	
	public static void Test1() {
		NelderMead nm = new NelderMead(x -> Math.pow(x[0]-2, 2) + Math.pow(x[1]-2, 2));
		nm.solve(
				new double[][] {{13., 21.}, {21., 32.}, {43., -21.}}
		);
	}
	
	public static void Test() {
		Newton nt = new Newton();
		double x0 = 10;
		nt.solve(
				x0, 
				x -> 3*Math.pow((x-2), 2),
				x -> 6*(x-2)
		);
		
		System.out.println(nt.x);
	}
	
	
	public static void Test0() {
		
		//0. Smith-Wilson
		//double[] term = {0.25, 0.5, 0.75, 1., 1.5, 2., 2.5, 3., 5., 7., 10., 15., 20.};
		//double [] rate = {0.04107	, 0.04342, 0.04479, 0.04521, 0.04725, 0.04841, 0.04893, 0.04946, 0.05145, 0.0535, 0.05446, 0.05592, 0.05793};
		//double ufr = 0.045;
		//double llp = 20;
		//double alpha = 0.01;
		
		//SmithWilson sw = new SmithWilson(term, rate, alpha, ufr, llp);
		
		
		//1. 데이터 읽기
		String fileName = "./data/sample.csv";
		List<String[]> intRate = new ArrayList<>();
		String[] line = null;
		CSVReader reader = null;
		
		try {
			reader = new CSVReader(new FileReader(fileName));
			while((line = reader.readNext()) != null) {
				intRate.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//2. 기준년월 구하기
		List<String> baseDate = intRate.stream()
			.filter(row -> row[1].equals("A100") & row[2].equals("M0012"))
			.map(row -> row[0])
			.sorted()
			.collect(Collectors.toList());
		String curDate = baseDate.get(baseDate.size()-1);
		
		
		//3. 만기 구하기
		List<String> matCd  = intRate.stream()
				.filter(row -> row[1].equals("A100") & row[0].equals(curDate))
				.map(row -> row[2])
				.sorted()
				.collect(Collectors.toList());
		
		//4. 만기를 연 단위로 변환
		double[] terms = matCd.stream().map(x -> Double.parseDouble(x.substring(1))/12).mapToDouble(Double::doubleValue).toArray();
		
		//5. 데이터 넣기
		int n = baseDate.size();
		int m = matCd.size();
				
		double[][] ktbRate = new double[n][m];
		double[][] kdbRate = new double[n][m];
		
		List<String[]> ktbList = intRate.stream().filter(row -> row[1].equals("A100")).collect(Collectors.toList());
		List<String[]> kdbList = intRate.stream().filter(row -> row[1].equals("E110")).collect(Collectors.toList());
		
		// 국고채
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				for(int k=0; k<ktbList.size(); k++) {
					if(ktbList.get(k)[0].equals(baseDate.get(i)) & ktbList.get(k)[2].equals(matCd.get(j))) {
						ktbRate[i][j] = Double.parseDouble(ktbList.get(k)[3]);
						ktbList.remove(k);
						break;
					}
				}
			}
		}
		
		// 산금채
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				for(int k=0; k<kdbList.size(); k++) {
					if(kdbList.get(k)[0].equals(baseDate.get(i)) & kdbList.get(k)[2].equals(matCd.get(j))) {
						kdbRate[i][j] = Double.parseDouble(kdbList.get(k)[3]);
						kdbList.remove(k);
						break;
					}
				}
			}
		}
		
		//6. 데이터 쓰기
		
		CoveredBond cb = new CoveredBond(terms, ktbRate, kdbRate);
		
		double[] mat = new double[1201];
		for(int i=0; i<1201; i++)
			mat[i] = (double) i/12;
		double[] liqPrem = cb.liqPrem(mat).toArray();
		
		List<double[]> rst = new ArrayList<>();
		for(int i=0; i<1201; i++)
			rst.add(new double[] {i, liqPrem[i]});
		
		/*
		String output = "./result/liquid_premium.csv";
		
		try {
			CSVWriter cw = new CSVWriter(new FileWriter(output), ',', '"');
			Iterator<double[]> it = rst.iterator();
			String[] temp = new String[2];
			double[] row;
			
			try {
				while(it.hasNext()) {
					row = it.next();
					for(int i=0; i<2; i++)
						temp[i] = String.valueOf(row[i]);
					cw.writeNext(temp);
				}
			} finally {
				cw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		// 1. Smith-Wilson Method
		double[] term = {1., 3., 5., 10., 20.};
		double[] rate = {0.01752, 0.01735, 0.01777, 0.01893, 0.01918};
		
		double ufr = 0.045;
		double alpha = 0.01;
		double llp = 20.;
		
		SmithWilson sw = new SmithWilson(term, rate, alpha, ufr, llp);
		
		
		// 2. Dynamic Nelson-Siegel
		double[][] rates = {
				{0.01740, 0.01726, 0.01742, 0.01866, 0.01895},
				{0.01733, 0.01706, 0.01730, 0.01856, 0.01898},
				{0.01743, 0.01720, 0.01754, 0.01889, 0.01919},
				{0.01744, 0.01720, 0.01754, 0.01877, 0.01915},
				{0.01752, 0.01735, 0.01777, 0.01893, 0.01918}
		};
		double[] params = new double[] {0.60731, 1e-3, 0.12844, 0.34156, 0.70498, 0.04029, -0.02018, -0.00900, 0.00653, -0.00610, 0.00420, 0.00009, -0.00468, 0.01036};
		DynamicNelsonSiegel dns = new DynamicNelsonSiegel(params, term, rates);
		dns.getClass();
		System.out.println(dns.currentState);
		System.out.println(dns.currentError);
		System.out.println(dns.A);
		
	}

}
