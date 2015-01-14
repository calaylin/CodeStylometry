from urllib import urlopen
from urllib import urlretrieve
import json
import sys
import os

'''
download_url = "http://code.google.com/codejam/contest/" \
    + "scoreboard/do?cmd=GetSourceCode&contest=" \
    + contest \
    + "&problem=" \
    + problist[p_index]['id'] \
    + "&io_set_id=" \
    + str(set_id) \
    + "&username=" \
    + user \
    + "&csrfmiddlewaretoken="

target_dir = "c" + contest + "/p" + problist[p_index]['id']

if not os.path.exists(target_dir) :
    os.makedirs(target_dir)

target_file = target_dir + "/" + str(set_id) + "." \
    + user + ".zip"

#print target_file

urlretrieve(download_url,target_file)
dl_cnt += 1
'''

'''
public static String getDownloadURL(String roundNum, String problemNum, String name) {
        return "http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest="
                + roundNum
                + "&problem="
                + problemNum
                + "&io_set_id=0&username=" + name;
}
'''

# configfile = open("datasets.json").read()
# parsed_config = json.loads(configfile) -- json object
# {} -> hash and [] -> list

def get_download_url(round_id, problem_id, username):
    return "http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest=" \
                + round_id \
                + "&problem=" \
                + problem_id \
                + "&io_set_id=0&username=" \
                + username

def get_all_users(round_id, num_players):
    counter = 1
    users = []
    for pos in range(1, int(num_players), 30):
        meta_url = "http://code.google.com/codejam/contest/scoreboard/" \
            + "do?cmd=GetScoreboard&contest_id=" \
            + round_id \
            + "&show_type=all&start_pos=" \
            + str(pos) \
            + "&views_time=1&views_file=0&csrfmiddlewaretoken="
        meta_url_data = urlopen(meta_url).read()
        meta_json = json.loads(meta_url_data)
        for row in meta_json['rows']:
            users.append(row['n'])
            print "added user " + str(counter)
            counter += 1
    return users

def load_users():
    user_file = open('users.txt', 'r')
    data = user_file.read()
    users = data.splitlines()
    return users

users = load_users()
metadatafile = open("CodeJamMetadata.json").read()
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

# import shutil
# copyfile(src, dest)
#
# import zipfile
# import os.path
# fh = open('something.zip', 'rb')
# z = zipfile.ZipFile(fh)
# for name in z.namelist():
#    outpath = "C:\\asudhfkwjqehfkh"
#    z.extract(name, outpath)
# fh.close()