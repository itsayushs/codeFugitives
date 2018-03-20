#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 20 04:13:01 2018

@author: stark
"""

from wtforms import Form, TextField, PasswordField, validators

class UserLoginForm(Form):
    st_id = TextField('Station ID', [validators.Length(min=4, max=20),validators.InputRequired()])
    password = PasswordField('Password', [validators.Length(min=4, max=20),validators.InputRequired()])
    
class UserRegistrationForm(Form):
    st_id = TextField('Station ID', [validators.Length(min=4, max=20)])
    st_loc = TextField('Location', [validators.Length(min=4, max=50)])
    st_iname = TextField('Incharge Name', [validators.Length(min=4, max=50)])
    password = PasswordField('New Password', [
        validators.Required(),
        validators.EqualTo('confirm', message='Passwords must match')
    ])
    confirm = PasswordField('Repeat Password')
    
class BandRegistrationForm(Form):
    st_id = TextField('Station ID', [validators.Length(min=4, max=20)])
    band_id = TextField('Band ID', [validators.Length(min=4, max=50)])
    f_id = TextField('Fugitive ID', [validators.Length(min=4, max=50)])
    param = TextField('Radius', [validators.Length(min=4, max=50)])
    password = PasswordField('Password', [validators.Length(min=4, max=20),validators.InputRequired()])
