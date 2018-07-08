from flask import Flask, render_template, request, make_response
app = Flask(__name__)

@app.route('/hello/<int:score>')
def hello(score):
    return render_template('hello.html', marks=score)

@app.route('/result', methods=['POST', 'GET'])
def result():
    res = request.form
    return render_template('result.html', result=res)

@app.route('/index')
def index():
    return render_template('index.html')

@app.route('/student')
def student():
    return render_template('student.html')

@app.route('/setcookie', methods=['POST','GET'])
def setcookie():
    if request.method=='POST':
        user = request.form['nm']
        res = make_response(render_template('readcookie.html'))
        res.set_cookie('userID', user)
    return res

@app.route('/getcookie')
def getcookie():
   name = request.cookies.get('userID')
   return '<h1>welcome '+name+'</h1>'


if __name__ == '__main__':
    app.run(debug=True)
