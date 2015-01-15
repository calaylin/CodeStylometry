from urllib import urlopen
from urllib import urlretrieve
import json
import sys
import os
import zipfile
import shutil

script_path = os.path.dirname(os.path.realpath(__file__))

def get_download_url(round_id, problem_id, username):
    '''
    round_id = '7214486'
    problem_id = '5722683480211456'
    username = 'Gennady.Korotkevich'
    '''
    return "http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest=" \
                + round_id \
                + "&problem=" \
                + problem_id \
                + "&io_set_id=0&username=" \
                + username

def load_users():
    user_file = open(script_path + '/users.txt', 'r')
    data = user_file.read()
    users = data.splitlines()
    return users
'''
# make temp directory for zips
if not os.path.exists('temp'):
    os.makedirs('temp')
'''
#make codejam dir
if not os.path.exists('codejamfolder'):
    os.makedirs('codejamfolder')
users = load_users()
metadatafile = open(script_path + "/CodeJamMetadata.json").read()
metadata = json.loads(metadatafile)
for year_json in metadata['competitions']:
    year = year_json['year']
    print "on year: " + year
    for round_json in year_json['round']:
        round_desc = round_json['desc']
        round_id = round_json['contest']
        num_players = round_json['numPlayers']
        print "on " + round_desc
        #users = get_all_users(round_id, num_players)
        #print "got users"
        for problem_json in round_json['problems']:
            problem_name = problem_json['name']
            problem_id = problem_json['id']
            for username in users:
                download_url = get_download_url(round_id, problem_id, username)
                # alsdkjfa;lsdkfj;asldkjf;alsdkfj
                print download_url
                # need to send to someplace, unzip, check extension, and copy/rename
                # create folders for zips like rosenblum does
                # and put the zips there
                #target_zip = 'temp/problem_num.username0.zip' ##############

                # make temp here
                if not os.path.exists('temp'):
                    os.makedirs('temp')

                target_zip = 'temp/' + problem_id + '.' + username + '0.zip' ## verify later
                urlretrieve(download_url,target_zip)
                # python can tell the contents of zips (?)
                # check if these are c/cpp
                # if so, extract and put them in some other folder that happens to be more organized
                # authorname -> authorname0 or 0authorname0
                # root -> author -> p[problem number].username.cpp
                zip_header = open(target_zip, 'rb')

                # TODO add try-catch here
                try:
                    my_zip = zipfile.ZipFile(zip_header)
                    #files = my_zip.namelist()
                    #c_or_cpp_files = []
                    for my_file in my_zip.namelist():
                        if my_file.endswith(('.c', '.cpp')):
                            #c_or_cpp_files.append(my_file)
                            #extract! :DDDDDDDDD
                            # my_zip.extract(my_file, 'path to put file')
                            #target_source = 'codejamfolder/username0/ dont forget to make dir if it doesnt exist' ###

                            target_source = 'codejamfolder/' + username + '0' ## make if non-existent
                            if not os.path.exists(target_source):
                                os.makedirs(target_source)

                            # source dir can't have the rename thingy
                            # need to extract, copy/paste, then delete
                            my_zip.extract(my_file, target_source) #### may need full path for my_file
                            # might wanna put a print statement here
                            #os.rename
                            file_newname = 'p' + problem_id + '.' + username + '0.'
                            if my_file.endswith('.c'):
                                file_newname += 'c'
                            else:
                                file_newname += 'cpp'
                            #naming convention: p[problem num].[username]0.c or cpp
                            os.rename((target_source + '/' + my_file), (target_source + '/' + file_newname))
                            print target_source + '/' + file_newname
                except:
                    print "error:", sys.exc_info()[0]
                # delete zip (or temp dir) here
                if os.path.exists('temp'):
                    #os.makedirs('temp')
                    shutil.rmtree('temp')
