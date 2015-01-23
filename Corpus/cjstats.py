import os
import sys
import re
import json

#
# Counts number of files per round based off filename.
# Run in root directory of files you want to search.
# CodeJamMetadata.json must be in the same directory as this script.
#

def get_problem_id(filename):
	regex = re.search('[0-9]+', filename, flags=0)
	return regex.group()

def get_username(filename):
	filename += os.path.splitext(filename)[0]
	return re.sub('p[0-9]+\.', '', filename)

metadatafile = open(os.path.dirname(os.path.realpath(__file__)) + "/CodeJamMetadata.json").read()
metadata = json.loads(metadatafile)


# hash: p# -> r#
# another hash: r# -> {user -> true}
prob_to_round = {}
#round_users = {}
round_users = []
round_to_desc = []

round_count = 0

for year_json in metadata['competitions']:
	year = year_json['year']
	for round_json in year_json['round']:
		description = round_json['desc']
		round_id = round_json['contest']
		#round_users[round_id] = {} #
		round_users.append({})
		round_to_desc.append(description)
		num_players = round_json['numPlayers']
		for problem_json in round_json['problems']:
			problem_name = problem_json['name']
			problem_id = problem_json['id']
			#prob_to_round[problem_id] = round_id #
			prob_to_round[problem_id] = round_count
		round_count += 1

# go through all files under the root directory
for (path, dirs, files) in os.walk('.'):
	for f in files:
		p_id = get_problem_id(f)
		u_name = get_username(f)
		round_users[prob_to_round[p_id]][u_name] = True

for i in range(len(round_users)):
	print round_to_desc[i]
	print len(round_users[i])
