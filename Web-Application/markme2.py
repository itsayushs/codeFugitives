#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 20 16:08:22 2018

@author: stark
"""

import MySQLdb as sql
#for i in 
host="localhost"
user="root"
passw=""
db='test'
def getCord():
    con = sql.connect(host=host,user=user,passwd=passw, db=db)
    cur = con.cursor()
    cur.execute("Select lats,longd,id from location")
    loc = cur.fetchall()
    con.commit()
    con.close()
    return(list(loc))

    
