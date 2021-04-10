import streamlit as st

# Title
st.title('Streamlit Tutorial')

# Header
st.header('This is header')
st.subheader('This is subheader')

# Text
st.text('Hello Streamlit! 이 글은 튜토리얼 입니다.')

# Markdown
st.markdown('# This is a Markdown Title')
st.markdown('## This is a Markdown header')

# Latex
st.latex(r'Y = \alpha + \beta X_i')

# Error
st.success('Successful')
st.info('Information!')
st.warning('This is a warning')
st.error('This is an error!')
st.exception("NameError('Error name is not defined')")

# Load data
import pandas as pd
from sklearn.datasets import load_iris
iris = load_iris()
iris_df = pd.DataFrame(iris.data, columns=iris.feature_names)
iris_df['target'] = iris.target
iris_df['target'] = iris_df['target'].apply(lambda x: 'setosa' if x==0 else 'versicolor' if x==1 else 'virginica')

# Table
st.table(iris_df.head())

# DataFrame
st.dataframe(iris_df)
st.write(iris_df)

# Checkbox
if st.checkbox('Show/Hide'):
    st.write('체크박스가 선택되었습니다.')

# Radio button
status = st.radio('Select status.', ('Active', 'Inactive'))
if status == 'Active':
    st.success('활성화 되었습니다.')
else:
    st.warning('비활성화 되었습니다.')

# Select Box
occupation = st.selectbox('직군을 선택하세요', [
    'Backend Developer',
    'Frontend Developer',
    'ML Engineer',
    'Data Engineer',
    'Database Administrator',
    'Data Scientist',
    'Data Analyst',
    'Security Engineer',
])
st.write(f'당신의 직군은 {occupation} 입니다.')

# Multiselect
location = st.multiselect('선호하는 유튜브 채널을 선택하세요.', (
    '운동', 'IT기기', '브이로그', '먹방', '반려동물', '맛집리뷰'
))
st.write(len(location), '가지를 선택했습니다.')

# Slider
level = st.slider('레벨을 선택하세요', 1, 5)

# Button
if st.button('About'):
    st.text('Streamlit을 이용한 튜토리얼입니다.')

# Text Input
first_name = st.text_input('Enter Your First Name', "Type Here ...")
if st.button('Submit', key='first_name'):
    result = first_name.title()
    st.success(result)

# Text Area
message = st.text_area('메시지를 입력하세요', 'Type Here ...')
if st.button('Submit', key='message'):
    result = first_name.title()
    st.success(result)

# Data Input
import datetime
now = datetime.datetime.now()
today = st.date_input('날짜를 선택하세요.', now)
the_time = st.time_input('시간을 입력하세요.', datetime.time())

# Raw Code(1 line)
st.subheader('Display one-line code')
st.code('import numpy as np')

# Raw Code(snippet)
st.subheader('Display code snippet')
with st.echo():
    import pandas as pd
    df = pd.DataFrame()

# JSON
st.subheader('Display JSON')
st.json({'name': '민수', 'gender': 'male', 'Age': 29})

# Siderbar
with st.sidebar:
    st.subheader('서브헤더')

# File Uploader
myfile = st.file_uploader('Upload File', type=['txt'])
mytext = str(myfile.read(), 'utf-8')
st.write(mytext)