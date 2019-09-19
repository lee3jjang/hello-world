import os
import time
import shutil
import pandas as pd

class Searcher:
    
    def __init__(self, directory):
        self.directory = directory.replace("\\", "/")
        if self.directory[-1] != "/": self.directory = self.directory + "/"

    def search(self, directory):
        files = os.listdir(directory)
        for file in files:
            full_file_name = os.path.join(directory, file)
            if os.path.isdir(full_file_name):
                self.search(full_file_name)
            else:
                self.origin_path.append([directory, file, full_file_name])
                
    def run(self, save=True):
        print('------------- Searching Start -------------')
        self.origin_path = []
        self.search(self.directory)
        self.to_frame()
        if save:
            self.to_xlsx(self.origin_path, 'router.xlsx')
        print('------------- Searching End -------------')
        
    def to_frame(self):
        self.origin_path = pd.DataFrame(self.origin_path)
        self.origin_path.columns = ['절대경로', '파일명', '절대경로_전체']
        self.origin_path['절대경로'] = self.origin_path['절대경로'].str.replace("\\", "/")
        self.origin_path['절대경로_전체'] = self.origin_path['절대경로_전체'].str.replace("\\", "/")
        self.origin_path['상대경로'] = self.origin_path['절대경로'].str.slice(len(self.directory)-1)
        self.origin_path['상대경로_전체'] = self.origin_path['절대경로_전체'].str.slice(len(self.directory)-1)
        self.origin_path['상대경로'] = self.origin_path['상대경로'].str.replace("\\", "/")
        self.origin_path['상대경로_전체'] = self.origin_path['상대경로_전체'].str.replace("\\", "/")
        self.origin_path = self.origin_path[['파일명', '절대경로', '상대경로', '절대경로_전체', '상대경로_전체']]
        
    def to_xlsx(self, df, file_name):
        writer = pd.ExcelWriter(file_name, 'xlsxwriter')
        df.to_excel(writer, index=False)
        writer.save()
        writer.close()
        
        
class Router:
    
    def __init__(self, source_directory, origin_path):
        self.source_directory = source_directory.replace("\\", "/")
        if not any([s == 'result' for s in os.listdir('.')]):
            os.mkdir('result')
        self.target_directory = './result'
        if self.source_directory[-1] != "/": self.source_directory = self.source_directory + "/"
        if self.target_directory[-1] != "/": self.target_directory = self.target_directory + "/"
        self.origin_path = origin_path
        
    def run(self):
        print('------------- Routing Start -------------')
        self.files = self.get_files()
        self.address = self.get_address()
        self.routes = self.get_routes()
        self.check_uniqueness()
        self.make_paths_all()
        self.move_all()
        self.get_missing()
        print('------------- Routing End -------------')
        
    def get_files(self):
        return os.listdir(self.source_directory)
    
    def get_routes(self):
        return self.address['상대경로'].unique()
    
    def get_address(self):
        return self.origin_path.loc[self.origin_path['파일명'].isin(self.files), ['파일명', '상대경로']]
    
    def check_uniqueness(self):
        if self.address.shape[0] != self.address.drop_duplicates().shape[0]:
            print("중복없음")
        else:
            print("중복있음")
            
    def make_paths(self, directory):
        while(1):
            try:
                try:
                    os.mkdir(self.target_directory + directory)
                except(FileNotFoundError):
                    self.make_paths('/'.join(directory.split('/')[:-1]))
            except(FileExistsError):
                break
                
    def make_paths_all(self):
        for route in self.routes:
            self.make_paths(route)
            
    def move_one(self, file):
        goals = self.address.loc[self.address['파일명'] == file, '상대경로']
        A = os.path.join(self.source_directory, file)
        for goal in goals:
            B = os.path.join(self.target_directory + goal, file)
            shutil.copy(A, B)
    
    def move_all(self):
        for file in self.files:
            self.move_one(file)
            
    def get_missing(self):
        searcher = Searcher(self.target_directory)
        searcher.run(save=False)
        self.missing = self.origin_path.loc[~self.origin_path['파일명'].isin(searcher.origin_path['파일명']), '파일명'].drop_duplicates()
        self.to_xlsx(self.missing, 'missing.xlsx')
    
    def to_xlsx(self, df, file_name):
        writer = pd.ExcelWriter(file_name, 'xlsxwriter')
        df.to_excel(writer, index=False)
        writer.save()
        writer.close()