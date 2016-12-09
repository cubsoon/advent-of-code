import numpy as np


def create_display(cols, rows):
    return np.array([[False for _ in range(cols)] for _ in range(rows)])


def rect(display, cols, rows):
    for row in display[0:rows]:
        row[0:cols] = [True for _ in range(cols)]


def rotate(display, what, index, amount):
    if what == 'column':
        to_rotate = display[:, index]
    elif what == 'row':
        to_rotate = display[index, :]
    else:
        raise AttributeError('Neither column nor row chosen.')

    shift = amount % len(to_rotate)
    to_rotate = np.concatenate([to_rotate[-shift:], to_rotate[0:-shift]])

    if what == 'column':
        display[:, index] = to_rotate
    elif what == 'row':
        display[index, :] = to_rotate


def print_display(display):
    for row in display:
        print(''.join('. ' if not lit else 'XX' for lit in row))


def count_lit(display):
    return np.sum(display)


def execute_line(display, line):
    line = str(line).strip()
    tokens = line.split()
    if len(tokens) == 0:
        pass

    try:
        command = tokens[0]
        assert command in ('rect', 'rotate')
        if tokens[0] == 'rect':
            assert len(tokens) == 2
            cols, rows = tuple(tokens[1].split("x"))
            rect(display, int(cols), int(rows))
        elif tokens[0] == 'rotate':
            assert len(tokens) == 5
            what = tokens[1]
            assert what in ('column', 'row')
            letter, index = tuple(tokens[2].split("="))
            assert letter == 'x' if what == 'column' else 'y'
            by = tokens[3]
            assert by == 'by'
            amount = tokens[4]
            rotate(display, str(what), int(index), int(amount))
    except Exception as exception:
        raise SyntaxError("Can not parse line '{}'.".format(str(line))) from exception

if __name__ == '__main__':
    display = create_display(50, 6)

    lines = []
    with open('day8.txt') as file:
        lines += file.readlines()

    for line in lines:
        try:
            execute_line(display, line)
        except SyntaxError:
            pass

    print_display(display)
    print(count_lit(display))