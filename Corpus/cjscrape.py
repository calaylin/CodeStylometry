from urllib import urlopen
from urllib import urlretrieve
import json
import sys
import os
import zipfile
import shutil
import multiprocessing

#
# Scrapes Google Code Jam data, and extracts the C/C++/Python source files.
#
# The directory structure and naming convention of the data is as follows:
#
# ./codejamdata/ |--> c/   | --> username0 | --> p[problem_number].[user_name]0.c
#                |         |               | --> p2453486.Bob0.c
#                |         |               | --> etc...
#                |         |
#                |         | --> name0     | --> etc...
#                |         |               | --> etc...
#                |         |               | --> etc...
#                |         |
#                |         | --> another0  | --> etc...
#                |                         | --> etc...
#                |
#                |--> cpp/ | --> etc...    | --> etc...
#                |         |               | --> etc...             
#                |         |
#                |         | --> etc...    | --> etc...
#                |
#                |--> py/  | --> etc...    | --> etc...
#

# returns the URL to download the user submission
def get_download_url(round_id, problem_id, username):
    return "http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest=" \
                + round_id \
                + "&problem=" \
                + problem_id \
                + "&io_set_id=0&username=" \
                + username

# scrapes the C/C++/Python files of the given round
def scrape(round_id, problems, script_path):

    # load list of users
    user_file = open(script_path + '/users/' + round_id + '.txt', 'r')
    users = user_file.read().splitlines()
    
    # loop through problems in the round
    for problem_json in problems:
        problem_id = problem_json['id']

        # loop through users who participated in the round
        for username in users:
            download_url = get_download_url(round_id, problem_id, username)

            # print and flush URL
            print download_url
            sys.stdout.flush()
            
            # make temp directory for storing zips
            tempdir = round_id + 'temp'
            if not os.path.exists(tempdir):
                os.makedirs(tempdir)

            # download and read zip
            target_zip = tempdir + '/' + problem_id + '.' + username + '0.zip'
            urlretrieve(download_url,target_zip)
            zip_header = open(target_zip, 'rb')

            # try-except in case of a bad header
            try:
                my_zip = zipfile.ZipFile(zip_header)

                # loop through each file in the zip file
                for my_file in my_zip.namelist():

                    # check for C/C++/Python source
                    if my_file.endswith(('.c', '.cpp', '.py')):
                        target_source = username + '0' # destination of source files
                        file_newname = 'p' + problem_id + '.' + username + '0.' # appropriate name for file
                        if my_file.endswith('.c'):
                            file_newname += 'c'
                            target_source = 'c/' + target_source
                        elif my_file.endswith('.cpp'):
                            file_newname += 'cpp'
                            target_source = 'cpp/' + target_source
                        else:
                            file_newname += 'py'
                            target_source = 'py/' + target_source
                        target_source = 'codejamfolder/' + target_source

                        # make directory for language and author
                        if not os.path.exists(target_source):
                            os.makedirs(target_source)

                        # extract and rename source file
                        my_zip.extract(my_file, target_source)
                        os.rename((target_source + '/' + my_file), (target_source + '/' + file_newname))
                        
                        # print location of extracted source file
                        print target_source + '/' + file_newname
                        sys.stdout.flush()
            except:
                print "error:", sys.exc_info()[0] # can happen if the user didn't do a problem
                sys.stdout.flush()

            # delete temp directory
            if os.path.exists(tempdir):
                shutil.rmtree(tempdir)
    return

# main section of script
if __name__ == '__main__':
    script_path = os.path.dirname(os.path.realpath(__file__))
    metadatafile = open(script_path + "/CodeJamMetadata.json").read()
    metadata = json.loads(metadatafile)

    # loop through years
    for year_json in metadata['competitions']:
        year = year_json['year']

        # loop through rounds
        for round_json in year_json['round']:
            round_id = round_json['contest']
            problems = round_json['problems']

            # run scraper on current round
            scraper = multiprocessing.Process(target=scrape, args=(round_id, problems, script_path))
            scraper.start()
