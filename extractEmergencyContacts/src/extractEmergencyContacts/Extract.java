package extractEmergencyContacts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Extract {
	static List<String> numbers=new ArrayList<String>();
	private static final String FILENAME = "D:\\emerygencyContacts.log";
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] split=sCurrentLine.split("\\]:");
				String contact=split[1];
				Matcher matcher = pattern.matcher(contact);
				if((!matcher.matches()) && (!contact.contains("_"))){
					if(checkRegionalLanguages(contact)){
						String rawParsed=contact.replaceAll("\\)", "").replaceAll("\\(", "").replaceAll(" ", "").replaceAll("-","").trim();
						numbers.add(rawParsed);
					}
				}
			}
			writefile();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();

			}

		}

	}
	private static boolean checkRegionalLanguages(String element){
		try{
			for (char c: element.toCharArray()) {
			     if ((Character.UnicodeBlock.of(c) == Character.UnicodeBlock.DEVANAGARI) || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.TAMIL) 
			    		 || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.TELUGU)) {
			    	 return false;
			     }
			 }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	private static void writefile() throws IOException{
		 Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss") ;

		  FileWriter writer = new FileWriter("D:/"+dateFormat.format(date)+".txt"); 
		  for(String str: numbers) {
		    writer.write(str);
		    writer.write(System.getProperty("line.separator"));
		  }
			  writer.close();
	          System.out.println("File written Successfully");
	}
}
