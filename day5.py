import hashlib

identificator = 'wtnhxymk'
password = [ None ] * 8
number = 0
while None in password:
    while True:
        m = hashlib.md5()
        m.update(identificator.encode('ascii'))
        m.update(str(number).encode('ascii'))
        hash = m.hexdigest()
        number += 1

        position = int(hash[5], 16)
        if hash.startswith('00000') and position < len(password) and password[position] is None:
            print(hash, position, hash[6], number)
            password[position] = hash[6]
            break
        
print('part II:', ''.join(password))
