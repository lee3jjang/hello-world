# 변수가 있는 문장 출력하기
str="Hello World!"
echo "다음의 문장이 출력됩니다. ${str}"
echo "=============================="

# 입력변수
echo "\$0: $0"
echo "\$1부터는 입력하는 변수가 들어감"
echo "\$1: $1"
echo "\$2: $2"
echo "=============================="

# local variable
str="Hello"
global_string_test() {
    echo "외부에서 정해진 변수가 출력됩니다."
    echo ${str}
}

local_string_test() {
    echo "내부에서 정해진 변수가 출력됩니다."
    echo "외부 변수에는 영향을 가하지 않습니다."
    local str="World"
    echo ${str}
}

local2_string_test() {
    echo "내부에서 정해진 변수가 출력됩니다."
    echo "local 키워드를 빼게 되면 외부 변수에 영향을 끼칩니다."
    str="Hi"
    echo ${str}
}

global_string_test
local_string_test
local2_string_test
echo ${str}