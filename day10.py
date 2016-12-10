from collections import defaultdict
import re

factory = {
    'bot': defaultdict(lambda: {'has': [], 'low': None, 'high': None}),
    'output': defaultdict(lambda: {'has': []})
}

comps = []


def get_target(target):
    t, i = target
    return factory[t][i]


def give(what, target):
    get_target(target)['has'].append(what)
    t, _ = target
    if t == 'bot' and len(get_target(target)['has']) == 2:
        l, h = tuple(sorted(get_target(target)['has'], key=lambda i: int(i)))
        give(l, get_target(target)['low'])
        give(h, get_target(target)['high'])
        get_target(target)['has'] = []
        comps.append((l, h, target))


input_commands = []
def handle_input_line(match):
    what, target = tuple(match.groups())
    target = tuple(target.split())
    input_commands.append((what, target))


def handle_bot_line(match):
    who, type1, target1, type2, target2 = match.groups()
    who = tuple(who.split())
    target1 = tuple(target1.split())
    target2 = tuple(target2.split())
    get_target(who)[type1] = target1
    get_target(who)[type2] = target2


line_input_pattern = re.compile(r'value (.+) goes to (.+)')
line_bot_pattern = re.compile(r'(.+) gives (.+) to (.+) and (.+) to (.+)')
commands = [(line_input_pattern, handle_input_line), (line_bot_pattern, handle_bot_line)]

with open('day10.txt') as file:
    for line in file:
        for command in commands:
            pattern, handler = command
            match = pattern.match(line.strip())
            if match is not None:
                handler(match)
                break

for input_command in input_commands:
    w, t = input_command
    give(w, t)

for comp in comps:
    l, h, b = comp
    if l == '17' and h == '61':
        print(comp)
        print('part I:', b[1])

n = 1
for output in factory['output'].items():
    if output[0] in ['0', '1', '2']:
        print(output)
        n *= int(output[1]['has'][0])
print('part II:', n)
