from hashlib import sha256
import time

MAX_NONCE = int(1e10)
DIFFICULTY = 6

block_number = 668861
transactions = """
    This is sample transactions
"""

previous_hash = '0000000000d[u294qjmfo;3umijqru89cfnyhdajks1238910u023u7d0j'
new_hash = None

start_time = time.time()

for nonce in range(MAX_NONCE):
    text = str(block_number) + transactions + previous_hash + str(nonce)
    new_hash = sha256(text.encode('ascii')).hexdigest()
    if new_hash.startswith('0'*DIFFICULTY):
        print(f"Hash: {new_hash}")
        print(f"Nonce: {nonce}")
        break

if not new_hash.startswith('0'*DIFFICULTY):
    print("Cannot find new hash")

print(f"Mining took {time.time() - start_time}s!")