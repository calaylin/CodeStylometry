import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;


public class DatasetCreator 
{
	public static void organizeByCountry(String parentDir, String outputFolderName, int year) throws IOException
	{
		 File file = new File(parentDir);
		   String[] directories = file.list(new FilenameFilter() 
		   {
		     @Override
		     public boolean accept(File current, String name) 
		     {
		       return new File(current, name).isDirectory();
		     }
		   });
		   System.out.println(Arrays.toString(directories));
		   
		   String contestantInfo = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/CodeJamStatDBs/contestants"+ year +".csv" ;
		
		   
		   for (int i =0; i< directories.length; i++)
		   {
			   //authorname is directoryname - 1 because Andrew put an extra 0 at the end of the authorname
			   String authorName = directories[i].toString().substring(0, directories[i].toString().length()-1); 
			   String authorDir = parentDir + directories[i] + "/";
			   System.out.println(authorName);
			   System.out.println(authorDir);

			   BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				String country= null;
					br = new BufferedReader(new FileReader(contestantInfo));
					while ((line = br.readLine()) != null) 
					{
						String[] info = line.split(cvsSplitBy);
						if(info[0].equals(authorName) )
						{
							System.out.println(info[0]+info[1]);

							 country = info[1].toString().trim();
							 //copy authors folder to a folder named as the country
							 
							 
							  File srcFolder = new File(authorDir);
						    	File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/"
							   +outputFolderName) ;
						      	File destFolder = new File(destFolderParent +"/"+ country) ;
						    	if(!destFolder.exists())
						    	{
									///System.out.println(file.getAbsolutePath());
						    		destFolder.mkdirs();
								}
						    	
							    List cpp_file_paths = Util.listCPPFiles(authorDir);
							    for(int j=0; j< cpp_file_paths.size(); j++)
							    {
							    	File srcFile=new File(cpp_file_paths.get(0).toString());							    	
							    	File destFile= new File(destFolder + "/"+ srcFile.getName());
							    	FileUtils.copyFile(srcFile, destFile);
							     	if(!destFile.exists())
							    	{
										///System.out.println(file.getAbsolutePath());
							    		destFile.mkdirs();
									}
							    }
							    

						    	
						    	
/*						    	//make sure source exists
						    	if(!srcFolder.exists())
						    	{
						           System.out.println("Directory does not exist.");
						           //just exit
						           System.exit(0);
						        }else
						        {
						 
						           try
						           {
						        	Util.copyFolder(srcFolder,destFolder);
						           }catch(IOException e)
						           {
						        	e.printStackTrace();
						        	//error, just exit
						                System.exit(0);
						           } 
						        }*/		 
					}
				}
		   }		   
	}

	public static void mergeSameAuthors(String parentDir, String outputFolderName)
	{
		  File file = new File(parentDir);
		   String[] directories = file.list(new FilenameFilter() 
		   {
		     @Override
		     public boolean accept(File current, String name) 
		     {
		       return new File(current, name).isDirectory();
		     }
		   });
		   System.out.println(Arrays.toString(directories));
		   

		   for(int j=0; j< directories.length; j++)
		    {
				  File yearFile = new File(parentDir+"/"+directories[j]);

			   String[] authorDirectories = yearFile.list(new FilenameFilter() 
			   {
			     @Override
			     public boolean accept(File current, String name) 
			     {
			       return new File(current, name).isDirectory();
			     }
			   });			   
			   
			   for(int k=0; k< authorDirectories.length; k++)
			    { 		   
			   String author_cpp_dir = parentDir + directories[j] +"/"+authorDirectories[k] +"/";
			   System.out.println(author_cpp_dir);
			   List test_file_paths = Util.listCPPFiles(author_cpp_dir);
			   for(int i=0; i< test_file_paths.size(); i++)
			   {
					   String filePath = test_file_paths.get(i).toString();
					   
					   File srcFolder = new File(author_cpp_dir);
				    	File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/"
					   +outputFolderName) ;
				      	File destFolder = new File(destFolderParent +"/"+ authorDirectories[k].toString()) ;
				    	if(!destFolder.exists())
				    	{
							///System.out.println(file.getAbsolutePath());
				    		destFolder.mkdirs();
						}
				    	//make sure source exists
				    	if(!srcFolder.exists())
				    	{
				           System.out.println("Directory does not exist.");
				           //just exit
				           System.exit(0);
				        }else
				        {
				 
				           try{
				        	Util.copyFolder(srcFolder,destFolder);
				           }catch(IOException e)
				           {
				        	e.printStackTrace();
				        	//error, just exit
				                System.exit(0);
				           }
				        }
				   }  
			   }}
		
	}
	 

		public static void copyAuthorsWithExactFileNumber(String test_cpp_dir, int fileCount){
		File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/bigExperiments/"
					   +fileCount+"FilesExactlyPerAuthor_2012_validation_exact") ;
	  	if(!destFolderParent.exists())
    	{
			///System.out.println(file.getAbsolutePath());
	  		destFolderParent.mkdirs();
		}
		
	   File file = new File(test_cpp_dir);
	   
	   String[] directories = file.list(new FilenameFilter() 
	   {
	     @Override
	     public boolean accept(File current, String name) 
	     {
	       return new File(current, name).isDirectory();
	     }
	   });
	   System.out.println(Arrays.toString(directories));
	   
	   
	   for(int j=0; j< directories.length; j++)

	    {
//		   System.out.println(directories[j].toString());
		   String author_cpp_dir = test_cpp_dir + directories[j] +"/";
//		   System.out.println(author_cpp_dir);
		   List test_file_paths = Util.listCPPFiles(author_cpp_dir);
		   for(int i=0; i< test_file_paths.size(); i++)
		   {
//				int testIDlength = test_file_paths.get(i).toString().length();   
			   //if the author has 6 cpp files
			//   int fileCount =6;

			   
		   
		   String[] authors = destFolderParent.list(new FilenameFilter() 
		   {
		     @Override
		     public boolean accept(File current, String name) 
		     {
		       return new File(current, name).isDirectory();
		     }
		   });
		   System.out.println(authors.length);
			 	
		
//		   if(test_file_paths.size() == fileCount)

// Use this if you want to have a specific number of authors in the folder		 	
			   if(test_file_paths.size() == fileCount && authors.length < 10000000)
			   {
				   System.out.println(author_cpp_dir);

				   String filePath = test_file_paths.get(i).toString();
				   //one empty file in each folder, skip that
				   System.out.println(filePath);  
				   
				   File srcFolder = new File(author_cpp_dir); 
			      	File destFolder = new File(destFolderParent +"/"+ directories[j].toString()) ;
			    	if(!destFolder.exists())
			    	{
						///System.out.println(file.getAbsolutePath());
			    		destFolder.mkdirs();
					}
			    	//make sure source exists
			    	if(!srcFolder.exists())
			    	{
			           System.out.println("Directory does not exist.");
			           //just exit
			           System.exit(0);
			        }else
			        {
			 
			           try{			      
			        	Util.copyFolder(srcFolder,destFolder);
			           }catch(IOException e)
			           {
			        	e.printStackTrace();
			        	//error, just exit
			                System.exit(0);
			           }
			        }
			   }   
		   }	   
	    }
	}

		
		
		
		
		
		
		
		public static void copyAuthorsWithAtLeastFileNumber(String test_cpp_dir, int fileCount) throws IOException{

			   File file = new File(test_cpp_dir);
			   String[] directories = file.list(new FilenameFilter() 
			   {
			     @Override
			     public boolean accept(File current, String name) 
			     {
			       return new File(current, name).isDirectory();
			     }
			   });
			   System.out.println(Arrays.toString(directories));
			   for(int j=0; j< directories.length; j++)
			    {
//				   System.out.println(directories[j].toString());
				   String author_cpp_dir = test_cpp_dir + directories[j] +"/";
//				   System.out.println(author_cpp_dir);
				   List test_file_paths = Util.listCPPFiles(author_cpp_dir);
				   for(int i=0; i< test_file_paths.size(); i++)
				   {
//						int testIDlength = test_file_paths.get(i).toString().length();   
					   //if the author has 6 cpp files
					//   int fileCount =6;
					   if(test_file_paths.size() >= fileCount)
					   {
						   System.out.println(author_cpp_dir);

						   String filePath = test_file_paths.get(i).toString();
						   //one empty file in each folder, skip that
						   System.out.println(filePath);  
						   
						   File srcFolder = new File(author_cpp_dir);
					    	File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/bigExperiments/randomAuthorProblems/"
						   +fileCount+"FilesAtLeastPerAuthor_2012_validation") ;
					      	File destFolder = new File(destFolderParent +"/"+ directories[j].toString()) ;
					    	if(!destFolder.exists())
					    	{
								///System.out.println(file.getAbsolutePath());
					    		destFolder.mkdirs();
							}
					    	
					    	
					  
					    	
						    List cpp_file_paths = Util.listCPPFiles(author_cpp_dir);
						    for(int k=0; k< fileCount; k++)
						    	
						    {
						    	File srcFile=new File(cpp_file_paths.get(k).toString());							    	
						    	File destFile= new File(destFolder + "/"+ srcFile.getName());
						    	FileUtils.copyFile(srcFile, destFile);
						     	if(!destFile.exists())
						    	{
									///System.out.println(file.getAbsolutePath());
						    		destFile.mkdirs();
								}
						    }
						    

					    	
					    	
					   }   
				   }	   
			    }
			}

		public static void copyAuthorsRandomlyWithAtLeastFileNumber(String test_cpp_dir, int fileCount, int year) throws IOException{

			   File file = new File(test_cpp_dir);
			   String[] directories = file.list(new FilenameFilter() 
			   {
			     @Override
			     public boolean accept(File current, String name) 
			     {
			       return new File(current, name).isDirectory();
			     }
			   });
			   System.out.println(Arrays.toString(directories));
			   for(int j=0; j< directories.length; j++)
			    {
//				   System.out.println(directories[j].toString());
				   String author_cpp_dir = test_cpp_dir + directories[j] +"/";
//				   System.out.println(author_cpp_dir);
				   List test_file_paths = Util.listCPPFiles(author_cpp_dir);
				   for(int i=0; i< test_file_paths.size(); i++)
				   {
//						int testIDlength = test_file_paths.get(i).toString().length();   
					   //if the author has 6 cpp files
					//   int fileCount =6;
					   if(test_file_paths.size() >= fileCount)
					   {
						   System.out.println(author_cpp_dir);

						   String filePath = test_file_paths.get(i).toString();
						   //one empty file in each folder, skip that
						   System.out.println(filePath);  
						   
						   File srcFolder = new File(author_cpp_dir);
					    	File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/bigExperiments/randomAuthorProblems/"
						   +fileCount+"FilesRandomAtLeastPerAuthor"+year) ;
					      	File destFolder = new File(destFolderParent +"/"+ directories[j].toString()) ;
					    	if(!destFolder.exists())
					    	{
								///System.out.println(file.getAbsolutePath());
					    		destFolder.mkdirs();
							}
					    	
					    	
					  
					    	
						    List cpp_file_paths = Util.listCPPFiles(author_cpp_dir);
						    Random no;
						    int use;
						    
						    for(int k=0; k < fileCount; k++)
						    {
						    	no = new Random();

								use = no.nextInt(cpp_file_paths.size());
								System.out.println("Size: "+cpp_file_paths.size() +" Using: "+use);

//						    	File srcFile=new File(cpp_file_paths.get(use).toString());
						   	File srcFile=new File(cpp_file_paths.get(k).toString());

								System.out.println(srcFile.getAbsolutePath());
								System.out.println(cpp_file_paths.get(k).toString());

						    	File destFile= new File(destFolder + "/"+ srcFile.getName());
						    	FileUtils.copyFile(srcFile, destFile);
						     	if(!destFile.exists())
						    	{
									///System.out.println(file.getAbsolutePath());
						    		destFile.mkdirs();
								}
						     	
						    }
						    

					    	
					    	
					   }   
				   }	   
			    }
			}

		
		
		
		public static void SplitDatasetInto2Easy(String testFolder){
		    

/*			
			String cppFileName=null;
		        List test_cpp_paths = Util.listCPPFiles(testFolder); //use this for preprocessing       
		 
		     File cpp_file=null;
		        for(int i=0; i< test_cpp_paths.size(); i++){
		        	cpp_file = new File(test_cpp_paths.get(i).toString());
			        
*/		        
        	File author = new File(testFolder);
		    String[] directories = author.list(new FilenameFilter() 
				   {
				     @Override
				     public boolean accept(File current, String name) 
				     {
				       return new File(current, name).isDirectory();
				     }
				   });  
		    
		        for (int i =0; i< directories.length; i++)
				   {
					   //authorname is directoryname - 1 because Andrew put an extra 0 at the end of the authorname
					   String authorName = directories[i].toString().substring(0, directories[i].toString().length()-1); 
					   String authorDir = testFolder + directories[i] + "/";
					   List test_cpp_paths = Util.listCPPFiles(authorDir); //use this for preprocessing 
					   File cpp_file=null;
				        for(int j=(test_cpp_paths.size()/2); j< test_cpp_paths.size(); j++){
				        	cpp_file = new File(test_cpp_paths.get(j).toString());
				        	System.out.println(cpp_file.getName());
				        	File txt = new File (cpp_file.getAbsolutePath().toString().substring(0, cpp_file.getAbsolutePath().toString().length()-3)+"txt");
				        	File dep = new File (cpp_file.getAbsolutePath().toString().substring(0, cpp_file.getAbsolutePath().toString().length()-3)+"dep");
				        	File ast = new File (cpp_file.getAbsolutePath().toString().substring(0, cpp_file.getAbsolutePath().toString().length()-3)+"ast");
				        	txt.delete();
				        	dep.delete();
				        	ast.delete();
				        	cpp_file.delete();
				        	}
				   }

		}
		
		public static void SplitDatasetInto2Difficult(String testFolder){
		    

/*			
			String cppFileName=null;
		        List test_cpp_paths = Util.listCPPFiles(testFolder); //use this for preprocessing       
		 
		     File cpp_file=null;
		        for(int i=0; i< test_cpp_paths.size(); i++){
		        	cpp_file = new File(test_cpp_paths.get(i).toString());
			        
*/		        
        	File author = new File(testFolder);
		    String[] directories = author.list(new FilenameFilter() 
				   {
				     @Override
				     public boolean accept(File current, String name) 
				     {
				       return new File(current, name).isDirectory();
				     }
				   });  
		    
		        for (int i =0; i< directories.length; i++)
				   {
					   //authorname is directoryname - 1 because Andrew put an extra 0 at the end of the authorname
					   String authorName = directories[i].toString().substring(0, directories[i].toString().length()-1); 
					   String authorDir = testFolder + directories[i] + "/";
					   List test_cpp_paths = Util.listCPPFiles(authorDir); //use this for preprocessing 
					   File cpp_file=null;
				        for(int j=0; j< (test_cpp_paths.size()/2); j++){
				        	cpp_file = new File(test_cpp_paths.get(j).toString());
				        	System.out.println(cpp_file.getName());
				        	File txt = new File (cpp_file.getAbsolutePath().toString().substring(0, cpp_file.getAbsolutePath().toString().length()-3)+"txt");
				        	File dep = new File (cpp_file.getAbsolutePath().toString().substring(0, cpp_file.getAbsolutePath().toString().length()-3)+"dep");
				        	File ast = new File (cpp_file.getAbsolutePath().toString().substring(0, cpp_file.getAbsolutePath().toString().length()-3)+"ast");
				        	txt.delete();
				        	dep.delete();
				        	ast.delete();
				        	cpp_file.delete();
				        	}
				   }

		}
		
		public static void main(String[] args) throws Exception, IOException, InterruptedException 
		{
		String test_cpp_dir = "test_cpp_dir";	
		int fileCount = 6;
//		copyAuthorsWithExactFileNumber(test_cpp_dir, fileCount);
		String parentDir = "parentDir";	
		String outputFolderName ="mergedAuthors";
	//	mergeSameAuthors(parentDir, outputFolderName);
		
        String folder = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2012MoreFileUsers/";

//		organizeByCountry(folder, "byCountry2014", 2014);
//		copyAuthorsWithAtLeastFileNumber(folder, 6);
		for(int i=9; i<10; i++){
		//	copyAuthorsWithExactFileNumber(folder, 9);
		//copyAuthorsWithAtLeastFileNumber(folder, i);
			copyAuthorsRandomlyWithAtLeastFileNumber(folder, i, 2012);
				}
		String bigFolderEasy = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2014complete_cpp_incremental_syntactic/7FilesPerAuthor_2014_easy/";
		String bigFolderDifficult = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2014complete_cpp_incremental_syntactic/7FilesPerAuthor_2014_difficult/";
	//	SplitDatasetInto2Easy(bigFolderEasy);
	//	SplitDatasetInto2Difficult(bigFolderDifficult);

		}
}
