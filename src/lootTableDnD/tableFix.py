import sys

name = input("Enter a file name: ")
try:
	file = open(name, 'r')
except:
	print("Invalid file\n")
	sys.exit(0)
content = file.read()
file.close()

newContent = ""
spot = 0
firstQuote = 0
secondQuote = 0

while secondQuote != -1 and firstQuote != -1:
	firstQuote = content.find("\"", spot)
	secondQuote = content.find("\"", firstQuote+1)
	newContent += content[spot:firstQuote]
	working = content[firstQuote:secondQuote+1]
	working = working.replace("\n", "")
	newContent += working
	spot = secondQuote+1

file = open("new"+name, 'w')
file.write(newContent)
file.close()
