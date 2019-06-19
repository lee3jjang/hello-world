package esg;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {
	
	private String[] header;
	private String[][] data;
	
	public void readCsv(String path) {
		List<List<String>> ret = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = Files.newBufferedReader(Paths.get(path));
			Charset.forName("UTF-8");
			String line = "";
			
			while((line = br.readLine()) != null) {
				List<String> tmpList = new ArrayList<>();
				String array[] = line.split(",");
				tmpList = Arrays.asList(array);
				ret.add(tmpList);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int m = ret.size()-1;
		int n = ret.get(0).size();
		this.header = new String[n];
		for(int j=0; j<n; j++)
			this.header[j] = ret.get(0).get(j);
			
		this.data = new String[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				this.data[i][j] = ret.get(i+1).get(j);
	}
	
	public String[] getHeader() {
		return this.header;
	}
	
	public String[][] getData(){
		return this.data;
	}
	

}
