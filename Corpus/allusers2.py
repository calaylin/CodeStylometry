from urllib import urlopen
import json
import os

#
# Gets all users who participated in the Google Code Jam competition.
# Posts results according to round number.
#

# writes a list of all users who participated in the round
def get_all_users(round_id, num_players):
	round_file = open('users/' + round_id + '.txt', 'w')

	# loop through each page of users
	for pos in range(1, int(num_players), 30):
		meta_url = "http://code.google.com/codejam/contest/scoreboard/" \
			+ "do?cmd=GetScoreboard&contest_id=" \
			+ round_id \
			+ "&show_type=all&start_pos=" \
			+ str(pos) \
			+ "&views_time=1&views_file=0&csrfmiddlewaretoken="
		print meta_url
		meta_url_data = urlopen(meta_url).read()
		meta_json = json.loads(meta_url_data)

		# find and print usernames
		for row in meta_json['rows']:
			username = row['n']
			round_file.write(username)
			round_file.write('\n')
			print username
	round_file.close()

# load JSON
metadatafile = open(os.path.dirname(os.path.realpath(__file__)) + "/CodeJamMetadata.json").read()
metadata = json.loads(metadatafile)

# loop through all years
for year_json in metadata['competitions']:
	for round_json in year_json['round']:
		num_players = round_json['numPlayers']
		round_id = round_json['contest']
		get_all_users(round_id, num_players)
