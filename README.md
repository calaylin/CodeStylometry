SCAA
====
Runs joern on testCode, writes joern-tools script output to text files for each testCode file, 
extracts features from the text files to create an arff file that can be used in WEKA for machine learning.
(This project requires the development branches of joern and python-joern, and also joern-tools to be set up.  These three git repositories have dependencies and come with thorough documentation.)

1) Do preprocessing for all files in the directory structure, year-> author name -> all_cpp_files_ofauthor
run preprocessData(filePath) in FeatureCalculators.java
test_cpp_dir has all the cpp files of an author

2) Start writing the attribute declaration to arff (writes relation, selected attributes and at last @attribute 'authorName' {cyg4ever,darkKelvin, ....} after getting all the author names.  The last attribute defines your test classes.
After preprocessing, run the main method in FeatureExtractor.java 
test_dir has all the .txt files written from joern, can be the same as test_cpp_dir
output_filename is your arff file path

3) Extract features: from all text files in the directory structure, year-> author name -> all_txt_files_ofauthor (output from joern) extract the desired features to be written to feature vectors.

Once the arff file is written, open it in WEKA or call WEKA from java and use the necessary classifiers, and attribute selection methods to do authorship attribution.

SCAA
