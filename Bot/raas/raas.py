import os
import pickle
import shutil
import zipfile
import collections
import numpy as np
from os import path
from glob import glob

def compress(source, target, name='result', max_size=np.inf, ignore=None):
    '''
        Input:
            source: 압축 대상 파일들이 모여있는 폴더 경로
            target: zip 파일을 저장할 경로
            max_size: 파일 1개당 최대 용량(byte 단위, 용량 초과 시 분할 압축)
            name: zip 파일 이름
            ignore: 무시할 파일명 리스트
            
        Output:
            zip 파일 (Return None)
        
        Description:
            - source에 있는 파일 전체를 압축한 후 target_path에 {name}.zip 파일로 저장함
            - 상대경로, 절대경로 둘 다 가능
            - 끝에 \ 붙여도 안 붙여도 상관없음
            - 경로가 존재하지 않으면 작동 안 함
            - source에 파일이 아닌 폴더가 있으면, 그 폴더 내부에 있는 파일을 모두 탐색하여 복사함
            - name 뒤에 .zip이 있어도 없어도 상관없음
            - 파일당 사이즈가 max_size보다 크면 error
            - 기존 zip 파일 위에 자동으로 덮어씀
            - target 폴더에 다른 zip 파일이 있지 않도록 하는 것을 추천
            
        Example:
            compress('C:/Users/Administrator/dev/source', 'C:/Users/Administrator/dev/result', 'test', 20000, ['test.txt', 'test2.txt'])
    '''
    if not name.endswith('.zip'):
        name = name + '.zip'
    source_path_abs_list = files_in_folder(path.abspath(source))
    if ignore != None:
        source_path_abs_list = [p for p in source_path_abs_list if path.basename(p) not in ignore]
    duplicates = [item for item, count in collections.Counter([path.basename(p) for p in source_path_abs_list]).items() if count > 1]
    if duplicates != []:
        raise Exception('중복되는 파일이 있습니다: {}'.format(duplicates))
    target_path_abs = path.abspath(target)
    result_zip = zipfile.ZipFile(target_path_abs + '\\' + name, 'w')
    file_size = 0
    i = 0
    for fp in source_path_abs_list:
        if(file_size + path.getsize(fp) > max_size):
            if path.getsize(fp) > max_size:
                raise Exception('파일당 사이즈가 max_size보다 큽니다: {} bytes'.format(path.getsize(fp)))
            result_zip.close()
            result_zip = zipfile.ZipFile(target_path_abs + '\\' + name[:-4] + '_{:03d}'.format(i+1) + '.zip', 'w')
            file_size = 0
            i += 1
        result_zip.write(fp, path.basename(fp), compress_type=zipfile.ZIP_DEFLATED)
        file_size += path.getsize(fp)
    result_zip.close()
    return source_path_abs_list

# Utility
def files_in_folder(source):
    '''
        Description:
            특정 폴더 안에 있는 모든 파일들의 절대경로 추출
        
        Input:
            source: 탐색하고자 하는 폴더의 경로
        
        Output:
            폴더경로 list
        
        Example:
            files_in_folder('C:\\Users\\Administrator\\dev\\result')
    '''
    result = []
    for fp in glob(path.abspath(source) + '\\*'):
        if not path.isdir(fp):
            result.append(fp)
        else:
            result += files_in_folder(fp)
    return result

def make_router(source, ignore=None, save=True):
    '''
        Description:
            - 폴더 내 각 파일들의 상대위치를 추출함
            - 라우터를 저장하게 되면 router.txt로 저장됨 (pickle)
        
        Input:
            source: 탐색하고자 하는 폴더의 경로
            ignore: 무시하고자 하는 파일명 리스트
            save: 만들어진 라우터 저장 여부
        
        Output:
            (파일명, 파일경로) dictionary
        
        Example:
            make_router('.', ['test.txt', 'test2.txt'])
    '''
    source_path_abs_list = files_in_folder(path.abspath(source))
    if ignore != None:
        source_path_abs_list = [p for p in source_path_abs_list if path.basename(p) not in ignore]
    duplicates = [item for item, count in collections.Counter([path.basename(p) for p in source_path_abs_list]).items() if count > 1]
    if duplicates != []:
        raise Exception('중복되는 파일이 있습니다: {}'.format(duplicates))
    router = {}
    for p in source_path_abs_list:
        router[path.basename(p)] = path.relpath(path.dirname(p), source)
    if save:
        with open('router.txt', 'wb') as f:
            pickle.dump(router, f)
    return router

# Utility
def mkdir2(target):
    '''
        Description:
            폴더 생성(이전 폴더 경로가 없으면 그 이전 폴더도 생성)
        
        Input:
            target: 폴더 경로
            
        Example:
            mkdir2('C:\\Users\\Administrator\\dev\\2\3\4') 
    '''
    try:
        os.mkdir(path.abspath(target))
    except FileNotFoundError:
        mkdir2(path.join(path.abspath(target), '..'))
        os.mkdir(path.abspath(target))
    except FileExistsError:
        pass
    
def make_tree(source, target, router):
    '''
        Description:
            router를 따라 폴더 구조를 생성하고 파일을 그 안에 복사
        
        Input:
            source: 파일들이 들어있는 폴더 경로
            target: 트리를 만들 폴더 경로
            router: router (dictionary)
            
        Example:
            make_tree('Rockstar Games', 'tree', router)
    '''
    for p in set(router.values()):
        mkdir2(path.join(target, p))
    for file in files_in_folder(source):
        try:
            filename = path.basename(file)
            shutil.copy(file, path.join(target, router[filename]))
        except KeyError:
            raise Exception('\'{}\' 의 경로명이 없습니다'.format(file))