from hashlib import sha256
import time
import multiprocessing
from multiprocessing import Pool, Manager
from itertools import repeat
import parmap
import os

DIFFICULTY = 5
MAX_NONCE = int(1e10)
NUM_PROCESSES = multiprocessing.cpu_count()

block_number = 668861
transactions = """
    This is sample transactions
"""

previous_hash = '0000000000d[u294qjmfo;3umijqru89cfnyhdajks1238910u023u7d0j'

def mining(process, result):
    new_hash = None
    for nonce in range(process-1, MAX_NONCE, NUM_PROCESSES):
        if result.keys() != [] and min(result.keys()) < nonce:
            return
        text = str(block_number) + transactions + previous_hash + str(nonce)
        new_hash = sha256(text.encode('ascii')).hexdigest()
        
        if new_hash.startswith('0'*DIFFICULTY):
            result[nonce] = new_hash
            return
            
    if not new_hash.startswith('0'*DIFFICULTY):
        print("Cannot find new hash")

if __name__ == "__main__":
    start_time = time.time()
    result = Manager().dict()
    parmap.map(mining, range(1, NUM_PROCESSES+1), result, pm_pbar=True, pm_processes=NUM_PROCESSES)
    nonce = min(result.keys())
    new_hash = result[nonce]
    print(f"Hash: {new_hash}")
    print(f"Nonce: {nonce}")
    print(f"Mining took {time.time() - start_time}s!")