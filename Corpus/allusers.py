from urllib import urlopen
import json

#
# Gets all users who participated in the Google Code Jam competition.
# Posts results in a single text file.
#

users = {} # dictionary of all discovered users

# adds all users who participated in the given round to the dictionary
def get_all_users(round_id, num_players):
	global users
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
			username = row['n']
			users[username] = True

user_file = open('users/users.txt', 'w')
metadatafile = open(os.path.dirname(os.path.realpath(__file__)) + "/CodeJamMetadata.json").read()
metadata = json.loads(metadatafile)

# loop through all years
for year_json in metadata['competitions']:
	qual_round = year_json['round'][0] # get only the qualification round
	num_players = qual_round['numPlayers']
	round_id = qual_round['contest']
	get_all_users(round_id, num_players) # get users for the qualification round of the given year

# write out all users
for user in users.keys():
	user_file.write(user)
	user_file.write('\n')
user_file.close()
