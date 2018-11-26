import turtle
import time

f = open ("coordinates.txt", "r")
coordinates = f.readlines()

number = ""
enclose = []
for a in coordinates[0]:
    if a != "," and a != "\n":
        number = number + a
    elif a == ",":
        enclose.append(int(number)*20)
        number = ""

turtle.forward(enclose[0])
turtle.left(90)
turtle.forward(enclose[1])
turtle.left(90)
turtle.forward(enclose[0])
turtle.left(90)
turtle.forward(enclose[1])
turtle.left(90)
enclose = []

for b in coordinates:
    for a in b:
        if a != "," and a != "\n":
            number = number + a
        elif a == ",":
            enclose.append(int(number)*20)
            number = ""
    if len(enclose) > 2:
        turtle.penup()
        turtle.setposition(enclose[0], enclose[1])
        turtle.pendown()
        turtle.forward(enclose[2])
        turtle.left(90)
        turtle.forward(enclose[3])
        turtle.left(90)
        turtle.forward(enclose[2])
        turtle.left(90)
        turtle.forward(enclose[3])
        turtle.left(90)
    enclose = []

time.sleep(10)

