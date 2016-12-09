import re


input_string = ''
with open('day9.txt') as file:
    input_string = file.read()
    input_string = re.sub(r'\s', '', input_string)


def deco(string):
    match = re.search(r'\(\d+x\d+\)', string)

    if not match:
        return string
    else:
        match_numbers = re.findall(r'\d+', match.group())
        c, t = (int(n) for n in match_numbers)
        s, e = match.start(), match.end() 
        return string[0:s] + t * string[e:e+c] + deco(string[e+c:])

 
print("part I:", len(deco(input_string)))


def decomoreno(string):
    match = re.search(r'\(\d+x\d+\)', string)

    if not match:
        return len(string)
    else:
        match_numbers = re.findall(r'\d+', match.group())
        c, t = (int(n) for n in match_numbers)
        s, e = match.start(), match.end()
        return len(string[0:s]) + t * decomoreno(string[e:e+c]) + decomoreno(string[e+c:])

cacao = decomoreno(input_string)
print("part II:", cacao)
