import pandas as pd

data = ['1990-04-22','1995-05-18','2014-03-08']
df = pd.DataFrame(data, columns=['yyyy-dd-mm'])

extract_year = lambda date: date.split('-')[0]
df['year'] = df['yyyy-dd-mm'].apply(extract_year)

extract_age = lambda year, current_year: current_year - int(year)
df['age'] = df['year'].apply(extract_age, current_year=2018)

get_introduce = lambda row, words1, words2: words1 + str(row.year) + words2 + str(row.age)
df['introduce'] = df.apply(get_introduce, words1="I was born in ", words2=" my age is ", axis=1)
