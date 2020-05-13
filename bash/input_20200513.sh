# 입력한 스케쥴을 받아서 파일에 추가하는 프로그램

echo -n "종목을 입력하세요: "
read -a words

python current_stock_price_20200513.py ${words[0]}
