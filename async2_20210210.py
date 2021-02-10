import asyncio

a_list = [4, 2, 3]

def f(x, y):
    return x*y

async def fetch(x):
    return await loop.run_in_executor(None, f, x, 7)
    

async def main():
    futures = [asyncio.ensure_future(fetch(a)) for a in a_list]
    result = await asyncio.gather(*futures)
    print(result)

if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())
    loop.close()