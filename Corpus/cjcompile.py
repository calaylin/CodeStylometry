import os
import sys

# os.listdir('.') --> list files in cwd
# os.path.isfile(f) --> if f is a file vs isdir

# os.system("ls -l")

# os.path.splitext(filename)[0] --> get filename w/o extension
# gcc test2/test.c -o test2/testout

# sys.argv == list of args
# len(sys.argv) == number of args (including file)
# example...
# python test.py arg1 args2 arg3
# sys.argv == ['test.py', 'arg1', 'arg2', 'arg3']

# make a stack
# add cwd to stack
# while stack has contents
	# cwd = stack.pop
	# get list of contents in cwd
	# for each item in the list
		# if it's a directory, add it to the stack
		# if it's a c/cpp file then compile it
'''
directories = []
directories.append(os.getcwd())
while len(directories) > 0:
	directory = directories.pop()
	for my_file in os.listdir(directory):
		if os.path.isdir(my_file): # will not work...listdir gives relative path -> need full path name
			directories.append(my_file)
		elif os.path.isfile(my_file) and my_file.endswith(('.c', '.cpp')):
			#compile
'''
'''
for (path, dirs, files) in os.walk('.'):
	print path # directory path
	print dirs # list of folders
	print files # list of files
'''
myargs = sys.argv
myargs.pop(0)
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
			command += os.path.splitext(f)[0]
			for arg in myargs:
				command += " "
				command += arg
			os.system(command)
			print command
'''
for myarg in sys.argv:
	print myarg
'''