import re


lines = []
with open('day7.txt', 'r') as file:
      lines += file.readlines()


def parse(line):
    exp = re.compile(r'[\[\]]')
    parts = exp.split(line)
    inside = [part for i, part in enumerate(parts) if i % 2 == 1]
    outside = [part for i, part in enumerate(parts) if i % 2 == 0]
    return (outside, inside)


def has_abba(string, length=2):
    for i in range(len(string) - length - length + 1):
        if string[i:i+length] == string[i+length+length-1:i+length-1:-1] and len(set(string[i:i+length])) == length:
            return True
    return False


def abba_in(string_list, length=2):
    return True in [has_abba(string, length) for string in string_list]


supports = 0
for line in [line.strip() for line in lines]:
    outside, inside = parse(line)
    if False in (not abba_in(inside), abba_in(outside)):
        continue
    else:
        supports += 1

print('TLSs:', supports)


def get_abas(string):
    return set([string[i:i+3] for i in range(len(string) - 2) if string[i] == string [i + 2]])


def to_bab(aba):
    if aba[0] != aba[2] or len(aba) != 3:
        raise AssertionError('Argument is not ABA!')
    return ''.join([aba[1], aba[0], aba[1]])


supports = 0
for line in [line.strip() for line in lines]:
    outside, inside = parse(line)
    abas = set([aba for string in outside for aba in get_abas(string)])
    babs = set([bab for string in inside for bab in get_abas(string)])
    for aba in abas:
        if to_bab(aba) in babs:
            supports += 1
            break

print('SSLs:', supports)
            
        


        
