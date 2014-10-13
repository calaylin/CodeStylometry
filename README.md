SCAA
====
Runs joern on testCode, writes joern-tools script output to text files for each testCode file, 
extracts features from the text files to create an arff file that can be used in WEKA for machine learning.

(This project requires the development branches of joern and python-joern, and also joern-tools to be set up.  These three git repositories have dependencies and come with thorough documentation.)

1) Do preprocessing for all files in the directory structure, year-> author name -> all_cpp_files_ofauthor
run preprocessDataToTXTdepAST(filePath) in FeatureCalculators.java test_cpp_dir has all the cpp files of an author.  Check if all dep, txt, and ast files are created correctly. (eg, if the cpp file has only comments and no code, the dep, txt and ast files will be empty. Exclude such cases from authorship attribution.) If you only want syntactic features, make sure that in joern-tools, change astLabel.py's lines:

        if len(children) == 0:
            node.attr['label'] = attrDict['node']
        else:
            node.attr['label'] = attrDict['type']
            
            to


        if len(children) == 0:
            node.attr['label'] = attrDict['type']
        else:
            node.attr['label'] = attrDict['type']

2) Start writing the attribute declaration to arff (writes relation, selected attributes and at last @attribute 'authorName' {cyg4ever,darkKelvin, ....} after getting all the author names.  The last attribute defines your test classes.
After preprocessing, run the main method in FeatureExtractor.java 
test_dir has all the .txt files written from joern, can be the same as test_cpp_dir
output_filename is your arff file path
If you want only syntactic features from the syntactic dataset that has only node types, make sure to select the correct ASTTypes in FeatureExtractor.java

3) Extract features: from all text files in the directory structure, year-> author name -> all_txt_files_ofauthor (output from joern) extract the desired features to be written to feature vectors.  In order to extract some layout and other lexical features, run Driver.java in Naive Baseline.  If you want to merge the arffs from feature extractor and driver, run MergeArffFiles.java. (the instance order is important, modify code accordingly.)

4)Once the arff file is written, open it in WEKA or call WEKA from java and use the necessary classifiers, and attribute selection methods to do authorship attribution.  AuthorClassification.java can also be used with a random forest and relaxed attribution.

SCAA
