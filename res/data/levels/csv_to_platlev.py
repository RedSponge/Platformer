filename = raw_input("Filename: ")
f = open(filename, "r")
text = f.read()
print text
lines = text.split("\n")
for i in range(len(lines)):
    lines[i] = lines[i].split(",")

newfile = open(filename + "_final.platlev", "w+")
for line in lines:
    for num in line:
        if num != '':
            newfile.write(str(int(num) + 1) + " ")
    newfile.write("\n")
f.close()
newfile.close()

'''

public static void sayHi() {
    say(age)
}

'''
