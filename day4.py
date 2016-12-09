import re, operator

data = []
with open('day4.txt', 'r') as file:
    data = file.readlines()

count = 0;
names = []
for row in data:
    a = re.match(r'([\w-]+)-([\d]+)\[(\w+)\].*', row)
    res = a.groups()
    name = res[0].replace("-", "")
    hist = dict((char, name.count(char)) for char in name)
    cs = res[2]

    listentry = [(x, -y) for (x, y) in list(hist.items())]
    items = sorted(listentry, key=operator.itemgetter(1, 0))
    okkey = "".join([item[0] for item in items])
    okkey = okkey[0:5]

    deconame = ""
    for char in name:
        deconame += chr((ord(char) - ord('a') + int(res[1])) % (ord('z') - ord('a') + 1) + ord('a'))

    if (cs == okkey):
        names.append((deconame, int(res[1])))
        count += int(res[1])

print("part I:", count)
for name in names:
    label, _ = name
    if 'objects' in label:
        print("part II:", name)