#import sys
from tkinter import *
from tkinter import filedialog

root = Tk("Text Editor")
text = Text(root)
text.grid()

def saveas():
    global text
    t = text.get("1.0", "end-1c")
    saveLoc = filedialog.asksaveasfilename()
    file1=open(saveLoc, "w+")
    file1.write(str(text))
    file1.close()

button=Button(root, text="Save", command=saveas)
button.grid()

root.mainloop()
