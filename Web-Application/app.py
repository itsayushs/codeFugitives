#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Sun Mar 11 13:21:02 2018
Flask init [App]
@author: Ayush Sharma
"""
from flask import Flask, render_template, flash, request, url_for, redirect, session
from form import UserRegistrationForm,UserLoginForm,BandRegistrationForm
import hashlib
from functools import wraps
import models as dbHandler
from flask_googlemaps import GoogleMaps
from flask_googlemaps import Map
from markme2 import getCord
from pipam import patch_pi
#import connect

app=Flask(__name__)
app.secret_key = '66049c07d9e8546699fe0872fd32d8f6'
app.config['GOOGLEMAPS_KEY'] = "AIzaSyDgbrCoiWRj48dmsmHrXJPFs2cra11AKSs"
GoogleMaps(app, key="AIzaSyDgbrCoiWRj48dmsmHrXJPFs2cra11AKSs")

@app.route('/',methods=['GET','POST'])
def intro():       
    return render_template('index.html')

@app.route('/admin/', methods=["GET","POST"])
def register_page():
        form = UserRegistrationForm(request.form)
        if request.method == "POST" and form.validate():
            st_id  = form.st_id.data
            st_loc = form.st_loc.data
            st_iname = form.st_iname.data
            m=str(form.password.data)
            opassword = hashlib.md5(m.encode())
            password=opassword.hexdigest()
            dbHandler.insertUser(st_id, st_loc,password,st_iname)            
            return redirect(url_for('login'))
        return render_template("admin.html", form=form)

@app.route('/login/', methods=["GET","POST"])
def login():
    form = UserLoginForm(request.form)
    if request.method == "POST" and form.validate():
        st_id  = form.st_id.data
        hashed=dbHandler.retrieveUsers(str(st_id))
        m=str(form.password.data)
        opassword = hashlib.md5(m.encode())
        hpassword=opassword.hexdigest()
        if str(hpassword)==str(hashed):
            session['logged_in'] = True
            return redirect(url_for('home'))
        else:
            return ('error in matching the password')
    else:
        return render_template("login.html", form=form)

def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if 'logged_in' in session:
            return f(*args, **kwargs)
        else:
            flash("You need to login first")
            return redirect(url_for('login'))
    return wrap

@app.route('/home/')
@login_required
def home():
    return render_template("home.html")

@app.route("/logout/")
@login_required
def logout():
    session.clear()
    flash("You have been logged out!")
    return redirect(url_for('intro'))

@app.route('/trackall/')
@login_required
def fullmap():
    fullmap = Map(
        identifier="fullmap",
        varname="fullmap",
        style=(
            "height:100%;"
            "width:100%;"
            "top:0;"
            "left:0;"
            "position:absolute;"
            "z-index:200;"
        ),
        lat=26.8263,
        lng=75.7844,
        markers= getCord(),
    )
    return render_template('example_fullmap.html', fullmap=fullmap)

@app.route('/assign/', methods=["GET","POST"])
@login_required
def register_band():
        form = BandRegistrationForm(request.form)
        if request.method == "POST" and form.validate():
            st_id  = form.st_id.data
            band_id = form.band_id.data
            f_id = form.f_id.data
            param = form.param.data
            hashed=dbHandler.retrieveUsers(str(st_id))
            m=str(form.password.data)
            opassword = hashlib.md5(m.encode())
            hpassword=opassword.hexdigest()
            if str(hpassword)==str(hashed):
                dbHandler.insertBand(st_id, band_id,f_id,param)
                patch_pi(st_id, band_id,f_id,param)
                return redirect(url_for('login'))
        return render_template("assign.html", form=form)

if __name__ == "__main__":
    app.run(debug=True,port=6969)