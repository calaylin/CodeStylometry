import os
import sys

#
# Usage: python /directory/path/to/cjcompile.py [compiler flags]
#
# Compiles all C/C++ source files in the current working directory.
# Also recursively compiles all C/C++ source files in all subdirectories.
#

flags = sys.argv
flags.pop(0)

# go through all files under the root directory
for (path, dirs, files) in os.walk('.'):
	for f in files:
		# check if file is a C or C++ file
		if f.endswith(('.c', '.cpp')):
			if f.endswith('.c'): # C file
				command = "gcc "
			else: # C++ file
				command = "g++ "

			# add full directory path of the source file
			command += path
			command += '/'
			command += f

			# add full directory path of the executable
			command += " -o "
			command += path
			command += '/'
			command += os.path.splitext(f)[0] # remove file extension

			# adding in compiler flags (specified in the arguments)
			for flag in flags:
				command += " "
				command += flag

			# compile
			os.system(command)
			print command
