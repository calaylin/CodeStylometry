import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


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
		File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/"
					   +fileCount+"FilesExactlyPerAuthor_2012") ;
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

	
		
		
		
		
		
		
		
		public static void copyAuthorsWithLOCFileNumber(String test_cpp_dir, String destinationFolder, int LOC, int minFile, boolean exactFileNumber) throws IOException{
			File destFolderParent = new File(destinationFolder) ;
		  	if(!destFolderParent.exists())
	    	{
				///System.out.println(file.getAbsolutePath());
		  		destFolderParent.mkdirs();
			}
			
		   File file = new File(test_cpp_dir);
		   String ext;
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
			   int fileCounter=0;
			   int fileCounterExact=0;
			   File code=null;
			   System.out.println(directories[j].toString());
			   String author_cpp_dir = test_cpp_dir + directories[j] +"/";
			   List test_file_paths = Util.listCPPFiles(author_cpp_dir);
			   for(int k=0; k< test_file_paths.size(); k++)
			   {
				    code = new File(test_file_paths.get(k).toString());
				   if(code.isFile() && fileCounter <= minFile){
				   int lines = Util.getLines(code);
				   if(lines > LOC){
					   fileCounter++;}
				   }}
					  
				   for(int i=0; i< test_file_paths.size(); i++)
				   {
					   if(((exactFileNumber == true) && fileCounterExact < minFile ) ||
							   (exactFileNumber == false)   ){
					    code = new File(test_file_paths.get(i).toString());
						   int lines = Util.getLines(code);

					   if(fileCounter >= minFile){
						   if(lines > LOC){
							   fileCounterExact++;				
							   System.out.println(directories[j].toString());
							   ext = FilenameUtils.getExtension(test_file_paths.get(i).toString());
							   File srcTXT= null;
							   File srcDEP= null;
							   File srcAST= null;
							   File destFile = null;
							   File destTXT= null;
							   File destDEP= null;
							   File destAST= null;
							   
						   if(ext.length()==3){
							   System.out.println("extension:"+ext);


						   srcTXT= new File(test_file_paths.get(i).toString().substring(0, code.getPath().length()-3)+"txt");
						   srcDEP= new File(test_file_paths.get(i).toString().substring(0, code.getPath().length()-3)+"txt");
						   srcAST= new File(test_file_paths.get(i).toString().substring(0, code.getPath().length()-3)+"txt");

					   
				      	 destFile = new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName()) ;

				    	 destTXT = new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName().substring(0,code.getName().length()-3) +"txt") ;
				    	 destDEP =  new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName().substring(0,code.getName().length()-3) +"dep") ;
				    	 destAST =  new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName().substring(0,code.getName().length()-3) +"ast") ;
						   }
						   
						   
						   if(ext.length()==2){
							   System.out.println("extension:"+ext);

/*						   srcTXT= new File(test_file_paths.get(i).toString().substring(0, code.getPath().length()-2)+"txt");
						   srcDEP= new File(test_file_paths.get(i).toString().substring(0, code.getPath().length()-2)+"txt");
						   srcAST= new File(test_file_paths.get(i).toString().substring(0, code.getPath().length()-2)+"txt");

					   
				      	 destFile = new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName()) ;

				    	 destTXT = new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName().substring(0,code.getName().length()-2) +"txt") ;
				    	 destDEP =  new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName().substring(0,code.getName().length()-2) +"dep") ;
				    	 destAST =  new File(destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName().substring(0,code.getName().length()-2) +"ast") ;*/
						   }
						   File srcFolder = new File(author_cpp_dir); 

				    	//make sure source exists
				    	if(!srcFolder.exists())
				    	{
				           System.out.println("Directory does not exist.");
				           //just exit
				           System.exit(0);
				        }
				    	else
				        {
				 
				           try{							   if(ext.length()==3){

				       /* 	   String text =Util.readFile(test_file_paths.get(i).toString());
				        	   Util.writeFile(text, destFolderParent +"/"+ directories[j].toString()
				      			+"/"+ code.getName(), true);*/
						      	    FileUtils.copyFile(code, destFile);
						      	    FileUtils.copyFile(srcTXT, destTXT);
						      	    FileUtils.copyFile(srcDEP, destDEP);
						      	    FileUtils.copyFile(srcAST, destAST);
				           }

						      	} catch (IOException e) {
						      	    e.printStackTrace();
						      		System.out.println(code.getAbsolutePath());

						      	}
				        }
				   }   }
			   }	   
		    }}
		}

		
		

		
		
		public static void copyCAuthorsWithAtLeastFileNumber(String test_c_dir, int fileCount) throws IOException{

			   File file = new File(test_c_dir);
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
				   String author_cpp_dir = test_c_dir + directories[j] +"/";
//				   System.out.println(author_cpp_dir);
				   List test_file_paths = Util.listCFiles(author_cpp_dir);
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
					    	File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/largeScale/"
						   +fileCount+"FilesAtLeastPerAuthor_2014_C/") ;
					      	File destFolder = new File(destFolderParent +"/"+ directories[j].toString()) ;
					    	if(!destFolder.exists())
					    	{
								///System.out.println(file.getAbsolutePath());
					    		destFolder.mkdirs();
							}
					    	
					    	
					  
					    	
						    List cpp_file_paths = Util.listCFiles(author_cpp_dir);
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
		
		public static void copyPythonAuthorsWithAtLeastFileNumber(String test_py_dir, int fileCount) throws IOException{

			   File file = new File(test_py_dir);
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
				   String author_cpp_dir = test_py_dir + directories[j] +"/";
//				   System.out.println(author_cpp_dir);
				   List test_file_paths = Util.listPythonFiles(author_cpp_dir);
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
					    	File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/pythonDatasets/"
						   +fileCount+"FilesAtLeastPerAuthor_2014_Python/") ;
					      	File destFolder = new File(destFolderParent +"/"+ directories[j].toString()) ;
					    	if(!destFolder.exists())
					    	{
								///System.out.println(file.getAbsolutePath());
					    		destFolder.mkdirs();
							}
					    	
					    	
					  
					    	
						    List cpp_file_paths = Util.listPythonFiles(author_cpp_dir);
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
					    	File destFolderParent = new File("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/largeScale/"
						   +fileCount+"FilesAtLeastPerAuthor_2012/") ;
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
					   if(test_file_paths.size() >= (fileCount+3))
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
		
		public static int AvgLineOfCodePerFile(String folder) throws IOException{
/*			
		  	File author = new File(folder);
		    String[] directories = author.list(new FilenameFilter() 
				   {
				     @Override
				     public boolean accept(File current, String name) 
				     {
				       return new File(current, name).isDirectory();
				     }
				   });
		    int authorCount=directories.length;*/
			
			   List test_cpp_paths = Util.listCPPFiles(folder); //use this for preprocessing 
			   int numberFiles=test_cpp_paths.size();
			   int totalLines=0;
			   int avgLines=0;
			   for(int j=0; j< (test_cpp_paths.size()); j++){
		        	
				   FileReader fr=new FileReader(test_cpp_paths.get(j).toString());
				   BufferedReader br=new BufferedReader(fr); 
				   int i=0;
				   boolean isEOF=false;
				   do{
				   String t=br.readLine();
				   if(t!=null){
				   isEOF=true;
				   t=t.replaceAll("\\n|\\t|\\s", "");
				   if((!t.equals("")) && (!t.startsWith("//"))) {
				   i = i + 1;
				   }
				   }
				   else {
				   isEOF=false;
				   }
				   }while(isEOF);
				   br.close();
				   fr.close();
				   totalLines=totalLines+i;
			   }  
			   
			   avgLines=totalLines/numberFiles;
			   
			   
			   
			return avgLines; 
			
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
		
		
		public static void showSameAuthors(String parentDir1, String parentDir2)
		{
			
					  File authors1 = new File(parentDir1);

				   String[] authorDirectories1 = authors1.list(new FilenameFilter() 
				   {
				     @Override
				     public boolean accept(File current, String name) 
				     {
				       return new File(current, name).isDirectory();
				     }
				   });	
				   
				   File authors2 = new File(parentDir2);

				   String[] authorDirectories2 = authors2.list(new FilenameFilter() 
				   {
				     @Override
				     public boolean accept(File current, String name) 
				     {
				       return new File(current, name).isDirectory();
				     }
				   });		
				   
				   for(int k=0; k< authorDirectories1.length; k++)
				    { 		
					   for(int l=0; l< authorDirectories2.length; l++)
					    { 	
						   if(authorDirectories1[k].toString().equals(authorDirectories2[l].toString()))
						   {				
						   List test_cpp_paths = Util.listCPPFiles(parentDir1+authorDirectories1[k]+"/"); //use this for preprocessing 
						   File cpp_file=null;
					        if(test_cpp_paths.size()==8){
					        	   System.out.println(authorDirectories1[k]);
						   	}
					    }
				    }
			    }}
		public static void createMalloryDatasets(String trainFolder,String malloryFolder, String copy2Folder, int trainingFiles, int testFiles, int datasets) throws IOException{
			File authors = new File(trainFolder);
			   String[] authorDirectories = authors.list(new FilenameFilter() 
			   {
			     @Override
			     public boolean accept(File current, String name) 
			     {
			       return new File(current, name).isDirectory();
			     }
			   });	
				File mallorys = new File(malloryFolder);
				   String[] malloryDirectories = mallorys.list(new FilenameFilter() 
				   {
				     @Override
				     public boolean accept(File current, String name) 
				     {
				       return new File(current, name).isDirectory();
				     }
				   });	
			   
				   
				   
				   
				for(int i=0; i<datasets; i++){
					List<String> training_problems = new ArrayList<String>(trainingFiles);
					training_problems.clear();
					List<String> mallory_test = new ArrayList<String>(testFiles);
					List<String> other_test = new ArrayList<String>(testFiles);
					List<String> mallory_train = new ArrayList<String>(trainingFiles);

					int iteration = trainingFiles;
					 int temp=0;
					 
			   for(int k=0; k< iteration; k++)
			    { 				
					   List train_cpp_paths = Util.listCPPFiles(trainFolder+authorDirectories[(k+i) % authorDirectories.length]); //use this for preprocessing 
					  	
					   
						
						System.out.println(temp);
						 File srcFile=new File(train_cpp_paths.get(temp).toString());
						
				    	if (!training_problems.contains(srcFile.getName().toString().substring(0, 24)))
				     	{training_problems.add(srcFile.getName().toString().substring(0, 24));
				    	File destFile= new File(copy2Folder + "malloryDataset_" + i+ "/trainingDocs_ABCDEFGH/"+ srcFile.getName());
				    	
				    	FileUtils.copyFile(srcFile, destFile);
				     	if(!destFile.exists())
				    	{
				    		destFile.mkdirs();
						}		
				     	
				  //   	if(!training_problems.contains(train_cpp_paths.get(k).toString().substring(0, 24)))
					     	training_problems.add(srcFile.getName().toString().substring(0, 24));
							   List destFileSize=  Util.listCPPFiles(copy2Folder + "malloryDataset_" + i+ "/trainingDocs_ABCDEFGH/");
						    	temp= destFileSize.size();

			    }  
				    	else
				    		{iteration++;}
			    }
				System.out.println("Training problems: "+training_problems);

				
				
			   for(int mal=0; mal< malloryDirectories.length; mal++){
					mallory_train.clear();

			     //	  List train_cpp_paths_mallory = Util.listCPPFiles(trainFolder+malloryDirectories[trainingFiles+1]+"/");
			     	  List train_cpp_paths_mallory = Util.listCPPFiles(malloryFolder+malloryDirectories[(mal+i) % malloryDirectories.length]+"/");
			     	  List train_cpp_paths_other = Util.listCPPFiles(malloryFolder+malloryDirectories[(mal+i+1) % malloryDirectories.length] +"/");

		     	  for(int mal_train=0; mal_train < trainingFiles+testFiles ;mal_train++ ){
					     File malFile=new File(train_cpp_paths_mallory.get(mal_train).toString());							    	
					     File otherFile=new File(train_cpp_paths_other.get(mal_train).toString());							    	

		     		 mallory_train.add(malFile.getName().toString().substring(0, 24));
		     		 other_test.add(otherFile.getName().toString().substring(0, 24));

		     	  }
		     	  
		     	  
					System.out.println("Training problems: "+mallory_train);

		     		 if(mallory_train.containsAll(training_problems)==true){
		     			 
							System.out.println("match problems: "+mallory_train);
		     			mal= malloryDirectories.length;

					     int temp_other=0;

					     for(int copying=0; copying < train_cpp_paths_mallory.size(); copying++){
						      File malloryFile = new File(train_cpp_paths_mallory.get(copying).toString());							    	
					    	 if(training_problems.contains(malloryFile.getName().toString().substring(0, 24))){
				    	File destFile= new File(copy2Folder + "malloryDataset_" + i+ "/trainingDocs_mallory/"
					    	 + malloryFile.getName());
				    	FileUtils.copyFile(malloryFile, destFile);
				    	
				     	if(!destFile.exists())
				    	{
				    		destFile.mkdirs();
						}	
				     	
		     		 }
					    	 if(!training_problems.contains(malloryFile.getName().toString().substring(0, 24)))
					    	 { 
							    	File destFile= new File(copy2Folder + "malloryDataset_" + i+ "/testDocs_mallory/"+ malloryFile.getName());
							    	FileUtils.copyFile(malloryFile, destFile);
							     	if(!destFile.exists())
							    	{
							    		destFile.mkdirs();
									}	
							     	
					     		 }
		     		 
					     
					   
						      File otherFile = new File(train_cpp_paths_other.get(copying).toString());
		     			if(!training_problems.contains(otherFile.getName().toString().substring(0, 24)))
		     			{
		     				if(temp_other < testFiles){
		     				File destFileo= new File(copy2Folder + "malloryDataset_" + i+ "/testDocs_other/"
							    	 + otherFile.getName());
						    	FileUtils.copyFile(otherFile, destFileo);
						    	
						     	if(!destFileo.exists())
						    	{
						    		destFileo.mkdirs();
								}
						     	List destFolder= Util.listCPPFiles(copy2Folder + "malloryDataset_" + i+ 
						     			"/testDocs_other/");
						     	temp_other= destFolder.size();
		     				}
		     			}
					     	  

						     	
				     		 }	}
		     			}
				}
		     		 
		     		 }
			   			     	
	
			
				   
		
		public static void main(String[] args) throws Exception, IOException, InterruptedException 
		{
		String test_cpp_dir = "githubManySmallSnippets/";	
		int LOC = 10 ;
		int minFile = 5;
		copyAuthorsWithLOCFileNumber(test_cpp_dir, "githubManySmallSnippets" + "_minLOC"+LOC + "_minFiles"+
		minFile+"/", LOC, minFile, true);

//		copyAuthorsWithExactFileNumber(test_cpp_dir, fileCount);
		int fileCount = 20;
		String parentDir = "parentDir";	
		String outputFolderName ="mergedAuthors";
	//	mergeSameAuthors(parentDir, outputFolderName);

		
//createMalloryDatasets(String test_cpp_dir, String copy2Folder, int trainingFiles, int testFiles, int datasets) throws IOException{
		String trainFolder=
				"/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2012MoreFileUsers/";
		String malloryFolder="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/forMallory/14FilesAtLeastPerAuthor_2014_mallory/";
		String copy2Folder="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/forMallory/mallory_new/";

	//	createMalloryDatasets(trainFolder, malloryFolder, copy2Folder, 8, 6, 150);
		
//		organizeByCountry(folder, "byCountry2014", 2014);
//        copyAuthorsWithAtLeastFileNumber(trainFolder, 9);
		for(int i=14; i<15; i++){
	        String folder = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2012MoreFileUsers/";

	    //    System.out.println(i+" files: "+AvgLineOfCodePerFile(folder));

		//	copyAuthorsWithExactFileNumber(folder, 8);
		//copyAuthorsWithAtLeastFileNumber(folder, 8);
		//	copyAuthorsRandomlyWithAtLeastFileNumber(folder, i, 2012);
				}
		String bigFolderEasy = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2014Cprogrammers/";
		String bigFolderDifficult = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/difficultyExp/12FilesPerAuthor_2014_difficult/";
	//	SplitDatasetInto2Easy(bigFolderEasy);
	//	SplitDatasetInto2Difficult(bigFolderDifficult);
		String folder1 ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2012MoreFileUsers/";
		String folder2 ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2014complete_cpp/";

		//showSameAuthors(folder1, folder2);

		}
}
