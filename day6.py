input = []
with open('day6.txt', 'r') as file:
    input += [line.strip() for line in file.readlines()]

nchar = len(input[0])

positions = [ [c[n] for c in input] for n in range(nchar) ]

most_common = []
least_common = []
for position in positions:
    most_common.append( max(set(position), key=position.count) )
    least_common.append( min(set(position), key=position.count) )

print('part I:', ''.join(most_common))
print('part II:', ''.join(least_common))
