from urllib import urlopen
import json

users = {}

def get_all_users(round_id, num_players):
	global users
	counter = 1
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
			#users.append(row['n'])
			username = row['n']
			users[username] = True
			print "added user " + str(counter)
			counter += 1
	#return users

user_file = open('users.txt', 'w')
metadatafile = open("CodeJamMetadata.json").read()
metadata = json.loads(metadatafile)
for year_json in metadata['competitions']:
	#for round_json in year_json['round']:
		#round_id = round_json['contest']
	qual_round = year_json['round'][0]
	num_players = qual_round['numPlayers']
	round_id = qual_round['contest']
	get_all_users(round_id, num_players)
	#
	for user in users.keys():
		print user
		user_file.write(user)
		user_file.write('\n')
	#
user_file.close()