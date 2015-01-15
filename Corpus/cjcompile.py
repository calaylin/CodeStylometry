import os
import sys

myargs = sys.argv
myargs.pop(0)

# go through all files under the root directorys
for (path, dirs, files) in os.walk('.'):
	for f in files:
		if f.endswith(('.c', '.cpp')):
			if f.endswith('.c'):
				command = "gcc "
			else:
				command = "g++ "
			command += path
			command += '/'
			command += f
			command += " -o "
			command += path
			command += '/'
			command += os.path.splitext(f)[0] # remove file extension
			for arg in myargs:
				command += " "
				command += arg
			os.system(command)
			print command
