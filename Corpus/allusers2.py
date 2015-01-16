from urllib import urlopen
import json

#
# Gets all users who participated in the Google Code Jam competition.
# Posts results according to round number.
#

# gets a list of all users who participated in the round
def get_all_users(round_id, num_players):
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
			username = row['n']
			users.append(username)

#user_file = open('users.txt', 'w')
metadatafile = open(os.path.dirname(os.path.realpath(__file__)) + "/CodeJamMetadata.json").read()
metadata = json.loads(metadatafile)

# loop through all years
for year_json in metadata['competitions']:
	for round_json in year_json['round']:
		num_players = round_json['numPlayers']
		round_id = round_json['contest']
		users = get_all_users(round_id, num_players)
		round_file = open(round_id + '.txt', 'w')
		for user in users:
			user_file.write(user)
			user_file.write('\n')
		round_file.close()
