import os
import zipfile
import numpy as np
from os import path
from glob import glob
import collections

def compress(source_path, target_path, name='result', max_size=np.inf, check_duplicate=True):
    '''
        Input:
            source_path: 압축 대상 파일들이 모여있는 폴더 경로
            target_path: zip 파일을 저장할 경로
            max_size: 파일 1개당 최대 용량(byte 단위, 용량 초과 시 분할 압축)
            name: zip 파일 이름
            check_ducplicate: 중복되는 파일명이 있는지 검사
            
        Output:
            zip 파일 (Return None)
        
        Description:
            - source_path에 있는 파일 전체를 압축한 후 target_path에 {name}.zip 파일로 저장함
            - 상대경로, 절대경로 둘 다 가능
            - 끝에 \ 붙여도 안 붙여도 상관없음
            - 경로가 존재하지 않으면 작동 안 함
            - source_path에 파일이 아닌 폴더가 있으면, 그 폴더 내부에 있는 파일을 모두 탐색하여 복사함
            - name 뒤에 .zip이 없으면 자동으로 추가함
            - 파일당 사이즈가 max_size보다 크면 error
            - 기존 파일 자동 덮어쓰기
            
        Example:
            compress('C:/Users/Administrator/dev/source', 'C:/Users/Administrator/dev/result', 20000, 'test')
    '''
    if not name.endswith('.zip'):
        name = name + '.zip'
    source_path_abs_list = files_in_folder(path.abspath(source_path))
    if check_duplicate:
        duplicates = [item for item, count in collections.Counter([path.basename(p) for p in source_path_abs_list]).items() if count > 1]
        if duplicates != []:
            raise Exception('중복되는 파일이 있습니다.\n{}'.format(';'.join(duplicates)))
    target_path_abs = path.abspath(target_path)
    result_zip = zipfile.ZipFile(target_path_abs + '\\' + name, 'w')
    file_size = 0
    i = 0
    for fp in source_path_abs_list:
        if(file_size + path.getsize(fp) > max_size):
            if path.getsize(fp) > max_size:
                raise Exception('파일당 사이즈가 max_size보다 큽니다.')
            result_zip.close()
            result_zip = zipfile.ZipFile(target_path_abs + '\\' + name[:-4] + '_{:03d}'.format(i+1) + '.zip', 'w')
            file_size = 0
            i += 1
        result_zip.write(fp, path.basename(fp), compress_type=zipfile.ZIP_DEFLATED)
        file_size += path.getsize(fp)
    result_zip.close()

def files_in_folder(source_path='.'):
    '''
        Description:
            특정 폴더 안에 있는 모든 파일들의 절대경로 추출
        
        Input:
            source_path: 탐색하고자 하는 폴더의 경로
        
        Output:
            폴더경로 list
        
        Example:
        files_in_folder('C:\\Users\\Administrator\\dev\\result')
        
    '''
    result = []
    for fp in glob(path.abspath(source_path) + '\\*'):
        if not path.isdir(fp):
            result.append(fp)
        else:
            result += files_in_folder(fp)
    return result

# compress('source', 'result', name='test', max_size=5e6, check_duplicate=False)
