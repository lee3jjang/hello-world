from stockpricecrawler import StockPriceCrawler
import asyncio
import time

codes = ["005830", "005930", "105560"]

async def fetch(code):
    spc = StockPriceCrawler()
    spc.set_code([code])
    spc.set_daterange("2021-01-01", "2021-01-31")
    stock_price = await loop.run_in_executor(None, spc.get_stock_price)
    return stock_price

async def main():
    futures = [asyncio.ensure_future(fetch(code)) for code in codes]
    result = await asyncio.gather(*futures)
    print(result)

if __name__ == "__main__":
    start_time = time.time()
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())
    loop.close()
    end_time = time.time()
    print(f"실행시간 : {end_time-start_time:.3f}초")


