import asyncio
import time
import logging
logging.basicConfig(format=None, level=logging.INFO)
logger = logging.getLogger()


async def task1():
    logger.info('Task1 Start')
    await asyncio.sleep(6)
    logger.info('Task1 End')
    return '=*'

async def task2():
    logger.info('Task2 Start')
    await asyncio.sleep(3)
    logger.info('Task2 End')
    return '-!'
  
async def main():
    start_time = time.time()
    # result = await asyncio.gather(*[task1(), task2()])
    t1 = asyncio.create_task(task1())
    t2 = asyncio.create_task(task2())
    logger.info('====================')
    await t2
    logger.info(t2._result*10)
    await t1
    logger.info(t1._result*10)
    # logger.info(result[1]*20)
    # logger.info(result[0]*20)
    end_time = time.time()
    logger.info(f'Elapsed: {end_time-start_time:,.6f}')

if __name__ == '__main__':
    asyncio.run(main())

    

