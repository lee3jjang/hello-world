import os
import sys
import logging
import numpy as np

from datetime import datetime
from flask import Flask, render_template, request, redirect, url_for, send_file
import test
from multiprocessing import Process
from threading import Thread
import subprocess

def run(number):
    print(f'Run {number}')

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/download')
def download():
    file_name = 'data/test.txt'
    return send_file(file_name, attachment_filename='result.txt', as_attachment=True)

@app.route('/table')
def table():
    return render_template('table.html', rows=10)

@app.route('/form', methods=["POST"])
def form():
    print(request.form['id'])
    print(request.form.get('pwd'))
    print(request.form.get('gender'))
    print(request.form.getlist('part'))
    
    num_ticket = int(request.form.get('ticket'))
    print(request.form.get('price'))

    procs = []
    for i in range(num_ticket):
        proc = Thread(target=test.run, args=(i, ))
        procs.append(proc)
        proc.start()

    for proc in procs:
        proc.join()

    subprocess.run('dir', shell=True)

    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True)