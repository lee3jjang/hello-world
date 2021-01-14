import argparse
import sys

def hello(name):
    print(f"Hello, {name}")

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("name", help="이름입니다")
    parser.add_argument("-v", "--version", action="store_true", help="버전")
    parser.add_argument("-k", "--golive", action="store_true", help="say good bye")
    
    args = parser.parse_args()
    print(args.golive)
    print(args.version)
    # name = args.name
    # hello(name)
    # parser.print_help()

    # if args.version:
    #     print("1.0.0")
    #     sys.exit()

if __name__ == "__main__":
    main()