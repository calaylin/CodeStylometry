import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Extractor2012 {

	public static void getDownloadURL(int round_numbers_2012, long problem_numbers_2012,
			String name) throws IOException   {
		

	   try{         // get URL content
	          String url_open= "http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest="
				+ round_numbers_2012
				+ "&problem="
				+ problem_numbers_2012
				+ "&io_set_id=0&username=" + name;
	    		
	          URL website = new URL(url_open);
	          ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	          String contestantFolder = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2012MoreFileUsers/"+name+"/";
	          File aFile = new File(contestantFolder);			
	          if(aFile.exists() == false)
	 	    		aFile.mkdir();
	          String fileName = contestantFolder+round_numbers_2012+"_"+problem_numbers_2012+"_"+name+".zip";
	          FileOutputStream fos = new FileOutputStream(fileName);
	          File bFile = new File(fileName);			
	          if(bFile.exists() == false)
	 	    		bFile.createNewFile();
	          fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);    
	          unzip(fileName,contestantFolder );
	          List all_file_paths = Util.listAllFiles(contestantFolder);	         
	      		for(int j=0; j < all_file_paths.size();j++ )
	    	{
	      			int fileNamelength = all_file_paths.get(j).toString().length();
	      			if(!all_file_paths.get(j).toString().substring(fileNamelength-3, fileNamelength).contains("cpp")
	      					&
	      					!all_file_paths.get(j).toString().substring(fileNamelength-2, fileNamelength).contains("cc"))
	      			
	      			{
	      				File allFiles = new File(all_file_paths.get(j).toString());		
	      		      	allFiles.delete();	
	      			} 
	      			
	      			if((all_file_paths.get(j).toString().substring(fileNamelength-3, fileNamelength).contains("cpp") )
	      					& !(new File(all_file_paths.get(j).toString()).getName().contains(name)) )
	      			{
	      				File cppFiles = new File(all_file_paths.get(j).toString());		
      		      	cppFiles.renameTo(new File(fileName.substring(0, fileName.length()-3)+"cpp"));	
	      			}	
	      			
	      			if(all_file_paths.get(j).toString().substring(fileNamelength-2, fileNamelength).contains("cc")
	      					& !(new File(all_file_paths.get(j).toString()).getName().contains(name)) )
	      			{
	      				File cppFiles = new File(all_file_paths.get(j).toString());		
      		      	cppFiles.renameTo(new File(fileName.substring(0, fileName.length()-3)+"cpp"));	
	      			}	
	      			  
	      				
	    	}

	   }
	   catch (FileNotFoundException e) {
		   System.out.println("No File");		   
		   }
	   catch (IOException e) {
		   System.out.println("IOException");		   
		   }
	   

	}

	private static void unzip(String zipFilePath, String destDir) throws FileNotFoundException {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
           //     System.out.println("Unzipping to "+newFile.getAbsolutePath());                             
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                
                
                
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }
	
    public static void main(String[] args) throws IOException{

    	
/*
//http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest=[CONTESTNUMBER]&problem=[PROBLEMNUMBER]&io_set_id=[LARGEORSMALLDATASET]&username=[USERNAME]

//The [LARGEORSMALLDATASET] field is either a 0 or 1 to download either the small or large dataset. Since we always use the small dataset, we can always leave this as 0. So for example, if I wanted the URL for the first problem (small dataset) in the 2012 Qualification round for Gennady.Korotkevich, my URL would be:

//http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest=2974486&problem=5756407898963968&io_set_id=0&username=Gennady.Korotkevich
*/

    	int[] round_numbers_2012 ={1460488,	1645485,1836486,1781488,1842485,1835486,2075486};	
    	int[][] problem_numbers_2012 = new int[7][5];
    	//21 problems in total
    	problem_numbers_2012[0][0] = 1483485;
    	problem_numbers_2012[0][1] = 1595491;
    	problem_numbers_2012[0][2] = 1483488;
    	problem_numbers_2012[0][3] = 1285485;
    	problem_numbers_2012[1][0] = 1673486;
    	problem_numbers_2012[1][1] = 1482494;
    	problem_numbers_2012[1][2] = 1480492;
    	problem_numbers_2012[2][0] = 1480487;
    	problem_numbers_2012[2][1] = 1485488;
    	problem_numbers_2012[2][2] = 1484496;
    	problem_numbers_2012[3][0] = 1674486;
    	problem_numbers_2012[3][1] = 1482492;
    	problem_numbers_2012[3][2] = 1485490;
    	problem_numbers_2012[4][0] = 1481486;
    	problem_numbers_2012[4][1] = 1484495;
    	problem_numbers_2012[4][2] = 1486492;
    	problem_numbers_2012[4][3] = 1480495;
    	problem_numbers_2012[5][0] = 1475486;
    	problem_numbers_2012[5][1] = 1590487;
    	problem_numbers_2012[5][2] = 1481492;
    	problem_numbers_2012[5][3] = 1481496;
    	problem_numbers_2012[6][0] = 1486497;
    	problem_numbers_2012[6][1] = 2050486;
    	problem_numbers_2012[6][2] = 1821487;
    	problem_numbers_2012[6][3] = 1480498;
    	problem_numbers_2012[6][4] = 1484497;

    			

    			
    			
    	BufferedReader in = new BufferedReader(new FileReader("users/2012_user_morethan8.txt"));
    	String str;

        List<String> list = new ArrayList<String>();
        while((str = in.readLine()) != null){
            list.add(str);
        }

        String[] contestant_username_2012 = list.toArray(new String[0]);    	
//        getDownloadURL(2994486,5658282861527040L,"Aquaaarius");

        for (int i=0; i< round_numbers_2012.length; i++){//i is the round number
        	for(int j=0; j<5; j++){//j is the problem number

                for(int k=0; k< contestant_username_2012.length; k++){
                	getDownloadURL(round_numbers_2012[i],  problem_numbers_2012[i][j],
                			contestant_username_2012[k].toString());    
                	System.out.println(round_numbers_2012[i]+ "_"+ problem_numbers_2012[i][j]+"_"+
                			contestant_username_2012[k].toString());
                }
        	}        	
        }
        
        
        }

}