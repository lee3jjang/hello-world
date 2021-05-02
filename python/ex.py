import streamlit as st
import sqlite3
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
from sklearn.datasets import load_iris
import plotly.express as px
import plotly.graph_objects as go
from plotly.subplots import make_subplots
from pathlib import Path

def main():
    # 타이틀
    st.title('김나맥의 데이터사이언스 블로그')

    # 메뉴
    menu = ["시각화"]
    choice = st.sidebar.selectbox("메뉴", menu)

    # 서브메뉴
    st.subheader(choice)

    st.write(Path('title.md').read_text(encoding='utf-8'), unsafe_allow_html=True)
  
    # 시각화(1)
    x = np.linspace(0, 10, 100)
    y = np.sin(x)
    y2 = np.cos(x)

    low_c = '#dd4124'
    high_c = '#009473'
    background_color = '#fcfcfc'
    hovertemplate = """
    f(%{x:,.2f})=%{y:,.2f}
    """

    fig = make_subplots(rows=1, cols=1, shared_xaxes=True)
    fig.add_trace(go.Scatter(
        x=x, y=y,
        marker=dict(size=10, symbol='square', color=low_c, line=dict(width=1, color='#323232')),
        line=dict(width=4, dash='dot'),
        name='sin(x)', mode='lines+markers'
    ))
    fig.add_trace(go.Scatter(
        x=x, y=y2,
        marker=dict(size=10, symbol='cross', color=high_c, line=dict(width=1, color='#323232')),
        name='cos(x)', mode='markers',
    ))
    fig.update_traces(
        hovertemplate=hovertemplate,
    )
    fig.update_layout(
        title=dict(text='<b>삼각함수</b>', font_size=20, font_color='#323232', xanchor='center', yanchor='top', x=0.475, y=0.97),
        margin=dict(l=40, r=40, b=40, t=60),
        width=800,
        height=400,
        font_family='Malgun Gothic',
        font_color='black',
        plot_bgcolor=background_color,
        paper_bgcolor=background_color,
        legend_title='<b>함수</b>',
        legend_title_font_size=17,
        legend_font_size=15,
        # hovermode='x unified',
        showlegend=True,
        hoverlabel_align='left',
    )
    fig.update_xaxes(
        title=dict(text='<b>x</b>', font_color='#323232', font_size=18, standoff=0),
        tickfont=dict(size=15, family='Malgun Gothic', color='#323232'),
        tickformat='.0f',
        tickangle=0,
        dtick=1,
        showline=True, linecolor='#323232', linewidth=2,
        zeroline=False, #zerolinewidth=2, zerolinecolor='black',
        showgrid=False, #gridcolor='black', gridwidth=1,
    )
    fig.update_yaxes(
        title=dict(text=r'<b>f(x)</b>', font_color='#323232', font_size=18, standoff=0),
        tickfont=dict(size=15, family='Malgun Gothic', color='#323232'),
        tickformat='.1f',
        dtick=0.5,
        showline=False, #linecolor='#323232', linewidth=2,
        zeroline=False, #zerolinewidth=2, zerolinecolor='black',
        showgrid=False, #gridcolor='black', gridwidth=1,
    )
    st.plotly_chart(fig)

if __name__ == '__main__':
    main()